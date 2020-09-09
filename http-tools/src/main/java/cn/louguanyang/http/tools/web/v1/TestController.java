package cn.louguanyang.http.tools.web.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:louguanyang@gmail.com">louguanyang</a>
 * @create 2020-09-07 23:22
 */
@Slf4j
@RestController()
@RequestMapping("/v1/test")
public class TestController {

    @RequestMapping("hello.json")
    public String hello(@RequestParam(name = "name") String name) {
        return "hello:" + name;
    }
}
