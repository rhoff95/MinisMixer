package org.rhoff95.mixer.realtime;

import java.util.ArrayList;
import java.util.Optional;
import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaplanner.core.api.solver.ProblemFactChange;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class DeletePaintRealtimeChange implements ProblemFactChange<MixerSolution> {

    private final Paint paint;

    public DeletePaintRealtimeChange(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void doChange(ScoreDirector<MixerSolution> scoreDirector) {
        MixerSolution mixerSolution = scoreDirector.getWorkingSolution();
        Paint workingPaint = scoreDirector.lookUpWorkingObject(paint);
        if(workingPaint == null) {
            // The paint has already been deleted (the UI asked to changed the same paint twice), so do nothing
            return;
        }
        Optional<PaintMix> paintMixToRemove = mixerSolution.getPaintMixes()
            .stream()
            .filter(pm -> pm.getPaint().equals(paint))
            .findFirst();

        // A SolutionCloner does not clone problem fact lists (such as computerList)
        // Shallow clone the computerList so only workingSolution is affected, not bestSolution or guiSolution
        ArrayList<Paint> paintsList = new ArrayList<>(mixerSolution.getPaints());
        mixerSolution.setPaints(paintsList);
        // Remove the problem fact itself
        scoreDirector.beforeProblemFactRemoved(workingPaint);
        paintsList.remove(workingPaint);
        scoreDirector.afterProblemFactRemoved(workingPaint);

        ArrayList<PaintMix> paintMixList = new ArrayList<>(mixerSolution.getPaintMixes());
        mixerSolution.setPaintMixes(paintMixList);

        if(paintMixToRemove.isPresent()) {
            scoreDirector.beforeProblemFactRemoved(paintMixToRemove);
            paintMixList.remove(paintMixToRemove.get());
            scoreDirector.afterProblemFactRemoved(paintMixToRemove);
        }

        scoreDirector.triggerVariableListeners();
    }
}
