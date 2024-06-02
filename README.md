# spring-boot + microservices + webflux
A demonstration of Spring Boot + Microservices + Webflux

This Repository develops the sample architecture presented in the `problem-statement.pdf` file



## How to run
Each Folder contains a service  
Running the command
```
./gradlew bootRun
```
will start any service

To run the integration tests, run the below command
```
cd vehicleaggregator
./gradlew clean build
```

Bus metadata service is dependent on a mysql DB  
Please change the connection properties here
`busmetadata/src/main/resources/application.properties`
