package cities;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJpaRepositories
@RestController
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private Histogram searchResultHistogram;

    @Bean
    ConsoleReporter consoleReporter() {
        return ConsoleReporter.forRegistry(metricRegistry)
                .outputTo(System.out)
                .build();
    }

    @Override
    public void run(String... strings) throws Exception {
        searchResultHistogram = metricRegistry.histogram("histogram.search.results");

        consoleReporter().start(10, TimeUnit.SECONDS);
    }

    @Autowired
    CityRepository cityRepository;

    @Autowired
    MetricRegistry metricRegistry;

    @RequestMapping("/cities/{contains}")
    public Iterable<City> citySearch(@PathVariable("contains") String contains) {
        final Page<City> results = cityRepository.findByNameContainsIgnoreCase(contains, new PageRequest(0, 10));
        searchResultHistogram.update(results.getTotalElements());
        return results;
    }
}
