package security;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
* @description url加密解密工具
* @author guojianfeng
* @date 2019/10/10 19:20
*/
public class UrlEncodeUtil {

    /**
    * @description url加密
    * @param param 明文
    * @return URL密文
    * @author guojianfeng
    * @date 2019/10/10 19:21
    */
    public static String urlEncode(String param) throws Exception {
        if (param == null || param.length() == 0 || "null".equalsIgnoreCase(param)) {
            return "";
        }
        return URLEncoder.encode(param, "UTF-8");
    }


    /**
    * @description url解密
    * @param param url密文
    * @return 明文
    * @author guojianfeng
    * @date 2019/10/10 19:21
    */
    public static String urlDecode(String param) throws Exception {
        if (param == null || param.length() == 0 || "null".equalsIgnoreCase(param)) {
            return "";
        }
        return URLDecoder.decode(param, "UTF-8");
    }

}
