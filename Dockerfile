## detener mongo en anfitrion (powershell)
#net stop MongoDB

## iniciar mongo en anfitrion
#net start MongoDB

## iniciar servicios (Ubuntu)
#sudo service redis-server start

## detener servicios
#sudo service redis-server stop

## limpiar el target
# ./mvnw clean

## crear el jar
# ./mvnw clean package -DskipTests

## compilar imagen
# docker build -t tnosql-rest:1.0 .

## subir a docker hub
# docker push warriol/tnosql-rest:1.0

## ejecutar contenedor basado en la imagen
# docker run --name mi_contenedor -p 8080:8080 -e MONGO_HOST=mongorest -e MONGO_PORT=27018 -e MONGO_DB=tnosqlv1 -e MONGO_USER=root -e MONGO_PASS=password -e REDIS_HOST=redis -e REDIS_PORT=6379 -d warriol/tnosql-rest:1.0

#--------------------------------------------------------------------------

## Usa una imagen base
# docker login
# docker-compose up
FROM openjdk:17-jdk-alpine
# Copia el código fuente de la aplicación al contenedor
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
