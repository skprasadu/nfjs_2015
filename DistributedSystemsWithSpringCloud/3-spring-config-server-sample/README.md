cd 3-spring-config-server-sample/configServer

run

''''
spring run app.groovy
''''

cd 3-spring-config-server-sample/distConfig

''''
spring run app.groovy
''''

Finally call

curl localhost:8080
Bonjuar

it returns Bonjuar because, in the config server in the application.yml file we have uri: https://github.com/skprasadu/config-repo.git configured

Now install Rabbit MQ and start it, and we will see Control Bus in action, 

Now again go to the config-repo folder cloned from Git repository, now modify the string to Satsriakal in the demo.yml file.

do a git push,

execute this command,

''''
curl -X POST localhost:8080/bus/refresh
''''

It will notify all the client of config-server to refresh their greeting variable to "Satsriakal"

and we see "Satsriakal World"

now start another instance of distConfig, like "SERVER_PORT=8081 spring run app.groovy"

Again go and change the config-repo demo.yml to "Vanakkam" and do a git push

''''
curl -X POST localhost:8080/bus/refresh
''''
and do curl localhost:8080 it will tell "Vanakkam World"

do curl localhost:8081 it will tell "Vanakkam World"

All the instance the subscribe to config server will get populated with the same config value thru control bus.