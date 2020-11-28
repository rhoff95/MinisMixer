package org.rhoff95.mixer.solver.score;

import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import org.rhoff95.mixer.domain.MixerSolution;

public class MixerEasyScoreCalculator implements
    EasyScoreCalculator<MixerSolution, HardMediumSoftLongScore> {

    @Override
    public HardMediumSoftLongScore calculateScore(MixerSolution mixerSolution) {
        return HardMediumSoftLongScore.ZERO;
    }
}
