Download spring cli @ http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.3.5.RELEASE/spring-boot-cli-1.3.5.RELEASE-bin.zip
Add the <Spring unzip dir>/bin to the $PATH

Run below commands

''''

spring run app.groovy

do 

curl localhost:8080

it will display 'Hello World'

Now re-run the above command with

GREETING=Ohai spring run app.groovy

This will display 'Ohai World'

You can run with a specific profile:

SPRING_PROFILES_ACTIVE=spanish spring run app.groovy

This will display 'Hola World'

If you run like this below, 

SPRING_PROFILES_ACTIVE=spanish GREETING=Namaste spring run app.groovy

It will display 'Namaste World'

''''
