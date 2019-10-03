import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description xml工具类
 * @Author pc
 * @Date 2019/10/03 11:32
 */
public class XmlUtil {

    /**
      * @Description map转xml
      * @Author guojianfeng
      * @Date 2019/10/03 11:40
      * @param parameters key和value都为string类型的map参数对象
      * @Return
      */

    public static String mapToXml(Map<String, String> parameters){
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        sb.append("<xml>");
        for (String key : keys){
            String value = parameters.get(key);
            if (null != value && !"".equals(value) && !"appkey".equals(key)){
                sb.append("<"+key+">"+value+"</"+key+">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
