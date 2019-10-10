package security;

import java.security.MessageDigest;

/**
 * @Description MD5工具类
 * @Author pc
 * @Date 2019/10/04 14:13
 */
public class MD5Util {


    /**
      * @Description 加盐方式生成MD5密文
      * @Author guojianfeng
      * @Date 2019/10/04 14:17
      * @param string 需要加密的字符串
      * @Return
      */
    public static String MD5Password(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(string.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            string = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return string;
    }


    /**
      * @Description 普通MD5加密方式
      * @Author guojianfeng
      * @Date 2019/10/04 14:20
      * @param string 需要加密的字符串
      * @Return
      */
    public static String MD5(String string){
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = string.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("md5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
