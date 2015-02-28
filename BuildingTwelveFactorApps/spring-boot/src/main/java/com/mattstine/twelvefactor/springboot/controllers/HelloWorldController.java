package com.mattstine.twelvefactor.springboot.controllers;

import com.mattstine.twelvefactor.springboot.core.Saying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController("/hello-world")
public class HelloWorldController {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    @Autowired
    public HelloWorldController(@Value("${template}") String template, @Value("${default.name}") String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Saying sayHello(@RequestParam(value = "name", required = false) String name) {
        final String value = String.format(template, (name != null) ? name : defaultName);
        return new Saying(counter.incrementAndGet(), value);
    }
}
