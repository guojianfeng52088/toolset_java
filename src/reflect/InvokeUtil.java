package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * @description: 反射工具
 * @author: guojianfeng
 * @date: 2019/10/10 16:19
 */
public class InvokeUtil {

    private static Logger log = Logger.getLogger(InvokeUtil.class.getName());

    //反射类所在包路径
    private static final String ROOT_PACKAGE_PATH = "";

    /**
     * @param className  包名+类名
     * @param methodName 反射方法
     * @param bean       参数
     * @description 执行反射（单个参数）
     * @author guojianfeng
     * @date 2019/10/10 16:24
     */
    public static Object executeInvoke(String className, String methodName, Object bean) {
        return invokeMethod(className, methodName, bean);
    }

    /**
     * @param className        包名+类名
     * @param methodName       执行方法
     * @param methodParameters 参数值
     * @description 执行反射调用（多个参数）
     * @author guojianfeng
     * @date 2019/10/10 16:27
     */
    public static Object executeInvoke(String className, String methodName, Object[] methodParameters) {
        return invokeMethod(className, methodName, methodParameters);
    }

    /**
     * @param className        包名+类名
     * @param methodName       执行方法
     * @param methodParameters 参数值
     * @param parameterClasses 参数类型
     * @description 执行反射调用（多个参数）
     * @author guojianfeng
     * @date 2019/10/10 16:26
     */
    public static Object executeInvoke(String className, String methodName, Object[] methodParameters, Class... parameterClasses) {
        return invokeMethod(className, methodName, methodParameters);
    }


    /**
     * @param classUrl   包名+类名
     * @param methodName 执行方法
     * @param bean       参数值
     * @description 执行全路径反射调用（单个参数）
     * @author guojianfeng
     * @date 2019/10/10 16:28
     */
    public static Object executeFullPathInvoke(String classUrl, String methodName, Object bean) {
        return invokeMethod(classUrl, methodName, bean);
    }


    /**
     * 反射执行方法
     *
     * @param classPackage
     * @param methodName
     * @param params
     * @return 执行方法返回参数 null 代表无返回值
     */
    private static Object invokeMethod(String classPackage, String methodName, Object... params) {
        String classPackageUrl = "";
        try {
            classPackageUrl = InvokeUtil.ROOT_PACKAGE_PATH + "." + classPackage;
            Class cls = Class.forName(classPackageUrl);
            Method[] methods = cls.getDeclaredMethods();
            Method m = null;
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    m = method;
                    break;
                }
            }
            return m.invoke(cls.newInstance(), params);
        } catch (InvocationTargetException e) {
            // 抛出目标异常
            Throwable t = e.getTargetException();
            throw (RuntimeException) t;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[InvokeUtil-invokeMethod] 反射调用异常,包:" + classPackageUrl + " ,方法:{}" + methodName + ",参数:{} " + params);
            return null;
        }
    }
}
