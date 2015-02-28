footer: © 2015 Matt Stine
slidenumbers: true

# [fit] Building
# [fit] 12 Factor
# [fit] JVM Applications

---

![left](../Common/images/mattmug.jpeg)
# Me

Matt Stine [@mstine](http://twitter.com/mstine)
Senior Product Manager
Pivotal
[matt.stine@gmail.com](mailto:matt.stine@gmail.com)

---

![](../Common/images/12factor.png)
# [fit] http://12factor.net

---

# Patterns

- Cloud-native application architectures
- Optimized for speed, safety, & scale
- Declarative configuration
- Stateless/shared-nothing processes
- Loose coupling to application environment

---

![fit](../Common/images/heroku.png)
# [fit] http://heroku.com

---

![125%](../Common/images/cloud_foundry.png)
# [fit] http://cloudfoundry.org

---

![](../Common/images/docker-logo.png)
# [fit] http://docker.com

---

![](../Common/images/unicorn.jpg)
# [fit] Microservices!

---

![filter](../Common/images/dropwizard.png)
![](../Common/images/spring-boot.png)
# [fit] _Microframeworks_
# [fit] Dropwizard
# [fit] Spring Boot

---

# Dropwizard
- [http://dropwizard.io](http://dropwizard.io)
- Library | Framework
- GOAL: Meet "production-ready" web app needs.
- Jetty / Jersey / Jackson
- Metrics: [http://metrics.dropwizard.io/](http://metrics.dropwizard.io/)

---

`HelloWorldConfiguration`

```java
public class HelloWorldConfiguration extends Configuration {
  @NotEmpty
  private String template;

  @NotEmpty
  private String defaultName = "Stranger";

  @JsonProperty
  public String getTemplate() { return template; }

  @JsonProperty
  public void setTemplate(String template) { this.template = template; }

  @JsonProperty
  public String getDefaultName() { return defaultName; }

  @JsonProperty
  public void setDefaultName(String name) { this.defaultName = name; }
}
```

---

`HelloWorldResource`

```java
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}
```

---

`Saying`

```java
public class Saying {
    private long id;

    @Length(max = 3)
    private String content;

    public Saying() {}

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() { return id; }

    @JsonProperty
    public String getContent() { return content; }
}
```

---

`TemplateHealthCheck`

```java
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) { this.template = template; }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return HealthCheck.Result.healthy();
    }
}
```

---

`HelloWorldApplication`

```java
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() { return "hello-world"; }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setConfigurationFactoryFactory(new EnvironmentConfigurationFactoryFactory());
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
```

---

`hello-world.yml`

```haml
template: $env:TEMPLATE:Hello, %s!
defaultName: $env:DEFAULT_NAME:Stranger
```

---

# [fit] DEMO

---

# Spring Boot
- [http://projects.spring.io/spring-boot](http://projects.spring.io/spring-boot)
- Opinionated convention over configuration
- Production-ready Spring applications
- Embed Tomcat, Jetty or Undertow
- *STARTERS*
- Actuator: Metrics, health checks, introspection

---

# [http://start.spring.io](start.spring.io)
![](../Common/images/start_spring.png)

---

`HelloWorldController`

```java
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
```

---

`Saying`

```java
public class Saying {
    private long id;

    @Length(max = 3)
    private String content;

    public Saying() {}

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() { return id; }

    @JsonProperty
    public String getContent() { return content; }
}
```

---

`TemplateHealthCheck`

```java
@Component
public class TemplateHealthCheck implements HealthIndicator {

    private final String template;

    @Autowired
    public TemplateHealthCheck(@Value("${template}") String template) {
        this.template = template;
    }

    @Override
    public Health health() {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Health.down().withDetail("error", "template doesn't include a name").build();
        }
        return Health.up().build();
    }
}
```

---

`HelloSpringBootApplication`

```java
@SpringBootApplication
public class HelloSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }
}
```

---

`application.yml`

```haml
template: Hello, %s!
default:
  name: Stranger
```

---

# [fit] DEMO

---

> I. Codebase
-- One codebase tracked in revision control, many deploys

---

> II. Dependencies
-- Explicitly declare and isolate dependencies

---

> III. Config
-- Store config in the environment

---

> IV. Backing Services
-- Treat backing services as attached resources

---

> V. Build, release, run
-- Strictly separate build and run stages

---

> VI. Processes
-- Execute the app as one or more stateless processes

---

> VII. Port binding
-- Export services via port binding

---

> VIII. Concurrency
-- Scale out via the process model

---

> IX. Disposability
-- Maximize robustness with fast startup and graceful shutdown

---

> X. Dev/prod parity
-- Keep development, staging, and production as similar as possible

---

> XI. Logs
-- Treat logs as event streams

---

> XII. Admin processes
-- Run admin/management tasks as one-off processes

---

# Thanks!

_Matt Stine_ ([@mstine](http://twitter.com/mstine))

* _This Presentation:_ https://github.com/mstine/nfjs_2015/tree/master/BuildingTwelveFactorApps

---

# Credits

* _The Twelve-Factor App_: http://12factor.net/
