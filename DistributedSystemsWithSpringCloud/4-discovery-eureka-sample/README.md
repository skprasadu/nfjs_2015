cd 4-discovery-eureka/eureka

start the eureka as "spring run app.groovy"

cd 4-discovery-eureka/producer 

start the producer as "spring run app.groovy"

cd 4-discovery-eureka/consumer 

start the consumer as "SERVER_PORT=8081 spring run app.groovy"

execute curl command as "curl localhost:8081" it will return a json with the counter as follows, {"value": 1}

Also open the browser and type "http://localhost:8761", it will open Eureka and display how many producers and consumers are running

Now start one more producer services as "SERVER_PORT=8082 spring run app.groovy"

Open Eureka it will show that there are 2 producers running, now again go to execute curl command as "curl localhost:8081" it will return a json with the counter as follows, {"value": 4}

Shut off one producer and run the above command and see the Eureka, it will show, only one producer is running.
