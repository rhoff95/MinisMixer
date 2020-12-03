package org.rhoff95.mixer.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.persistence.xstream.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScoreXStreamConverter;
import org.rhoff95.mixer.constants.Vallejo;

@PlanningSolution
@XStreamAlias("Mixer")
public class MixerSolution extends AbstractPersistable {

    private Paint targetPaint;
    private List<Paint> paints;

    private List<PaintMix> paintMixes;

    @XStreamConverter(HardMediumSoftLongScoreXStreamConverter.class)
    private HardMediumSoftLongScore score;

    public MixerSolution() {
        super();
    }

    public MixerSolution(long id) {
        super(id);
    }

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

    @PlanningEntityCollectionProperty
    public List<PaintMix> getPaintMixes() {
        return paintMixes;
    }

    public void setPaintMixes(List<PaintMix> paintMixes) {
        this.paintMixes = paintMixes;
    }

    @PlanningScore
    public HardMediumSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @ValueRangeProvider(id = "mixtureRange")
    public CountableValueRange<Integer> getMixtureRange() {
        return ValueRangeFactory.createIntValueRange(0, 100);
    }
}
