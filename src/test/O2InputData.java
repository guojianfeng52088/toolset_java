package test;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/20 01:27
 */
public class O2InputData implements Serializable {
    private int stationId;  //id
    private String day; //日期
    private double sLat;    //经度
    private double sLongitude; //纬度
    private double sAltitude; //海拔
    private String pressure; //气压
    private String temperature; //温度
    private String rh;       //湿度

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
}
