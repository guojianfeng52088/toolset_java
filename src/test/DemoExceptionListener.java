package test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author pc
 * @Date 2020/05/22 02:11
 */
public class DemoExceptionListener extends AnalysisEventListener<Tibet> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<Tibet> list = new ArrayList<>();

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
//    @Override
//    public void onException(Exception exception, AnalysisContext context) {
//        // 如果是某一个单元格的转换异常 能获取到具体行号
//        // 如果要获取头的信息 配合invokeHeadMap使用
////        try {
////
////            System.out.println("---------------onException------------");
////            if (exception instanceof ExcelDataConvertException) {
////                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
////                System.out.println("第"+excelDataConvertException.getColumnIndex()+"行，发生异常");
////
////            }
////        }catch (ExcelAnalysisException e){
//            System.out.println("catch-----------");
////        }
//
//
//
//    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Tibet data, AnalysisContext context) {
        if (data != null){

        }
        System.out.println("---------------invoke------------");

        System.out.println("stationId="+data.getStationId());
        System.out.println("day = "+data.getDay());
        System.out.println("rain = "+data.getRain());
        System.out.println("pressure = "+data.getPressure());
        System.out.println("wind = "+data.getWind());
        System.out.println("temperature = "+data.getTemperature());
        System.out.println("shuiQy = "+data.getShuiQY());
        if (data.getDay() == null){
            return;
        }
        if (data.getRain() == null){
            return;
        }
        if (data.getPressure() == null){
            return;
        }
        if (data.getWind() == null){
            return;
        }
        if (data.getTemperature() == null){
            return;
        }
        if (data.getShuiQY() == null){
            return;
        }
        if (data.getRH() == null){
            return;
        }
        System.out.println("stationId="+data.getStationId());
        System.out.println("day = "+data.getDay());

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
//            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
