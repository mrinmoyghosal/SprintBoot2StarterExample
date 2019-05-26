** Dependency **

* Maven
* JDK 8

** Installation **

``` 
mvn clean package 
java -jar target/ratecrawler-0.0.1-SNAPSHOT.jar

```

** Get Current Rate (1 Bitcoin equivalent in USD) **

`  curl -X GET "http://localhost:8080/api/v1/rates/now" -H "accept: */*" `

** Get Historical Rate (1 Bitcoin equivalent in USD) **

`  curl -X GET "http://localhost:8080/api/v1/rates/historical?startDate=2019-05-26T22%3A40%3A00&endDate=2019-05-26T22%3A40%3A00" -H "accept: */*" `

** Get Crawler Configs ** 

` curl -X GET "http://localhost:8080/api/v1/crawler/configs" -H "accept: */*" `

** Update Refreshrate of any crawler **

` curl -X POST "http://localhost:8080/api/v1/crawler/configs/1/1000" -H "accept: */*" `

** Swagger UI **

` http://localhost:8080/swagger-ui.html `

** Todo **

* Using real datastore
* Using webflux instead of `RestTemplate`
* Incorporating DDD principles and eventstore.
* Writing UnitTest
* Writing IntegrationTest
* Dockerfile creation
* Kubernetes deployment configuration file

