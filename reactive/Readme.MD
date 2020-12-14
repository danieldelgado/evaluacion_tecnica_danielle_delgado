# Reactive REST API
BCP test with reactive REST API developed using Spring Boot with RxJava.

#Docker
Para generar la imagen
docker build -t bcp_test .

#Docker Run
docker run --rm --name=reactive -p 8081:8081 bcp_test_two:latest

#Jar
para correr el jar
java -jar reactive-0.0.1-SNAPSHOT.jar