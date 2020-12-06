package org.rhoff95.mixer.domain.calculator;

import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import org.rhoff95.mixer.domain.MixerSolution;

public class QuickColorScoreCalculator implements
    EasyScoreCalculator<MixerSolution, HardMediumSoftLongScore> {

    @Override
    public HardMediumSoftLongScore calculateScore(MixerSolution mixtureSolution) {
        return HardMediumSoftLongScore.ZERO;
    }
}
