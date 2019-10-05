package random;

import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

/**
 * @Description 随机数工具类
 * @Author pc
 * @Date 2019/10/05 23:23
 */
public class RandomUtil {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * @param length 需要字符串的长度
     * @Description 获取时间戳
     * @Author guojianfeng
     * @Date 2019/10/05 23:26
     * @Return
     */
    public static String genTimeStamp(int length) {
        String currentStamp = String.valueOf(System.currentTimeMillis());

        int strLength = currentStamp.length();
        if (strLength > length) {
            return currentStamp.substring(strLength - length);
        }
        return currentStamp;
    }

    /**
     * @param length 长度
     * @Description 生成随机码
     * @Author guojianfeng
     * @Date 2019/10/05 23:34
     * @Return
     */
    public static synchronized String genRandomCode(int length) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < length) {
            int num = RandomUtils.nextInt(0, 9);
            sb.append(num);
            i++;
        }
        return sb.toString();
    }

    /**
     * @param
     * @Description 生成短id（32位）
     * @Author guojianfeng
     * @Date 2019/10/05 23:37
     * @Return
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }


    /**
     * @param
     * @Description 生成注册/邀请码（24位）
     * @Author guojianfeng
     * @Date 2019/10/05 23:37
     * @Return
     */
    public static String generateInvitationCode() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}
