package http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @Description Servlet请求相关
 * @Author pc
 * @Date 2019/10/05 16:49
 */
public class ServletUtil {

    /**
     * @param request servletRequest
     * @Description 获取servlet请求中的所有参数，返回map对象
     * @Author guojianfeng
     * @Date 2019/10/05 16:55
     * @Return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map<String, String> returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


    /**
     * @param request request请求对象
     * @Description 获取请求的源IP地址
     * @Author guojianfeng
     * @Date 2019/10/05 23:42
     * @Return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        return getIp(request, ip);
    }


    /**
     * @param request 请求对象
     * @Description 多级代理获取源请求真实IP
     * @Author guojianfeng
     * @Date 2019/10/06 13:57
     * @Return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        return getIp(request, ip);
    }


    private static String getIp(HttpServletRequest request, String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (isNotEmpty(ip)) {
            int index;
            if ((index = ip.indexOf(",")) != -1) {
                ip = ip.substring(0, index);
            }
        }
        return ip;
    }


    /**
     * @param fileName 下载后的文件名
     * @Description 设置让浏览器弹出下载对话框的header在，不同浏览器使用不同的编码方式
     * @Author guojianfeng
     * @Date 2019/10/06 14:06
     * @Return
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String CONTENT_DISPOSITION = "Content-Disposition";

        try {
            String agent = request.getHeader("User-Agent");
            String encodedfileName = null;
            if (null != agent) {
                agent = agent.toLowerCase();
                if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {
                    encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
                } else if (agent.contains("msie")) {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                } else if (agent.contains("opera")) {
                    encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
                } else {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                }
            }
            response.setHeader(CONTENT_DISPOSITION, "attachment; " + encodedfileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
