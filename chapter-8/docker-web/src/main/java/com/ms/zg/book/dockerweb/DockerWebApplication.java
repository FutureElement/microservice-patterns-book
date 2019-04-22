package com.ms.zg.book.dockerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerWebApplication.class, args);
    }

    @GetMapping("hello")
    public String hello() {
        return "World";
    }
}
