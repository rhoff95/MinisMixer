package org.rhoff95.mixer.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
@XStreamAlias("PaintMix")
public class PaintMix extends AbstractPersistable {

    private Paint paint;
    private Integer amount;

    public PaintMix() {
        super();
        amount = 0;
    }

    public PaintMix(Paint paint) {
        this();
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @PlanningVariable(valueRangeProviderRefs = {"mixtureRange"})
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
