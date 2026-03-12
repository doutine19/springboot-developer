package me.scpark.springdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "박동훈") String name) {
        return "반갑습니다, " + name + "님!";
    }
}