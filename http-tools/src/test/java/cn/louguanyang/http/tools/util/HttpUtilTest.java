package cn.louguanyang.http.tools.util;

import cn.louguanyang.http.tools.HttpToolsApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-03 00:02
 */
class HttpUtilTest extends HttpToolsApplicationTests {

    @Test
    void get() {
        String rspMsg = HttpUtil.getInstance().get("http://127.0.0.1:8080/v1/test/hello.json?name=lou");
        Assertions.assertNotNull(rspMsg);
    }
}