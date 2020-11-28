package domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.optaplanner.examples.common.domain.AbstractPersistable;

public class Paint extends AbstractPersistable {

    protected int r;
    protected int g;
    protected int b;

    public Paint() {
        this(0, 0, 0);
    }

    public Paint(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Paint plus(Paint other) {
        int newR = (this.r + other.r) / 2;
        int newG = (this.g + other.g) / 2;
        int newB = (this.b + other.b) / 2;
        return new Paint(newR, newG, newB);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
