package cn.louguanyang.http.tools.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-03 00:02
 */
class HttpUtilTest {

    @Test
    void get() {
        String rspMsg = HttpUtil.get("http://cip.cc");
        Assertions.assertNotNull(rspMsg);
    }
}