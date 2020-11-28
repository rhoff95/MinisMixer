package domain;

import java.util.ArrayList;
import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

@PlanningSolution
public class MixtureSolution {

    private Paint targetPaint;
    private List<Paint> paints = new ArrayList<>();

    private List<PaintMix> paintMixes = new ArrayList<>();

    private HardMediumSoftLongScore score;

    @ProblemFactProperty
    public Paint getTargetPaint() {
        return targetPaint;
    }

    public void setTargetPaint(Paint targetPaint) {
        this.targetPaint = targetPaint;
    }

    @ProblemFactCollectionProperty
    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }

    public List<PaintMix> getPaintMixes() {
        return paintMixes;
    }

    public void setPaintMixes

    @PlanningScore
    public HardMediumSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftLongScore score) {
        this.score = score;
    }
}
