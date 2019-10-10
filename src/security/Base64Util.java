package security;

import org.apache.commons.codec.binary.Base64;

/**
* @description base64加解密
* @author guojianfeng
* @date 2019/10/10 19:13
*/
public class Base64Util {

  /**
  * @description 加密
  * @param param 加密前的字符串
  * @return base64密文
  * @author guojianfeng
  * @date 2019/10/10 19:13
  */
    public static String encodeByBase64(String param) {
        if (param == null || param.length() == 0 || "null".equalsIgnoreCase(param)) {
            return "";
        }
        return new String(new Base64().encode(param.getBytes()));
    }



    /**
    * @description base64解密
    * @param param base64密文
    * @return 明文
    * @author guojianfeng
    * @date 2019/10/10 19:18
    */
    public static String decodeByBase64(String param) throws Exception {
        if (param == null || param.length() == 0 || "null".equalsIgnoreCase(param)) {
            return "";
        }
        return new String(new Base64().decode(param.getBytes()));
    }
}
