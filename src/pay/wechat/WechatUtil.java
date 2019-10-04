package pay.wechat;



import md5.MD5Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description 微信支付中用到的工具
 * @Author pc
 * @Date 2019/10/04 13:54
 */
public class WechatUtil {


    /**
      * @Description 生成微信sign
      * @Author guojianfeng
      * @Date 2019/10/04 14:25
      * @param map 参数map
     * @param key key
      * @Return
      */
    public static String getWXSign(Map<String, String> map, String key){
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()){
            if (entry.getValue() != null && entry.getValue() != ""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i<size; i++){
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "=key=" + key;
        result = MD5Util.MD5Password(result).toUpperCase();
        return result;
    }

}
