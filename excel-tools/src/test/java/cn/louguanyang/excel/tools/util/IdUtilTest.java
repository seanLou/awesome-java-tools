package cn.louguanyang.excel.tools.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-14 23:00
 */
class IdUtilTest {

    @Test
    void generateOrderNo() {
        int count = 10000;
        List<String> orderNoList = IntStream.range(0, count).parallel().mapToObj(i -> IdUtil.generateOrderNo())
            .collect(Collectors.toList());
        int size = orderNoList.size();
        Assertions.assertEquals(count, size);
        long distinctCount = orderNoList.stream().distinct().count();
        Assertions.assertEquals(count, distinctCount);
    }
}