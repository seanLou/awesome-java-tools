package cn.louguanyang.excel.tools.util;

import com.alibaba.excel.EasyExcel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-09 22:56
 */
@Slf4j
class ExcelUtilTest {

    private static final String PRE_HEAD = "字段";
    private static final String PRE_VALUE = "值";
    private static final int ColNum = 100;
    private List<Map<String, String>> fullData = new ArrayList<Map<String, String>>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++) {
            Map<String, String> lineData = new HashMap<>();
            for (int j = 0; j < ColNum; j++) {
                lineData.put(PRE_HEAD + j, PRE_VALUE + j);
            }
            fullData.add(lineData);
        }
    }

    @Test
    void testDynamicHeadWrite() {
        List<List<String>> head = randomHead();

        String fileName = "/Users/louguanyang/Desktop/" + "DynamicHeadExcel_" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName).head(head).sheet("模板").doWrite(data(head));
    }

    private List data(List<List<String>> head) {
        List<List<String>> result = new ArrayList<List<String>>();
        if (CollectionUtils.isEmpty(head)) {
            return result;
        }
        List<String> headerList = head.stream().flatMap(Collection::stream).collect(Collectors.toList());

        for (Map<String, String> lineData : fullData) {
            List<String> data = new ArrayList<String>();
            for (String h : headerList) {
                String d = lineData.get(h);
                if (StringUtils.isEmpty(d)) {
                    continue;
                }
                data.add(d);
            }
            result.add(data);
        }
        return result;
    }

    private List<List<String>> randomHead() {
        List<List<String>> list = new ArrayList<List<String>>();
        int randomInt = ThreadLocalRandom.current().nextInt(10) + 1;

        for (int i = 0; i < ColNum; i++) {
            if (i % randomInt != 0) {
                log.error("{} % randomInt != 0, skip.", i);
                continue;
            }
            List<String> head = Collections.singletonList(PRE_HEAD + i);
            list.add(head);
        }
        return list;
    }
}