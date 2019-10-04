package download;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description 下载工具类
 * @Author pc
 * @Date 2019/10/04 12:35
 */
public class DownloadUtil {

    //设置缓存区大小
    public static final int cache = 10 * 1024;

    /**
     * @param originUrl 下载地址URL
     * @param filePath  存放下载文件的本地路径
     * @Description 文件流方式下载网络文件到本地
     * @Author guojianfeng
     * @Date 2019/10/04 12:36
     * @Return
     */
    public static void download(String originUrl, String filePath) {
        int byteSum = 0;
        int byteRead = 0;
        try {
            URL url = new URL(originUrl);
            //创建URLconnection对象
            URLConnection connection = url.openConnection();
            //获取下载文件输入流对象
            InputStream inputStream = connection.getInputStream();
            //获取保存文件输出流对象
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            while ((byteRead = inputStream.read(buffer)) != -1) {
                byteSum += byteRead;
                //以字节流形式写入文件
                fos.write(buffer, 0, byteRead);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param originUrl     下载地址URL
     * @param requestMethod 请求方式（GET/POST）
     * @param outputStr     输出流对象，可为null
     * @param filePath      存放至本地的路径
     * @Description http方式请求网络文件到本地
     * @Author guojianfeng
     * @Date 2019/10/04 14:28
     * @Return
     */
    public static void httpRequestDownload(String originUrl, String requestMethod, String outputStr, String filePath) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(originUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();

            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = inputStream.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }

            // 释放资源
            inputStream.close();
            fileout.flush();
            fileout.close();
            conn.disconnect();
        } catch (ConnectException ce) {
            System.out.println("连接超时：" + ce);
        } catch (Exception e) {
            System.out.println("https请求异常：" + e);
        }
    }
}
