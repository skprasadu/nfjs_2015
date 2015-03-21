package com.mattstine.mmlom;

import com.codahale.metrics.*;
import com.readytalk.metrics.StatsDReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static net.logstash.logback.marker.Markers.append;


@SpringBootApplication
@RestController
public class StatsdGraphiteDemoApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(StatsdGraphiteDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StatsdGraphiteDemoApplication.class, args);
    }

    @Autowired
    MetricRegistry metricRegistry;

    @Bean
    StatsDReporter statsDReporter() {
        return StatsDReporter.forRegistry(metricRegistry)
                .prefixedWith("springboot.metrics")
                .build("192.168.99.100", 8125);
    }

    @Bean
    ConsoleReporter consoleReporter() {
        return ConsoleReporter.forRegistry(metricRegistry)
                .outputTo(System.out)
                .build();
    }

    private Gauge matts;
    private Counter jobs;
    private Meter helloRequests;
    private Timer helloTimer;
    private Random random = new Random();

    private Map<String, Integer> names = new HashMap<>();

    @Override
    public void run(String... strings) throws Exception {
        jobs = metricRegistry.counter("counter.jobs");
        helloRequests = metricRegistry.meter("meter.hello.requests");
        helloTimer = metricRegistry.timer("timer.hello.requests");
        matts = metricRegistry.register("gauge.matts", new Gauge() {
            @Override
            public Object getValue() {
                return names.get("Matt");
            }
        });

        statsDReporter().start(10, TimeUnit.SECONDS);
        consoleReporter().start(10, TimeUnit.SECONDS);
    }

    @RequestMapping("/addJob")
    public String addJob() {
        jobs.inc();
        // Add Job to Queue
        return "added";
    }

    @RequestMapping("/removeJob")
    public String removeJob() {
        jobs.dec();
        // Remove Job from Queue
        return "removed";
    }


    @RequestMapping("/hello")
    public String hello() {
        final Timer.Context context = helloTimer.time();
        try {
            helloRequests.mark();

            logger.info("{} submitted {} request to {}", "mstine", HttpMethod.GET, "/hello");


            logger.info(append("endpoint", "/hello")
                            .and(append("type", HttpMethod.GET))
                            .and(append("user", "mstine")),
                    "Request recorded!");

            Thread.sleep(random.nextInt(5000));

            return "Hello World!";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            context.stop();
        }
    }

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        storeName(name);
        return "Hello " + name + "!";
    }

    private void storeName(String name) {
        Integer count = names.get(name);
        if (count == null) {
            names.put(name, 1);
        } else {
            names.put(name, ++count);
        }
    }

}
