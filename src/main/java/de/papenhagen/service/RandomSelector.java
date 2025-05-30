package de.papenhagen.service;
/*
 * Copyright 2015 Olivier Grégoire.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.quarkus.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Random;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import static java.util.Objects.requireNonNull;


/**
 * A tool to randomly select elements from collections.
 *
 * <p>
 * Example usages:
 * Random random = ...
 * Map<String, Double>; stringWeights = new HashMap<>();
 * stringWeights.put("a", 4d);
 * stringWeights.put("b", 3d);
 * stringWeights.put("c", 12d);
 * stringWeights.put("d", 1d);
 * RandomSelector<String> selector = RandomSelector.weighted(stringWeights.keySet(), s -> stringWeights.get(s));
 * List<String> selection = new ArrayList<>();
 * for (int i = 0; i < 10; i++) {
 * selection.add(selector.next(random));
 * }
 *
 * @param <T>
 * @author Olivier Grégoire
 */

public final class RandomSelector<T> {

    private static final Logger log = LoggerFactory.getLogger(RandomSelector.class);

    /**
     * Creates a random selector among <tt>elements</tt> where the elements have a weight defined by
     * <tt>weighter</tt>.
     *
     * <p>
     * A copy of <tt>elements</tt> is kept, so any modification to <tt>elements</tt> will not be
     * reflected in returned values.
     *
     * @param <T>
     * @param elements
     * @param weighter
     * @return
     * @throws IllegalArgumentException if <tt>elements</tt> is empty or if <tt>weighter</tt> returns
     *                                  a negative value or <tt>0</tt>.
     */
    public static <T> RandomSelector<T> weighted(
            final Collection<T> elements,
            final ToDoubleFunction<? super T> weighter)
            throws IllegalArgumentException {
        requireNonNull(elements, "elements must not be null");
        requireNonNull(weighter, "weighter must not be null");
        if (elements.isEmpty()) {
            Log.error("elements must not be empty");
            return null;
        }

        final int size = elements.size();

        @SuppressWarnings("unchecked") final T[] elementArray = elements.toArray((T[]) new Object[size]);

        double totalWeight = 0d;
        final double[] discreteProbabilities = new double[size];
        for (int i = 0; i < size; i++) {
            final double weight = weighter.applyAsDouble(elementArray[i]);
            if (weight < 0d) {
                Log.error("weighter returned a negative number");
                return null;
            }
            discreteProbabilities[i] = weight;
            totalWeight += weight;
        }
        for (int i = 0; i < size; i++) {
            discreteProbabilities[i] /= totalWeight;
        }
        return new RandomSelector<>(elementArray, new RandomWeightedSelection(discreteProbabilities));
    }

    private final T[] elements;
    private final ToIntFunction<Random> selection;

    RandomSelector(final T[] elements, final ToIntFunction<Random> selection) {
        this.elements = elements;
        this.selection = selection;
    }

    /**
     * Returns the next element using <tt>random</tt>.
     *
     * @param random
     * @return
     */
    public T next(final Random random) {
        return elements[selection.applyAsInt(random)];
    }


    private static class RandomWeightedSelection implements ToIntFunction<Random> {
        // Alias method implementation O(1)
        // using Vose's algorithm to initialize O(n)
        // https://en.wikipedia.org/wiki/Alias_method

        private final double[] probabilities;
        private final int[] alias;

        RandomWeightedSelection(final double[] probabilities) {
            final int size = probabilities.length;

            final double average = 1d / size;
            final int[] small = new int[size];
            int smallSize = 0;
            final int[] large = new int[size];
            int largeSize = 0;

            for (int i = 0; i < size; i++) {
                if (probabilities[i] < average) {
                    small[smallSize++] = i;
                } else {
                    large[largeSize++] = i;
                }
            }

            final double[] pr = new double[size];
            final int[] al = new int[size];
            this.probabilities = pr;
            this.alias = al;

            while (largeSize != 0 && smallSize != 0) {
                final int less = small[--smallSize];
                final int more = large[--largeSize];
                pr[less] = probabilities[less] * size;
                al[less] = more;
                probabilities[more] += probabilities[less] - average;
                if (probabilities[more] < average) {
                    small[smallSize++] = more;
                } else {
                    large[largeSize++] = more;
                }
            }
            while (smallSize != 0) {
                pr[small[--smallSize]] = 1d;
            }
            while (largeSize != 0) {
                pr[large[--largeSize]] = 1d;
            }
        }

        @Override
        public int applyAsInt(final Random random) {
            final int column = random.nextInt(probabilities.length);
            return random.nextDouble() < probabilities[column]
                    ? column
                    : alias[column];
        }
    }
}
