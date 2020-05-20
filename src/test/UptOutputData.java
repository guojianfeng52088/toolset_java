package test;

import java.io.Serializable;

public class UptOutputData extends UptInputData implements Serializable {
    private double dt;
    private double mct;
    private double upt;
    private int degree;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getMct() {
        return mct;
    }

    public void setMct(double mct) {
        this.mct = mct;
    }

    public double getUpt() {
        return upt;
    }

    public void setUpt(double upt) {
        this.upt = upt;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
