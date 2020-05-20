package test;

import java.io.Serializable;

public class O2OutputData extends O2InputData implements Serializable {
    private double O2_pre;
    private double O2p_dg;
    private double O2_con;

    public double getO2_pre() {
        return O2_pre;
    }

    public void setO2_pre(double o2_pre) {
        O2_pre = o2_pre;
    }

    public double getO2p_dg() {
        return O2p_dg;
    }

    public void setO2p_dg(double o2p_dg) {
        O2p_dg = o2p_dg;
    }

    public double getO2_con() {
        return O2_con;
    }

    public void setO2_con(double o2_con) {
        O2_con = o2_con;
    }
}
