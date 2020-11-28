package org.rhoff95.mixer.solver.move.factory;

import java.util.List;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.solver.move.MixChangeMove;

public class MixChangeMoveFactory implements MoveListFactory<MixerSolution> {

    @Override
    public List<MixChangeMove> createMoveList(MixerSolution mixerSolution) {
        return null;
    }
}
