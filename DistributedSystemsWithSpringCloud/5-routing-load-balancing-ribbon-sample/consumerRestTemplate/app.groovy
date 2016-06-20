import org.springframework.web.client.RestTemplate
import org.springframework.cloud.client.loadbalancer.LoadBalanced

@EnableDiscoveryClient
@RestController
public class Application {

  @LoadBalanced
  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }
  
  @Autowired
  RestTemplate restTemplate;

  @RequestMapping("/")
  String consume() {
    ProducerResponse response = restTemplate.getForObject("http://producer", ProducerResponse.class)

    "{\"value\": ${response.value}}"
  }
}

public class ProducerResponse {
  Integer value
}
