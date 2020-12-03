package org.rhoff95.mixer.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Paint")
public class Paint extends AbstractPersistable {

    protected float r;
    protected float g;
    protected float b;

    public Paint(Paint o) {
        this(o.r, o.g, o.b);
    }

    public Paint() {
        this(0, 0, 0);
    }

    public Paint(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
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

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
}
