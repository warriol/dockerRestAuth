## detener mongo en anfitrion (powershell)
#net stop MongoDB

## iniciar mongo en anfitrion
#net start MongoDB

## iniciar servicios (Ubuntu)
#sudo service redis-server start
#redis-cli
#keys *

## detener servicios
#sudo service redis-server stop

## limpiar el target
# ./mvnw clean

## crear el jar
# ./mvnw clean package -DskipTests

## compilar imagen
# docker build -t tnosql-rest:1.0 .

## correr imagen
# docker-compose up
# docker-compose up -d

#--------------------------------------------------------------------------

## Usa una imagen base
# docker login
# docker-compose up
FROM openjdk:17-jdk-alpine
#RUN apk update
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
