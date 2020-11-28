package domain.calculator;

import domain.MixtureSolution;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class QuickColorScoreCalculator implements EasyScoreCalculator<MixtureSolution, HardMediumSoftLongScore> {

    @Override
    public HardMediumSoftLongScore calculateScore(MixtureSolution mixtureSolution) {
        return HardMediumSoftLongScore.ZERO;
    }
}
