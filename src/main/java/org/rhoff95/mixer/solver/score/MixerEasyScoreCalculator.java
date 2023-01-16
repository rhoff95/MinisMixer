package org.rhoff95.mixer.solver.score;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class MixerEasyScoreCalculator implements
    EasyScoreCalculator<MixerSolution, HardMediumSoftLongScore> {

    private static final Logger logger = LogManager.getLogger(MixerEasyScoreCalculator.class);

    @Override
    public HardMediumSoftLongScore calculateScore(MixerSolution mixerSolution) {

        Paint targetPaint = mixerSolution.getTargetPaint();

        var mixes = 0;
        var paintWithMix = 0;
        var r = 0;
        var g = 0;
        var b = 0;

        var paintMixes = mixerSolution.getPaintMixes();

        for (PaintMix paintMix : paintMixes) {
            var paint = paintMix.getPaint();
            var amount = paintMix.getAmount();

            mixes += amount;
            if(amount > 0) {
                paintWithMix++;
            }

            r += amount * paint.getR();
            g += amount * paint.getG();
            b += amount * paint.getB();
        }

        var scaledR = 0f;
        var scaledG = 0f;
        var scaledB = 0f;

        if (mixes == 0) {
            return HardMediumSoftLongScore.ofHard(-1);
        }

        scaledR = (float) r / (float) mixes;
        scaledG = (float) g / mixes;
        scaledB = (float) b / mixes;

        var rDistance = Math.pow(scaledR - targetPaint.getR(), 2);
        var gDistance = Math.pow(scaledG - targetPaint.getG(), 2);
        var bDistance = Math.pow(scaledB - targetPaint.getB(), 2);

        var mediumScore = (long) -(rDistance + gDistance + bDistance);
        mediumScore -=   paintWithMix;

        var softScore = -mixes;

        return HardMediumSoftLongScore.of(0L, mediumScore, softScore);
    }
}
