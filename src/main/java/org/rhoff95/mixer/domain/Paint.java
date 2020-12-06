package org.rhoff95.mixer.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Paint")
public class Paint extends AbstractPersistable {

    protected final float r;
    protected final float g;
    protected final float b;
    protected final String label;

    public Paint() {
        this(0, 0, 0);
    }

    public Paint(Paint o) {
        this(o.r, o.g, o.b, o.label);
    }

    public Paint(float r, float g, float b) {
        this(r, g, b, "");
    }

    public Paint(float r, float g, float b, String label) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.label = label;
    }

    public Paint plus(Paint other) {
        float newR = (this.r + other.r) / 2;
        float newG = (this.g + other.g) / 2;
        float newB = (this.b + other.b) / 2;
        return new Paint(newR, newG, newB);
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public String getLabel() {
        return label;
    }
}