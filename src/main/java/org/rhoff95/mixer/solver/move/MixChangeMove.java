package org.rhoff95.mixer.solver.move;

import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.PaintMix;

public class MixChangeMove extends AbstractMove<MixerSolution> {

    private final PaintMix paintMix;
    private final Integer toAmount;

    public MixChangeMove(PaintMix paintMix, Integer toAmount) {
        this.paintMix = paintMix;
        this.toAmount = toAmount;
    }

    @Override
    public boolean isMoveDoable(ScoreDirector<MixerSolution> scoreDirector) {
        return !paintMix.getAmount().equals(toAmount);
    }

    @Override
    protected AbstractMove<MixerSolution> createUndoMove(
        ScoreDirector<MixerSolution> scoreDirector) {
        return new MixChangeMove(paintMix, paintMix.getAmount());
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector<MixerSolution> scoreDirector) {
        scoreDirector.beforeVariableChanged(paintMix, "amount"); // before changes are made
        paintMix.setAmount(toAmount);
        scoreDirector.afterVariableChanged(paintMix, "amount"); // after changes are made
    }

    @Override
    public MixChangeMove rebase(ScoreDirector<MixerSolution> destinationScoreDirector) {
        return new MixChangeMove(destinationScoreDirector.lookUpWorkingObject(paintMix), toAmount);
    }

    @Override
    public Collection<?> getPlanningEntities() {
        return Collections.singletonList(paintMix);
    }

    @Override
    public Collection<?> getPlanningValues() {
        return Collections.singletonList(toAmount);
    }

    @Override
    public String getSimpleMoveTypeDescription() {
        return "Changes the amount of one paint mix";
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
