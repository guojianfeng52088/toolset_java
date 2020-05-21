package test;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/21 23:12
 */
public class Station {
    private int stationId;
    private double sLat;
    private double sLongitude;
    private double sAltitude;
    private String stationName;
    private String sSM;


    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getsSM() {
        return sSM;
    }

    public void setsSM(String sSM) {
        this.sSM = sSM;
    }
}
