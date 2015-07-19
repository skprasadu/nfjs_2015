package com.deepsouthcloud.docker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class HelloDockerWorldApplication {

    private Log log = LogFactory.getLog(getClass());

    @RequestMapping("/")
    public String hello() {
        return "Hello Docker World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloDockerWorldApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void helloLogger() {
        log.info("Hello from HelloDockerWorldApplication!");
    }

}
