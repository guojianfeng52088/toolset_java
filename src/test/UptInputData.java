package test;

import java.io.Serializable;
import java.util.Date;

public class UptInputData implements Serializable {

    private int stationId;  //id
    private String day; //日期
    private double sLat;    //
    private double sLongitude; //纬度
    private double sAltitude; //海拔
    private String rain; //降雨量
    private String temperature; //温度
    private String wind;     //风力
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

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }
}
