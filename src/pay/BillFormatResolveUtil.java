package pay;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Description 支付宝、微信对账单格式解析工具
 * @Author pc
 * @Date 2019/10/04 16:23
 */
public class BillFormatResolveUtil {

    private static Logger logger = Logger.getLogger(BillFormatResolveUtil.class.getName());

    public static List<Object> resolveBill(String filePath) {

        if (null == filePath || filePath.length() <= 0) {
            logger.info("对账单文件路径不存在");
        }

        //存放对账单对象的list
        List<Object> billList = new ArrayList<>();

        File file = new File(filePath);

        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\,");
                /**
                 * 在这里取对应的行号和列号的字段就可以了，例如头几行是表头，不是需要的账单信息，
                 * 从第6行才是真正的账单信息，就从第6行开始取数据
                 */
                if (split.length > 5) {
                    String externalBillNo = split[0].trim();    //第一列是支付宝或微信生成的订单号
                    String orderNo = split[1].trim();           //我方的订单号
                    /**
                     * ...往后以此类推，按照对应的列数取出你需要的字段
                     */
                    //组装账单对象，往list中添加即可
                    billList.add(new Object());
                } else {
                    //数据没有超过6行，说明全是表头信息，没有账单信息
                    logger.info("没有相关账单信息");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }
}
