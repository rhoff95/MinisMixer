package org.rhoff95.mixer.solver.move;

import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.rhoff95.mixer.domain.MixerSolution;

public class MixChangeMove extends AbstractMove<MixerSolution> {


    @Override
    protected AbstractMove<MixerSolution> createUndoMove(
        ScoreDirector<MixerSolution> scoreDirector) {
        return null;
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector<MixerSolution> scoreDirector) {

    }

    @Override
    public boolean isMoveDoable(ScoreDirector<MixerSolution> scoreDirector) {
        return false;
    }
}
