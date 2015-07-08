footer: © 2015 Matt Stine
slidenumbers: true

# Advanced Data Architecture Patterns

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

Dive into Bounded Contexts

---

Set up FluffBox Architecture

---

Describe three main challenges:

* Spanning Transactions
* Data Synchronizaion
* Spanning Queries

Why are these challenges? We've created a distributed system...

http://bravenewgeek.com/distributed-systems-are-a-ux-problem/

---

# Spanning Transactions

TODO: come up with a use case for FluffBox

---

# Data Synchronizaion

TODO: come up with a use case for FluffBox

---

# Spanning Querues

TODO: come up with a use case for FluffBox (intelligent logistics?)

---

CAP Theorem

---

Discuss CA - you must be Partition Tolerant

http://codahale.com/you-cant-sacrifice-partition-tolerance/
http://bravenewgeek.com/cap-and-the-illusion-of-choice/

---

Discuss CP

---

Consensus Algorithms

http://bravenewgeek.com/understanding-consensus/

---

Distributed Transactions

---

Two-Phase Commit

---

Discuss AP

---

Discuss Eventual Consistency

---

Compensating Transactions

- these can be bad because they create coupling

---

Event-Driven Architecture

---

Solution #1: Raw Events

Can address:

* Spanning Transactions (noodle this one a bit)
* Data Synchronizaion

---

Rub a Message broker on it...

---

Pros/Cons of Solution #1

---

Solution #2: Event Sourcing/CQRS

Can address:

* Spanning Transactions
* Data Synchronizaion
* Spanning Queries

---

Event Sourcing?

---

CQRS?

---

Data Stores...

Kafka
EventStore
Datomic

---

App Frameworks

Axon
Jdon

---

Pros/Cons of Solution #2

---

Solution #3: Streaming

Can address:

* Data Synchronizaion (noodle)
* Spanning Queries

---

Lambda Architecture

---

Kappa Architecture

---

Tech?

---

Pros/Cons of Solution #3

---

Summary
