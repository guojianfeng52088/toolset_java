package test;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/21 23:18
 */
public class Tibet {

    private int stationId;
    private String day;
    private String rain;
    private String pressure;
    private String wind;
    private String temperature;
    private String shuiQY;
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
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getShuiQY() {
        return shuiQY;
    }

    public void setShuiQY(String shuiQY) {
        this.shuiQY = shuiQY;
    }

    public String getRH() {
        return RH;
    }

    public void setRH(String RH) {
        this.RH = RH;
    }

    public boolean isContainNull(){

        if (this.day == null || this.day.length() == 0 || this.day.equals("NULL")){
            return true;
        }
        if (this.rain == null || this.rain.length() == 0 || this.rain.equals("NULL")){
            return true;
        }
        if (this.pressure == null || this.pressure.length() == 0 || this.pressure.equals("NULL")){
            return true;
        }
        if (this.wind == null || this.wind.length() == 0 || this.wind.equals("NULL")){
            return true;
        }
        if (this.temperature == null || this.temperature.length() == 0 || this.temperature.equals("NULL")){
            return true;
        }
        if (this.shuiQY == null || this.shuiQY.length() == 0 || this.shuiQY.equals("NULL")){
            return true;
        }
        if (this.RH == null || this.RH.length() == 0 || this.RH.equals("NULL")){
            return true;
        }
        return false;
    }
}
