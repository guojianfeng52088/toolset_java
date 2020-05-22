package test;

import com.alibaba.excel.EasyExcel;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Test1 {

    public static void main(String[] args) {

        try {
            //读取的文件位置
            String stationInput = "D:\\test\\西藏station.xlsx";
            String tibetInput = "D:\\test\\tibet_data.xlsx";

            //输出的文件位置
            String uptOutput = "D:\\test\\uptOuput.xlsx";
            String o2Output = "D:\\test\\o2Ouput.xlsx";

            //读取excel
            List<Station> stationList = EasyExcel.read(stationInput, Station.class, new StationExceptionListener()).sheet().doReadSync();
            List<Tibet> tibetList = EasyExcel.read(tibetInput, Tibet.class, new TibetExceptionListener()).sheet().doReadSync();

            //存放输出的数据
            List<UptInputData> uptInputDataList = new ArrayList<>();
            List<O2InputData> o2InputDataList = new ArrayList<>();

            //构造数据
            if (stationList != null && tibetList != null) {
                for (Tibet t : tibetList){
                    if (t.isContainNull()){
                        continue;
                    }
                    int stationId = t.getStationId();
                    for (Station s : stationList){
                        if (stationId == s.getStationId()){
                            UptInputData uptInputData = new UptInputData();
                            uptInputData.setStationId(stationId);
                            uptInputData.setDay(t.getDay());
                            uptInputData.setsLat(s.getsLat());
                            uptInputData.setsLongitude(s.getsLongitude());
                            uptInputData.setsAltitude(s.getsAltitude());
                            uptInputData.setRain(t.getRain());
                            uptInputData.setTemperature(t.getTemperature());
                            uptInputData.setWind(t.getWind());
                            uptInputData.setRh(t.getRH());

                            O2InputData o2InputData = new O2InputData();
                            o2InputData.setStationId(stationId);
                            o2InputData.setDay(t.getDay());
                            o2InputData.setsLat(s.getsLat());
                            o2InputData.setsLongitude(s.getsLongitude());
                            o2InputData.setsAltitude(s.getsAltitude());
                            o2InputData.setPressure(t.getPressure());
                            o2InputData.setTemperature(t.getTemperature());
                            o2InputData.setRh(t.getRH());

                            o2InputDataList.add(o2InputData);
                            uptInputDataList.add(uptInputData);
                            break;
                        }
                    }
                }
            }
            if (uptInputDataList != null){
                //计算upt
                List<UptOutputData> uptOutputDataList = upt(uptInputDataList);
                //开始写upt数据的excel
                FileOutputStream fileOutputStream = new FileOutputStream(uptOutput);
                EasyExcel.write(fileOutputStream, UptOutputData.class).sheet("sheet").doWrite(uptOutputDataList);
            }
            if (o2InputDataList != null){
                //计算o2
                List<O2OutputData> o2OutputList = o2(o2InputDataList);
                //开始写o2数据的excel
                FileOutputStream fileOutputStream = new FileOutputStream(o2Output);
                EasyExcel.write(fileOutputStream, O2OutputData.class).sheet("sheet").doWrite(o2OutputList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 计算体感温度
     */
    public static List<UptOutputData> upt(List<UptInputData> uptInputDataList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        double rhc; //黄金分割？
        int rows = uptInputDataList.size();
        //input
        List<Float> rain = new ArrayList<>();
        List<Float> temperature = new ArrayList<>();
        List<Float> wind = new ArrayList<>();
        List<Double> rh = new ArrayList<>();

        //output
        List<Double> dtList = new ArrayList<>();
        List<Double> mctList = new ArrayList<>();
        List<Double> uptList = new ArrayList<>();
        List<Integer> degreeList = new ArrayList<>();

        for (UptInputData data : uptInputDataList) {
            rain.add(Float.valueOf(data.getRain()));
            temperature.add(Float.valueOf(data.getTemperature()));
            wind.add(Float.valueOf(data.getWind()));
            rh.add(Double.valueOf(data.getRh()) * 0.01);
        }

        double upt = 0;
        for (int i = 0; i < rows; i++) {
            UptInputData data = uptInputDataList.get(i);
            Calendar cal = Calendar.getInstance();
            int month;
            try {
                cal.setTime(sdf.parse(data.getDay()));
                month = cal.get(Calendar.MONTH);  //获取月份
            }catch (Exception e){
                month = 0;
            }
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
            dtList.add(dt);
            mctList.add(mct);
            try {
                upt = new BigDecimal(upt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }catch (Exception e){
                upt = 0;
            }
            uptList.add(upt);
            degreeList.add(degree);
        }

        //构造upt输出数据
        List<UptOutputData> outputDataList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            UptInputData inputData = uptInputDataList.get(i);
            UptOutputData outputData = new UptOutputData();
            outputData.setStationId(inputData.getStationId());
            outputData.setDay(inputData.getDay());
            outputData.setsLat(inputData.getsLat());
            outputData.setsLongitude(inputData.getsLongitude());
            outputData.setsAltitude(inputData.getsAltitude());
            outputData.setRain(inputData.getRain());
            outputData.setTemperature(inputData.getTemperature());
            outputData.setWind(inputData.getWind());
            outputData.setRh(inputData.getRh());
            outputData.setDt(dtList.get(i));
            outputData.setMct(mctList.get(i));
            outputData.setUpt(uptList.get(i));
            outputData.setDegree(degreeList.get(i));

            outputDataList.add(outputData);
        }

        return outputDataList;
    }

    /**
     * 计算含氧量
     */
    public static List<O2OutputData> o2(List<O2InputData> o2InputDataList) {
        int N = 32;
        double p2 = 101.325;
        float t0 = 273.15F;
        double ppm = 0.20948; // #round_((O2p_rh / p1) , 2) #氧气的体积分数
        double O2_threhold[] = {15.70, 14.95, 14.34, 13.60, 12.12, 11.52};
        int dg = 1;
        int rows = o2InputDataList.size();
        List<Float> p1 = new ArrayList<>();
        List<Float> tav = new ArrayList<>();
        List<Double> rhav = new ArrayList<>();

        List<Double> o2p = new ArrayList<>();
        List<Integer> degree = new ArrayList<>();
        List<Double> o2c1 = new ArrayList<>();

        for (O2InputData o2InputData : o2InputDataList) {
            p1.add(Float.valueOf(o2InputData.getPressure()));
            tav.add(Float.valueOf(o2InputData.getTemperature()) + t0);
            rhav.add(Double.valueOf(o2InputData.getRh()) * 0.01);

            o2p.add(Double.valueOf(o2InputData.getPressure()) * 0.1 * ppm);
            o2c1.add(0.8062 * Double.valueOf(o2InputData.getPressure()) * 100 / (Double.valueOf(o2InputData.getTemperature()) + t0));
        }

        for (int i = 0; i < o2InputDataList.size(); i++) {
            for (int j = 0; j < 6; j++) {
                if ((o2p.get(i) < O2_threhold[j]) && o2p.get(i) > O2_threhold[j + 1]) {
                    degree.add(j + 1);
                } else {
                    degree.add(0);
                }
            }
        }

        List<O2OutputData> outputDataList = new ArrayList<>();

        //构造o2输出数据
        for (int i = 0; i < o2InputDataList.size(); i++) {
            O2InputData o2InputData = o2InputDataList.get(i);
            O2OutputData outputData = new O2OutputData();
            outputData.setStationId(o2InputData.getStationId());
            outputData.setDay(o2InputData.getDay());
            outputData.setsLat(o2InputData.getsLat());
            outputData.setsLongitude(o2InputData.getsLongitude());
            outputData.setsAltitude(o2InputData.getsAltitude());
            outputData.setPressure(o2InputData.getPressure());
            outputData.setTemperature(o2InputData.getTemperature());
            outputData.setRh(o2InputData.getRh());
            outputData.setO2_pre(o2p.get(i));
            outputData.setO2p_dg(degree.get(i));
            outputData.setO2_con(o2c1.get(i));

            outputDataList.add(outputData);
        }
        return outputDataList;
    }
}
