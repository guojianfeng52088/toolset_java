package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Test1 {

    public static void main(String[] args) {

        List<UptData> uptDataList = new ArrayList<>();
        double upt = upt(uptDataList);

        List<O2Data> o2DataList = new ArrayList<>();
        double o2 = o2(o2DataList);
    }


    /**
     * 计算体感温度
     */
    public static double upt(List<UptData> uptDataList) {
        double rhc; //黄金分割？
        int rows = uptDataList.size();
        List<Float> rain = new ArrayList<>();
        List<Float> temperature = new ArrayList<>();
        List<Float> wind = new ArrayList<>();
        List<Double> rh = new ArrayList<>();

        for (UptData data : uptDataList) {
            rain.add(data.getRain());
            temperature.add(data.getTemperature());
            wind.add(data.getWind());
            rh.add(data.getRh() * 0.01);
        }

        double upt = 0;
        for (int i = 0; i < rows; i++) {
            UptData data = uptDataList.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(data.getDay());
            int month = cal.get(Calendar.MONTH);  //获取月份
            double longitude = data.getsLongitude() * 0.01; //纬度 × 0.01；
            double altitude = data.getsAltitude();       //海拔

            double mct = 22.7 * (1.0 - 0.3 * Math.sin((longitude - 23.5) * Math.PI / 180.0)) - Math.abs(0.3 * Math.cos((month - 1.0) * 15.0 * Math.PI / 180.0)) - 2.0 * Math.tan(1.5 * 6.5 * altitude * Math.PI / 180);
            double tv;
            double trh;
            if (temperature.get(i) > mct) { //1. 高温段，温度大于mct[i]
                tv = -0.03 * (temperature.get(i) - mct) * wind.get(i);  //风速修正项
                if (rain.get(i) > 0) { //1.1 有降水
                    rhc = 0.618;
                    if (rh.get(i) > rhc) {
                        trh = 14.0 * (Math.exp(0.1 * (temperature.get(i) - mct) * (rh.get(i) - rhc)) - 1);
                    } else {
                        trh = 0;
                    }
                    upt = temperature.get(i) + trh + tv;
                } else if (rain.get(i) == 0) { //1.2 无降水
                    rhc = 0.5;
                    if (rh.get(i) > rhc) {
                        trh = 14.0 * (Math.exp(0.1 * (temperature.get(i) - mct) * (rh.get(i) - rhc)) - 1);
                    } else {
                        trh = 0;
                    }
                    upt = temperature.get(i) + trh + tv;
                }
            } else if (temperature.get(i) < mct) { //2. 低温段，温度小于mct[i]
                tv = -0.01 * (mct - temperature.get(i)) * wind.get(i);
                if (rain.get(i) > 0) { //2.1 有降水（降雪）时
                    rhc = 0.618;
                    if (rh.get(i) > rhc) {
                        trh = 14.0 * (Math.exp(0.02 * (mct - temperature.get(i)) * (rh.get(i) - rhc)) - 1);
                    } else {
                        trh = 0;
                    }
                    upt = temperature.get(i) + trh + tv;
                } else if (rain.get(i) == 0) { // 2.2 无降水时
                    rhc = 0.5;
                    if (rh.get(i) > rhc) {
                        trh = 14.0 * (Math.exp(0.1 * (temperature.get(i) - mct) * (rh.get(i) - rhc)) - 1);
                    } else {
                        trh = 0;
                    }
                    upt = temperature.get(i) + trh + tv;
                }
            }
            double dt = 22.7 - mct;

            double upt_threshold[] = {35.0, 30.0, 25.0, 22.7, 18.0, 13.0, 5.0, -5.0, -15.0, -25.0};
            int dg = 4;
            int degree = 0;
            if (upt > upt_threshold[0] - dt) {
                degree = dg;
            } else if (upt < upt_threshold[9] - dt) {
                degree = dg - 10;
            } else {
                for (int j = 0; j < 9; j++) {
                    if ((upt < (upt_threshold[j] - dt)) && (upt > upt_threshold[j + 1] - dt)) {
                        degree = dg - j;
                    }
                }
            }
        }

        upt = new BigDecimal(upt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        upt = pd.DataFrame(c_[22.7 - mct, mct, upt, degree],columns = ['dt', 'mct', 'upt', 'degree']);
        return upt;
    }

    /**
     * 计算含氧量
     */
    public static double o2(List<O2Data> o2DataList) {
        int N = 32;
        double p2 = 101.325;
        float t0 = 273.15F;
        double ppm = 0.20948; // #round_((O2p_rh / p1) , 2) #氧气的体积分数
        double O2_threhold[] = {15.70, 14.95, 14.34, 13.60, 12.12, 11.52};
        int dg = 1;
        int rows = o2DataList.size();
        double degree = 0;
        List<Float> p1 = new ArrayList<>();
        List<Float> tav = new ArrayList<>();
        List<Double> rhav = new ArrayList<>();

        List<Double> o2p = new ArrayList<>();
        List<Double> o2c1 = new ArrayList<>();

        for (O2Data o2Data : o2DataList) {
            p1.add(o2Data.getPressure());
            tav.add(o2Data.getTemperature() + t0);
            rhav.add(o2Data.getRh() * 0.01);

            o2p.add(o2Data.getPressure() * 0.1 * ppm);
            o2c1.add(0.8062 * o2Data.getPressure() * 100 / (o2Data.getTemperature() + t0));
        }

        for (int i = 0; i<o2DataList.size(); i++){
            for (int j = 0; j < 6;j++){
                if ((o2p.get(i) < O2_threhold[j]) && o2p.get(i) > O2_threhold[j+1]){
                    degree = j+1;
                }
            }
        }

        O2_result = pd.DataFrame(c_[O2p,degree,O2c1],columns=['O2_pre','O2p_dg','O2_con'])
        O2_result = pd.concat([O2_data.iloc[:,:5],O2_result],axis=1)
        return(O2_result)
    }

}
