package cn.hejinyo;

import cn.hejinyo.utils.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SkyeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyeApplication.class, args);
    }

    @GetMapping("/")
    public Result index() {
        return Result.ok("Welcome to skye", "http://weibo.com/hejinyo");
    }

}
