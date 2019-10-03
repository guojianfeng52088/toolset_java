package verify;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description 参数校验工具
 * @Author pc
 * @Date 2019/10/03 15:24
 */
public class ParamVerify {

    /**
     * @param value 需要校验的参数
     * @Description 校验对象是否为空，为空返回true，非空返回false
     * @Author guojianfeng
     * @Date 2019/10/03 15:27
     * @Return true:空值，false非空值
     */
    public static boolean checkParamIsEmpty(Object value) {
        if (null == value || "".equals(value.toString().trim()) || "null".equals(value) || "null".equals(value.toString().trim())) {
            return true;
        }
        return false;
    }


    /**
     * @param values 多个待校验的参数
     * @Description 校验多个参数是否为空
     * @Author guojianfeng
     * @Date 2019/10/03 15:28
     * @Return
     */
    public static boolean checkParamsIsEmpty(Object... values) {
        for (Object obj : values) {
            if (checkParamIsEmpty(obj)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param value 需要校验的字符串
     * @Description 校验字符串是否是json格式
     * @Author guojianfeng
     * @Date 2019/10/03 15:31
     * @Return
     */
    public static boolean checkParamIsJson(String value) {
        if (checkParamIsEmpty(value)) {
            return false;
        }
        try {
            JSONObject.parseObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    /**
     * @param value 需要校验的字符串
     * @Description 校验字符串是否是jsonArray格式
     * @Author guojianfeng
     * @Date 2019/10/03 15:41
     * @Return
     */
    public static boolean checkParamIsJsonArray(String value) {
        if (checkParamIsEmpty(value)) {
            return false;
        }
        try {
            JSONArray.parseArray(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    /**
     * @param value 需要校验的字符串
     * @Description 校验字符串是否可以转换为整型
     * @Author guojianfeng
     * @Date 2019/10/03 15:43
     * @Return
     */
    public static boolean checkParamIsInt(String value) {
        if (checkParamIsEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
     * @param value 需要校验的字符串
     * @Description 校验字符串是否可以转换为bigDecimal类型
     * @Author guojianfeng
     * @Date 2019/10/03 15:47
     * @Return
     */
    public static boolean checkParamIsDecimal(String value) {
        if (checkParamIsEmpty(value)) {
            return false;
        }
        try {
            new BigDecimal(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
     * @param value 需要校验的字符串
     * @Description 校验字符串是否可以转换为long类型
     * @Author guojianfeng
     * @Date 2019/10/03 15:48
     * @Return
     */
    public static boolean checkParamIsLong(String value) {
        if (checkParamIsEmpty(value)) {
            return false;
        }
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
      * @Description 校验实体对象是否为空
      * @Author guojianfeng
      * @Date 2019/10/03 15:58
      * @param bean 需要校验的bean对象
      * @Return
      */
    public static boolean checkBeanNotEmpty(Object bean){
        Class clazz = bean.getClass();
        try {
            boolean b = true;
            for (;clazz != Object.class;clazz = clazz.getSuperclass()){
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields){
                    String name = field.getName();
                    name = name.substring(0,1).toUpperCase()+name.substring(1);
                    Method m = clazz.getMethod("get"+name, new Class[0]);
                    Object obj = m.invoke(bean, new Object[0]);
                    if (checkParamIsEmpty(obj)){
                        b = false;
                        break;
                    }
                }
            }
            return b;
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return false;
    }


    /**
      * @Description 判断数组中是否包含某个key
      * @Author guojianfeng
      * @Date 2019/10/03 16:01
      * @param arr 数组
     * @param targetValue 需要查找的key
      * @Return
      */
    public static boolean checkArrayKey(String[] arr, String targetValue){
        return Arrays.asList(arr).contains(targetValue);
    }


    /**
      * @Description 校验map中是否包含数组emptyArray中的元素（感觉有点多余，如果只是校验map中是否含有一个key的时候，可以用map.containsKey()方法）
      * @Author guojianfeng
      * @Date 2019/10/03 16:06
      * @param emptyArray 字符串数组
     * @param paramMap 需要查找的map
      * @Return
      */
    public static String checkEmpty(String[] emptyArray, Map<String, String> paramMap){
        for (String str : emptyArray){
            if (checkParamIsEmpty(paramMap.get(str))){
                return str + "校验错误，不可以为空";
            }
        }
        return null;
    }


    //    /**
//     * @param map     需要转换的map
//     * @param isUpper 是否要转为大写,如果false则转为小写
//     * @return
//     * @description 将map里边的key首字母转换为大写或者小写
//     * @author chuck
//     * @create 2018-08-01 14:29:05
//     */
//    public static Map mapKeyPrefixTransForm(Map<String, Object> map, boolean isUpper) {
//
//        //校验参数是否为空
//        if (ParamVerify.checkParamIsEmpty(map)) {
//            throw new ParamException(GlobleCode.PARAM_NOT_NULL);
//        }
//
//        Set<String> keySet = map.keySet();
//        Iterator<String> it = keySet.iterator();
//
//        //接收转换后的参数的map
//        Map<String, String> transMap = new HashMap<String, String>();
//
//        while (it.hasNext()) {
//            String key = it.next();
//            //key为空或者key值长度小于1
//            if (ParamVerify.checkParamIsEmpty(key) || key.length() < 1) {
//                throw new ParamException(GlobleCode.PARAM_FORMAT_EXCEPTION);
//            }
//            //获取当前key值对应的value
//            String value = map.get(key).toString();
//
//            //转换后的新key
//            String newKey = "";
//            if (isUpper) {
//                //取出首字母,如果已经大写
//                if (Character.isUpperCase(key.charAt(0))) {
//                    newKey = key;
//                } else {
//                    newKey = new StringBuilder().append(Character.toUpperCase(key.charAt(0))).append(key.substring(1)).toString();
//                }
//            } else {
//                //如果已经小写
//                if (Character.isLowerCase(key.charAt(0))) {
//                    newKey = key;
//                } else {
//                    newKey = new StringBuilder().append(Character.toLowerCase(key.charAt(0))).append(key.substring(1)).toString();
//                }
//            }
//            transMap.put(newKey, value);
//        }
//        return transMap;
//    }

}

































