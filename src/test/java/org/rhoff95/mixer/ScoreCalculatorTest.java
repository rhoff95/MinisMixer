package org.rhoff95.mixer;

import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.rhoff95.mixer.constants.Vallejo;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.PaintMix;
import org.rhoff95.mixer.solver.score.MixerEasyScoreCalculator;

public class ScoreCalculatorTest {

    private static final Logger logger = LogManager.getLogger(ScoreCalculatorTest.class);

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testScoreCalculationMatchSimple(int amount) {
        var paint = Vallejo.BloodyRed;
        var paintMix = new PaintMix(paint);
        paintMix.setAmount(amount);

        var mixerSolution = new MixerSolution();
        mixerSolution.setPaints(Collections.singletonList(paint));
        mixerSolution.setPaintMixes(Collections.singletonList(paintMix));
        mixerSolution.setTargetPaint(paint);

        var score = new MixerEasyScoreCalculator().calculateScore(mixerSolution);

        Assertions.assertEquals(score, HardMediumSoftLongScore.ZERO);
    }

    @Test
    public void testScoreCalculationInitial() {
        var paint = Vallejo.BloodyRed;
        var paintMix = new PaintMix(paint);
        paintMix.setAmount(0);

        var mixerSolution = new MixerSolution();
        mixerSolution.setPaints(Collections.singletonList(paint));
        mixerSolution.setPaintMixes(Collections.singletonList(paintMix));
        mixerSolution.setTargetPaint(paint);

        var score = new MixerEasyScoreCalculator().calculateScore(mixerSolution);

        Assertions.assertEquals(score, HardMediumSoftLongScore.ZERO);
    }

    @Test
    public void testScoreCalculationWrongColor() {
        var paint = Vallejo.BloodyRed;
        var targetPaint = Vallejo.MoonYellow;
        var paintMix = new PaintMix(paint);
        paintMix.setAmount(0);

        var mixerSolution = new MixerSolution();
        mixerSolution.setPaints(Collections.singletonList(paint));
        mixerSolution.setPaintMixes(Collections.singletonList(paintMix));
        mixerSolution.setTargetPaint(targetPaint);

        var score = new MixerEasyScoreCalculator().calculateScore(mixerSolution);

        Assertions.assertEquals(score, HardMediumSoftLongScore.ZERO);
    }
}
