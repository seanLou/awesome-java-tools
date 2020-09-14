package cn.louguanyang.excel.tools.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-14 22:50
 */
public class IdUtil {

    private static final int INITIAL_VALUE = 1000;
    private static final AtomicInteger SEQ = new AtomicInteger(INITIAL_VALUE);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    private final static IdUtil INSTANCE = new IdUtil();
    private static final int MAX_SEQ = INITIAL_VALUE * 10 - 1;


    private IdUtil() {
    }

    public static IdUtil getInstance() {
        return INSTANCE;
    }

    public static String generateOrderNo() {
        if (SEQ.get() > MAX_SEQ) {
            SEQ.set(INITIAL_VALUE);
        }
        return LocalDateTime.now(ZONE_ID).format(DATE_TIME_FORMATTER) + SEQ.getAndIncrement();
    }
}
