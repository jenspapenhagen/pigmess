package de.papenhagen.service;

import de.papenhagen.entities.DiceCast;

import java.time.temporal.ValueRange;
import java.util.List;

import static de.papenhagen.entities.Postion.FEEDS_DOWN;
import static de.papenhagen.entities.Postion.FEEDS_UP;
import static de.papenhagen.entities.Postion.LAY_LEFT;
import static de.papenhagen.entities.Postion.LAY_RIGHT;
import static de.papenhagen.entities.Postion.STAND_HALF_ON_NOSE;
import static de.papenhagen.entities.Postion.STAND_ON_NOSE;

public class RuleSetUtil {
    public static List<DiceCast> ruleSet() {
        //hardcoded probability into 0 - 10000 Steps
        final ValueRange rangeFeedDown = ValueRange.of(0, 905);
        final ValueRange rangeFeedUp = ValueRange.of(906, 4171);

        final ValueRange rangeStandOnNose = ValueRange.of(4172, 4382);
        final ValueRange rangeStandHalfHalsNose = ValueRange.of(4383, 4403);

        final ValueRange rangeLayLeft = ValueRange.of(4404, 7509);
        final ValueRange rangeLayRight = ValueRange.of(7510, 10000);

        // build up the rule Set for this game
        final DiceCast feedDown = new DiceCast(FEEDS_DOWN, rangeFeedDown);
        final DiceCast feedUp = new DiceCast(FEEDS_UP, rangeFeedUp);

        final DiceCast standOnNose = new DiceCast(STAND_ON_NOSE, rangeStandOnNose);
        final DiceCast standHalfHalsNose = new DiceCast(STAND_HALF_ON_NOSE, rangeStandHalfHalsNose);

        final DiceCast layLeft = new DiceCast(LAY_LEFT, rangeLayLeft);
        final DiceCast layRight = new DiceCast(LAY_RIGHT, rangeLayRight);


        // the Rule set of this game
        return List.of(feedDown, feedUp, standOnNose, standHalfHalsNose, layLeft, layRight);
    }
}
