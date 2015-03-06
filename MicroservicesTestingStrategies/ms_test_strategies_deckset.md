footer: © 2015 Matt Stine
slidenumbers: true

# Microservices
# [fit] _Testing_
# [fit] Strategies

---

![left](../Common/images/mattmug.jpeg)
# Me

_Matt Stine_ [@mstine](http://twitter.com/mstine)
_Senior Product Manager ,Pivotal_
[http://www.mattstine.com](http://www.mattstine.com)
[matt.stine@gmail.com](mailto:matt.stine@gmail.com)

---

# [fit] Microservices?

---

# _There Seems to Be Some_ Hype...
![](../Common/images/unicorn.jpg)

---

> Loosely coupled service oriented architecture with bounded contexts...
-- Adrian Cockcroft

---

# [fit] _Loosely_
# [fit] Coupled

---

# [fit] _Bounded_
# [fit] Contexts

---

# [fit] _CONWAY'S_
# [fit] LAW

---

> Any organization that designs a system (defined broadly) will produce a design whose structure is a copy of the organization's communication structure.
-- Melvyn Conway, 1967

---

# [fit] Inverse
# [fit] _Conway_
# [fit] Manuever
![](../Common/images/picard-maneuver.jpg)

---

# [fit] Decentralized
# [fit] _Autonomous_
# [fit] Capability
# [fit] _Teams / Services_

---

# [fit] TESTING

---

# Who moved my Trust Boundary?

- _µServices Expose Hidden Boundaries_
- _Increased Testing Flexibility: Types & Levels_
- _Tradeoff: Comprehensiveness vs. Criticality, Lifespan_
- _Possible with Monolith, but Easier with µServices_

---

# Internal Anatomy
- _Resources_ (API)
- _Domain Model_ (services/domain objects/repositories)
- _Gateway_ (API to collaborators + transport mechanism)
- _Persistence_ (Data Mappers, ORM, etc.)

---

# [fit] Automated Testing @ Communication Boundaries

- _Resource to Domain_
- _Resource to Gateway_
- _Domain to Gateway_
- _Gateway to Network Resource_
- _Persistence to Network Resource_

---

# [fit] Testing
# [fit] Pyramid

![99% right](../Common/images/test_pyramid.png)

---

# [fit] Unit

---

# [fit] What you already do (_right?_).

---

# [fit] Integration

---

# Verify Interactions:

- _Other Microservices (IF YOU OWN...)_
- _Caches_
- _Data Stores_
- _Etc._

---

# [fit] _ANYTHING_
# [fit] that touches the _network_

---

# [fit] Component

---

# [fit] Testing the
# [fit] _Microservice_

---

# [fit] _In Process_
# [fit] vs.
# [fit] _Out of Process_

---

# In Process

- _No Network!_
- _Test Doubles for External Services_
- _In-Memory Data Stores_
- _Performance Goes Up!_
- _Internal API Probes: Metrics, Logs, Health_

---

# In Process (Rest Assured)

```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceBrokerControllerIntegrationTest {

    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void catalogTest() {
        given()
                .when()
                .auth().basic("warreng", "natedogg")
                .get("/v2/catalog")

                .then().log().all()
                .statusCode(200)

                .body("services.id", hasItems("BD7097C1-F12A-11E3-AC10-0800200C9A66"))
                .body("services.bindable", hasItems(true))
                .body("services.plans.id", hasItems(Arrays.asList("BD7097C0-F12A-11E3-AC10-0800200C9A66")))
                .body("services.plans.name", hasItems(Arrays.asList("only")));
    }
}
```

---

# Out of Process

- _MUST DEPLOY!_
- _Stub External Services (over network)_
- _Real Data Stores_
- _Performance Goes Down!_

---

# [fit] Consumer
# [fit] Driven
# [fit] _Contracts_

---

# Bounded Contexts

- _Crossing Boundaries_ :arrow_right: _Increased Complexity_
- _Interfaces to Other Contexts_
- _Availability of Other Services_

---

# [fit] _THIS_
# [fit] _IS_
# [fit] HARD

---

# [fit] _Consumer to Component_ =
# [fit] CONTRACT

---

# [fit] Contracts

- _Input/Output Data Structure Expectations_
- _Side Effects_
- _Performance Characteristics_
- _Concurrency Characteristics_

---

# [fit] _MULTIPLE CONSUMERS_
# [fit] =
# [fit] _MULTIPLE CONTRACTS_

---

# [fit] _Contracts_
# [fit] Must Be Met Over :alarm_clock:

---

# [fit] _API_ =
# [fit] Contract

---

# _Consumer-Driven_ Contract

_I capture my_ expectations _of your service as a suite of automated_ tests _that I_ provide _to you._

_You_ run _them in your_ continuous integration _pipeline._

---

# [fit] Remember
# [fit] _Decentralized_
# [fit] _Autonomous_
# [fit] _Capability_
# [fit] _Teams/Services_?

---

# [fit] _Consumer-Driven_ Contracts
# :thumbsup:

---

# [fit] _PACT_
https://github.com/realestate-com-au/pact

---

# [fit] PACT _JVM_
https://github.com/DiUS/pact-jvm

---

# Consumer `PactFragment`

```java
protected PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("a request for Foos")
                .path("/foos")
                .method("GET")

                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("[{\"value\":42}, {\"value\":100}]").toFragment();
}
```
---

# The Pact

```json
{
  "provider" : {
    "name" : "Foo_Provider"
  },
  "consumer" : {
    "name" : "Foo_Consumer"
  },
  "interactions" : [ {
    "description" : "a request for Foos",
    "request" : {
      "method" : "GET",
      "path" : "/foos"
    },
    "response" : {
      "status" : 200,
      "headers" : {
        "Content-Type" : "application/json;charset=UTF-8"
      },
      "body" : [ {
        "value" : 42
      }, {
        "value" : 100
      } ]
    }
  } ],
  "metadata" : {
    "pact-specification" : {
      "version" : "2.0.0"
    },
    "pact-jvm" : {
      "version" : "2.1.7"
    }
  }
}
```

---

# Producer Pact Verification

```java
project(':microservices-pact-provider') {
  apply plugin: 'au.com.dius.pact'

  jar {
      baseName = 'microservices-pact-provider'
      version = '0.0.1-SNAPSHOT'
  }

  pact {
      serviceProviders {
          fooProvider {
              hasPactWith('fooConsumer') {
                  pactFile = file("${project(':microservices-pact-consumer').projectDir}/target/pacts/Foo_Consumer-Foo_Provider.json")
              }
          }
      }
  }
}
```

---

# [fit] PACT
# [fit] _Broker_
https://github.com/bethesque/pact_broker

---

![original fit](../Common/images/pact_broker_1.png)

---

![original fit](../Common/images/pact_broker_2.png)

---

![original fit](../Common/images/pact_broker_3.png)

---


# [fit] _End_
# [fit] to
# [fit] _End_

---

# [fit] External Requirements
# [fit] _Black Box_
# [fit] Business Facing

---

# [fit] _THIS_
# [fit] _IS_
# [fit] REALLY
# [fit] _HARD_

---

1. _Few as possible_
2. _Personas / Journeys_
3. _Reliability over Comprehensiveness_
4. _Infrastructure as Code_
5. _Data Independency_

---

# [fit] _SKIP_
# [fit] _IT_?

^ In favor of monitoring in production...

---

# [fit] Exploratory

---

# [fit] Discover the _risks_ you _care_ about...

---

![270%](../Common/images/dos_equis.jpg)
## Testing in _Production_

---

# [fit] _Blue_
# [fit] _Green_
# [fit] Deploys

---

![original fit](../Common/images/blue_green.png)

---

# [fit] _Canary_
# [fit] Releases

---

![original fit](../Common/images/canary_release.png)

---

# [fit] _MTBF_
# [fit] vs.
# [fit] _MTTR_

^ Mean Time Between Failures

^ Mean Time To Recovery

---

>Monitor the snot out of production and staging!
-- Elisabeth Hendrickson

---


# [fit] _NON_
# [fit] Functional

---

# _NON_Functional

- _acceptable latency of a web page_
- _number of users a system should support_
- _how accessible a system is to people w/ disabilities_
- _how secure customer data should be_

---

> If you don't meet your non-functional requirements, then you functional requirements won't function.
-- Me

---

# Thanks!

_Matt Stine_ ([@mstine](http://twitter.com/mstine))

* _This Presentation:_ https://github.com/mstine/nfjs_2015/tree/master/MicroservicesTestingStrategies

---

# Credits

* _Internal Anatomy adapted from:_ http://martinfowler.com/articles/microservice-testing/#anatomy-modules
* _Testing Pyramid adapted from:_ http://martinfowler.com/articles/microservice-testing/#conclusion-test-pyramid
