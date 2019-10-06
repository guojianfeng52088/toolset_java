package mapandlist;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description Map工具类
 * @Author pc
 * @Date 2019/10/04 20:56
 */
public class MapUtil {

    /**
     * @param map       待转换的map对象
     * @param beanClass 需要转成的目标对象
     * @Description Map转bean对象
     * @Author guojianfeng
     * @Date 2019/10/04 20:59
     * @Return
     */
    public static Object mapToObject(Map<String, String> map, Class<?> beanClass) {
        if (map == null || map.size() <= 0)
            return null;
        try {
            Object obj = beanClass.newInstance();
            boolean ret = true;
            Class oo = obj.getClass();
            List<Class> clazzs = new ArrayList<>();
            while (ret) {
                clazzs.add(oo);
                oo = oo.getSuperclass();
                if (oo == null || oo == Object.class)
                    break;
            }

            for (int i = 0; i < clazzs.size(); i++) {
                Field[] fields = clazzs.get(i).getDeclaredFields();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    //由字符串转换回对象对应的类型
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(obj, map.get(field.getName()));
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param obj 需要转的Object对象
     * @Description 对象转map
     * @Author guojianfeng
     * @Date 2019/10/04 21:09
     * @Return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null)
            return null;
        try {
            boolean ret = true;
            Class oo = obj.getClass();
            List<Class> clazzs = new ArrayList<Class>();
            while (ret) {
                clazzs.add(oo);
                oo = oo.getSuperclass();
                if (oo == null || oo == Object.class) break;
            }

            Map<String, Object> map = new HashMap<String, Object>();

            for (int i = 0; i < clazzs.size(); i++) {
                Field[] declaredFields = clazzs.get(i).getDeclaredFields();
                for (Field field : declaredFields) {
                    int mod = field.getModifiers();
                    //过滤 static 和 final 类型
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }
            }
            return map;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param
     * @return
     * @description: map转bean
     * @author: guojianfeng
     * @date: 2019-10-04 21:31
     */
    public static <T> T mapToBean(Class<T> clazz, Map map) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}