package test;

import java.io.Serializable;

public class O2OutputData implements Serializable {
    private int stationId;  //id
    private String day; //日期
    private double sLat;    //经度
    private double sLongitude; //纬度
    private double sAltitude; //海拔
    private String pressure; //气压
    private String temperature; //温度
    private String rh;       //湿度
    private double o2_pre;
    private double o2p_dg;
    private double o2_con;


    public int getStationId() {
        return stationId;
    }


    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getsLat() {
        return sLat;
    }

    public void setsLat(double sLat) {
        this.sLat = sLat;
    }

    public double getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public double getsAltitude() {
        return sAltitude;
    }

    public void setsAltitude(double sAltitude) {
        this.sAltitude = sAltitude;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public double getO2_pre() {
        return o2_pre;
    }

    public void setO2_pre(double o2_pre) {
        this.o2_pre = o2_pre;
    }

    public double getO2p_dg() {
        return o2p_dg;
    }

    public void setO2p_dg(double o2p_dg) {
        this.o2p_dg = o2p_dg;
    }

    public double getO2_con() {
        return o2_con;
    }

    public void setO2_con(double o2_con) {
        this.o2_con = o2_con;
    }
}
