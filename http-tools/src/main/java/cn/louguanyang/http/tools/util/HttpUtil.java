package cn.louguanyang.http.tools.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-02 23:38
 */
@Slf4j
public class HttpUtil {
    private HttpUtil() {
    }

    private final static HttpUtil INSTANCE = new HttpUtil();

    private static final int TIME_OUT = 20 * 1000;

    private static PoolingHttpClientConnectionManager cm;

    private static final String HTTP = "http";

    private static final String HTTPS = "https";

    static {
        LayeredConnectionSocketFactory socketFactory;
        try {
            socketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("创建SSL连接失败");
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register(HTTPS, socketFactory)
            .register(HTTP, new PlainConnectionSocketFactory()).build();
        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
    }

    public static HttpUtil getInstance() {
        return INSTANCE;
    }

    private static CloseableHttpClient httpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    public String get(String url) {
        // 配置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT)
            .setSocketTimeout(TIME_OUT).build();
        CloseableHttpResponse response = null;
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            request.setConfig(requestConfig);
            CloseableHttpClient httpClient = httpClient();
            response = httpClient.execute(request);
            // 请求发送成功，并得到响应
            int code = response.getStatusLine().getStatusCode();
            String message = EntityUtils.toString(response.getEntity());
            log.info("code:{},message:{}", code, message);
            return message;
        } catch (IOException e) {
            log.error("occur error", e);
            return "error:" + e.getMessage();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("response close fail");
                }

            }
        }
    }

}
