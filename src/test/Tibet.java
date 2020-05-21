package test;

import java.util.Date;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/21 23:18
 */
public class Tibet {
    private int stationId;
    private String day;
    private String Rain;
    private String Pressure;
    private String Wind;
    private String Temperature;
    private String ShuiQY;
    private String RH;

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

    public String getRain() {
        return Rain;
    }

    public void setRain(String rain) {
        Rain = rain;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getShuiQY() {
        return ShuiQY;
    }

    public void setShuiQY(String shuiQY) {
        ShuiQY = shuiQY;
    }

    public String getRH() {
        return RH;
    }

    public void setRH(String RH) {
        this.RH = RH;
    }
}
