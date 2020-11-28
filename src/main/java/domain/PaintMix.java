package domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class PaintMix extends Paint {

    private int amount;

    public PaintMix(Paint paint) {
        super(paint.r, paint.b, paint.g);
        amount = 0;
    }

    @PlanningVariable
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
