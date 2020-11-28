package org.rhoff95.mixer.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PlanningEntity
@XStreamAlias("PaintMix")
public class PaintMix extends AbstractPersistable {

    private static final Logger logger = LoggerFactory.getLogger(PaintMix.class);

    private Paint paint;
    private Integer amount;

    public PaintMix(Paint paint) {
        super();
        this.paint = paint;
        amount = 0;
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
        logger.debug("Setting amount to {}", amount);
        this.amount = amount;
    }
}
