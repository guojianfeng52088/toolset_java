package test;

import java.util.ArrayList;
import java.math.*;
import java.util.Calendar;
import java.util.List;

public class Test1 {

    public static final double pi = 3.1415926;

    public static void main(String[] args) {

        List<Data> dataList = new ArrayList<>();
        double upt =  upt(dataList);
    }


    /**
     * 计算体感温度
     */
    public static double upt(List<Data> dataList){

        for (Data data : dataList){
            Calendar cal = Calendar.getInstance();
            cal.setTime(data.getDay());
            int month = cal.get(Calendar.MONTH);
            double lat = data.getLongitude();
            double alt = data.getAltitude() * 0.01; //维度×0.01


        }
    }

    /**
     * 计算含氧量
     */
    public static double o2(){

    }

}
