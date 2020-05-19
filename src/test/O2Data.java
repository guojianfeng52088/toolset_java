package test;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/20 01:27
 */
public class O2Data implements Serializable {
    private int stationId;  //id
    private Date day; //日期
    private double sLat;    //经度
    private double sLongitude; //纬度
    private double sAltitude; //海拔
    private float pressure; //气压
    private float temperature; //温度
    private float rh;       //湿度

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
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

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getRh() {
        return rh;
    }

    public void setRh(float rh) {
        this.rh = rh;
    }
}
