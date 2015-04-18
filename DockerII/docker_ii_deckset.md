footer: © 2015 Matt Stine
slidenumbers: true

![150%](../Common/images/docker-cluster.png)
# [fit] Docker II
# _Beyond Containers to Clusters_

---

![left](../Common/images/mattmug.jpeg)
# Me

Matt Stine [@mstine](http://twitter.com/mstine)
Senior Product Manager
Pivotal
[matt.stine@gmail.com](mailto:matt.stine@gmail.com)

---

![right fit](../Common/images/cloud-native-book.jpg)
# I wrote a little cloud book...

FREE - Compliments of Pivotal

[http://bit.ly/cloud-native-book](http://bit.ly/cloud-native-book)

---

# What's missing?
- Capacity Management
- Scheduling / Bin Packing
- Health Checks
- Persistent State / Volume Management
- Networking
- Service Discovery
- Routing / Load Balancing
- Log / Metrics Aggregation

---

# [fit] We'll Cover
# [fit] a Few
# [Fit] of These

---

![40%](../Common/images/earth.png)
# [fit] YES
# [fit] We're rebuilding the entire world...

---

# [fit] FIRST

---

> I need a place that I can run Docker...

---

# [fit] Docker
# [fit] Machine

https://docs.docker.com/machine/

---

# [fit] DEMO

---

> OK Cool...how do I run containers on that?

---

# [fit] Back
# [fit] to
# [fit] Docker

---

# [fit] DEMO

---

> Great...I'd like to declaratively compose containers!

---

# [fit] Docker
# [fit] Compose

https://docs.docker.com/compose/

---

# `docker-compose.yml`

```haml
web:
  build: .
  command: python app.py
  ports:
   - "5000:5000"
  volumes:
   - .:/code  
  links:
   - redis
redis:
  image: redis
```

---


# [fit] DEMO

---

# So back to bigger issues...

- Capacity Management
- Scheduling / Bin Packing
- Health Checks
- Persistent State / Volume Management
- Networking
- Service Discovery
- Routing / Load Balancing
- Log / Metrics Aggregation

---

# [fit] We Did This
# [fit] Once

---

# [fit] App/Service
# [fit] Delivery vs.
# [fit] Infrastructure

---

# [fit] All
# [fit] Bleeding Edge

---

# Capacity Management

- BOSH (and some Volume Mgmt!)
- Mesos

---

# BOSH
![inline](../Common/images/bosh-architecture.png)

http://bosh.io

---

# Mesos
![inline](../Common/images/mesos_1.jpg)

http://mesos.apache.org/

---

# Mesos Resource Offer
![inline](../Common/images/mesos_2.jpg)

---

# Scheduling / Health Checks

- Swarm
- Diego
- Kubernetes
- Marathon

---

# [fit] Docker
# [fit] Swarm

https://docs.docker.com/swarm/

---


# [fit] DEMO

---

# [fit] Kubernetes

![inline](../Common/images/kubernetes.png)

http://kubernetes.io/

---

# [fit] Minions
# [fit] Pods
# [fit] Replication Controllers
# [fit] Services

---

![original fit](../Common/images/kubernetes_arch.png)

---

```
{
  "id": "lattice-app-controller",
  "kind": "ReplicationController",
  "apiVersion": "v1beta1",
  "desiredState": {
    "replicas": 3,
    "replicaSelector": {"name": "lattice-app"},
    "podTemplate": {
      "desiredState": {
         "manifest": {
           "version": "v1beta1",
           "id": "lattice-app-controller",
           "containers": [{
             "name": "lattice-app",
             "image": "mstine/lattice-app",
             "command": ["/lattice-app"],
             "env": [{
                "key": "PORT",
                "value": "8080"
              }],
             "cpu": 100,
             "memory": 50000000,
             "ports": [{"containerPort": 8080}]
           }]
         }
       },
       "labels": {
         "name": "lattice-app"
       }
      }
  },
  "labels": {"name": "lattice-app"}
}
```

---

```json
{
  "id": "lattice-service",
  "kind": "Service",
  "apiVersion": "v1beta1",
  "port": 8000,
  "publicIPs": ["10.245.1.3"],
  "selector": {
    "name": "lattice-app"
  },
  "labels": {
    "name": "lattice-app"
  }
}
```

---

# [fit] DEMO

---

# [fit] Marathon

https://mesosphere.github.io/marathon/

---

![inline fit](../Common/images/maraton_arch_1.png)
![inline fit](../Common/images/marathon_arch_2.png)![inline fit](../Common/images/marathon_arch_3.png)

---

```json
{
    "id": "lattice-app",
    "container": {
      "docker": {
        "image": "cloudfoundry/lattice-app",
        "network": "BRIDGE",
        "portMappings": [
          { "containerPort": 8080 }
        ]
      }
    },
    "cmd": "INSTANCE_INDEX=$PORT0 PORT=8080 /lattice-app",
    "cpus": 0.2,
    "mem": 32.0,
    "instances": 2
}
```

---


# [fit] DEMO

---

# [fit] Diego

---

![original fit](../Common/images/diego-overview.png)

---

![](https://www.youtube.com/watch?v=e76a50ZgzxM)

---

# Diego also touching...

- Service Discovery
- Routing / Load Balancing
- Log / Metrics Aggregation

---

# Lattice
![inline](../Common/images/lattice.png)

https://lattice.cf

---


# [fit] DEMO

---

# [fit] Networking?

---

# Weave

![inline fit](../Common/images/weave_arch.png)

http://zettio.github.io/weave/

---

# Thanks!

_Matt Stine_ ([@mstine](http://twitter.com/mstine))

* This Presentation: [https://github.com/mstine/nfjs_2015/tree/master/DockerII](https://github.com/mstine/nfjs_2015/tree/master/DockerII)
