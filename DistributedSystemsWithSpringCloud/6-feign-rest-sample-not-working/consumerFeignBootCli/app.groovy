package producer

@Grab("org.springframework.cloud:spring-cloud-starter-feign:1.1.2.RELEASE")
@Grab("org.springframework.cloud:spring-cloud-starter-eureka:1.1.2.RELEASE")

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.cloud.netflix.feign.EnableFeignClients

@EnableDiscoveryClient
@RestController
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableFeignClients  
public class Application {

  @Autowired
  ProducerClient client

  @RequestMapping("/")
  String consume() {
    ProducerResponse response = client.getValue()

    "{\"value\": ${response.value}}"
  }
}

@FeignClient("producer")
public interface ProducerClient {
  @RequestMapping(method = RequestMethod.GET, value = "/")
  ProducerResponse getValue()
}

public class ProducerResponse {
  Integer value
}
