package http;

import org.apache.http.client.config.RequestConfig;

import java.util.logging.Logger;

/**
 * @Description http请求工具
 * @Author pc
 * @Date 2019/10/05 15:04
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class.getName());
    private static RequestConfig requestConfig = null;
    static {
        requestConfig = RequestConfig.custom().
                setSocketTimeout(10000)         //响应超时时间，超过此时间不再读取响应
                .setConnectTimeout(3000)        //链路建立的超时时间
                .setConnectionRequestTimeout(5000)//从connection pool中获取一个connection的超时时间
                .build();
    }



}
