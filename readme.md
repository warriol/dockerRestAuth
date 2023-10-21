# <span style="color: #10a1ff">Taller de base de datos No Sql</span>

Segundo laboratorio Bases de Datos NoSql 2023

**Autores:**
- Damián Vera
- Nicolás Lepore
- Wilson Arriola

## Avance del Taller

- Requisitos Funcionales Obligaotrios (API REST) <img src="https://img.shields.io/badge/ESTADO-completo-green">
  - [x] Agregar persona
  - [x] Agregar domicilio
  - [x] Consultar Domicilio por CI
  - [x] Consultar domicilio por parametros
- Requisitos No Funcionales <img src="https://img.shields.io/badge/ESTADO-completo-green">
  - [x] Intercambio se realiza mediante JSON
  - [x] La base de datos principal es MongoDB
  - [x] Modelol lógico realizado con justificativo
  - [x] Git como herramienta de control de versiones
  - [x] Casos de prueba en Postman
  - [x] Lenguaje utilizado JAVA
- Documentacion <img src="https://img.shields.io/badge/ESTADO-completo-green">
  - [x] Swagger
- Opcionales (al menos 2) <img src="https://img.shields.io/badge/ESTADO-en proceso-orange">
  - [ ] Utilizar Jenkins para autamatizacion de pruebas 
  - [ ] Dockerizar la solucion
  - [ ] Realizar prueba de cargas contra los servicios rest y analizar rendimiento (JMeter)
  - [x] Utilizar Redis como base de datos secundaria, justificando su inclusión en el diseño

## <span style="color: #10a1ff">--</span> Formato de intercambio de datos
- Coleccion "Personas"

| Parameter  | Type       | Description                                     |
|:-----------|:-----------|:------------------------------------------------|
| `_id`      | `ObjectId` | **Requirido**. Clave única generado por MongoDB |
| `ci`       | `String`   | **Requirido**. Clave única para la persona      |
| `nombre`   | `String`   | Nombre de la persona                            |
| `apellido` | `String`   | Apellido de la persona                          |
| `edad`     | `Int`      | Edad de la persona                              |

    - Justificativo del diseño de la colección "Personas"
      - Se utiliza una colección llamada "Personas" para almacenar la información de las personas.
      -  Cada persona se identifica por su número de cédula (CI), que se define como una clave única.
      - Esto garantiza que no haya duplicados en la base de datos y permite realizar búsquedas eficientes por CI.

- Coleccion "Direcciones"

| Parameter      | Type       | Description                                             |
|:---------------|:-----------|:--------------------------------------------------------|
| `_id`          | `ObjectId` | **Requirido**. Clave única generado por MongoDB         |
| `ci`           | `String`   | **Requirido**. Clave foránea para referiri a la persona |
| `departamento` | `String`   | **Indice** Nombre del departamento                      |
| `localidad`    | `String`   | **Indice** Nombre de la localidad                       |
| `barrio`       | `String`   | **Indice** Nombre de del barrio                         |
| `calle`        | `String`   | Nombre de la vía principal                              |
| `nro`          | `String`   | Número de puerta                                        |
| `apartamento`  | `String`   | Número de apartamento                                   |
| `padron`       | `String`   | Número de padrón                                        |
| `ruta`         | `Int`      | Número de ruta                                          |
| `km`           | `String`   | Km de la ruta donde se ubica el punto                   |
| `letra`        | `String`   | Indentificativo de la ruta                              |

    - Justificativo del diseño de la Colección "Direcciones"
      - Se utiliza una colección llamada "Direcciones" para almacenar la información de las
        direcciones asociadas a las personas. Cada dirección se relaciona con una persona 
        mediante su CI (clave foránea).
      - Esto permite mantener una relación uno a muchos entre personas y sus direcciones.
      - Se incluyen todos los campos relacionados con la dirección como se especifica en el 
        enunciado. Esta estrategia garantiza que los campos que no son obligatorios pueden 
        estar vacíos si no se proporcionan datos para ellos.

## <span style="color: #10a1ff">--</span> Instalación en Docker
  ### crear el paquete jar
   ```bash
    # limpiar la consola
    ./mvnw clean
    # compilar el paquete completo
    ./mvnw clean package -DskipTests
   ```
  ### docker
   ```bash
    # constuir el docker
    docker-compose build
    # iniciar el docker
    docker-compose up
   ```

## configuración Dockerfile
   ```bash
    FROM openjdk:17-jdk-alpine
    COPY target/*.jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
   ```

## configuración docker-compose
  ```bash
  version: "3.8"
  services:
    # configuracion del servicio apache
    java_app:
      container_name: java_app
      image: tnosql-rest:1.0
      build: .
      links:
          - mongorest
      ports:
        - "8080:8080"
      environment:
          - MONGO_HOST=mongorest
          - MONGO_PORT=27018
          - MONGO_DB=personas
          - MONGO_USER=root
          - MONGO_PASS=password
      depends_on:
          - mongorest
    mongorest:
      # docker pull mongo:7.0.2
      image: mongo
      container_name: mongorest
      environment:
        - MONGO_INITDB_ROOT_USERNAME=root
        - MONGO_INITDB_ROOT_PASSWORD=password
      restart: unless-stopped
      ports:
        - "27018:27017"
      volumes:
        - ./database/mongodb/db:/data/db
        - ./database/mongodb/dev.archive:/Databases/dev.archive
        - ./database/mongodb/production:/Databases/production
  ```

## <span style="color: #10a1ff">--</span> Instalación en Windows

### MongoDB
- Es necesario tener instalada la base de datos MongoDB en el equipo.
- Adicionalmente se puede instalar su administrador, mongo compass, para facilitar la visualización de los datos.

```bash
  # iniciar servicio mongo
    net start MongoDB
  
  # detener servicio mongo
    net stop MongoDB
```

### Java
- Es necesario tener instalado Java 17 corretto en el equipo.

### Postman
- Es necesario tener instalado Postman para realizar las pruebas de los servicios rest.

### WSL 2
- Es necesario tener instalado WSL 2 para poder ejecutar el comando `./mvnw` en la consola de windows.

### Redis
- Es necesario tener instalado Redis en el equipo.

```bash
curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list

sudo apt-get update
sudo apt-get install redis

# iniciar servicios
sudo service redis-server start

# detener servicios
sudo service redis-server stop

#conectar a redis
redis-cli

#listar las claves existente
keys *
```

## <span style="color: #10a1ff">--</span> Bases de datos utilizadas

![Logo](https://cdn.iconscout.com/icon/free/png-512/free-redis-3-1175053.png?f=webp&w=106)
![Logo](https://cdn.iconscout.com/icon/free/png-512/free-mongodb-226029.png?f=webp&w=120)

## <span style="color: #10a1ff">--</span> Plataformas

![Logo](https://cdn.iconscout.com/icon/free/png-512/free-docker-11-1175228.png?f=webp&w=80)
![Logo](https://www.docker.com/wp-content/uploads/2023/07/github-logo.svg)

## <span style="color: #10a1ff">--</span> Lenguajes

![Logo](https://cdn.iconscout.com/icon/free/png-512/free-java-57-1174929.png?f=webp&w=156)
![Logo](https://cdn.iconscout.com/icon/free/png-512/free-spring-16-283031.png?f=webp&w=156)

## <span style="color: #10a1ff">--</span> Documentacion SWAGGER
- /v1/swagger-ui/index.html#/

## <span style="color: #10a1ff">Requisitos funcionales de la API Rest</span>

## - <span style="color: #db291d">POST</span> Agergar personas

    Agrega una persona que no existe en el sistema, se pasa como parámetro un objeto de tipo persona.
    En caso de la persona existir ya en el sistema (la CI pertenece a una persona que está en la base)
    se retorna el error 401 con el mensaje: “La persona ya existe”.

```http POST
  POST /api/v1/personas
```
### respuestas:
- <span style="color: #1ddb2c">200</span> - Datos insertados
- <span style="color: #db291d">401</span> - La persona ya existe

| Parameter  | Type       | Description                                     |
|:-----------|:-----------|:------------------------------------------------|
| `_id`      | `ObjectId` | **Requirido**. Clave única generado por MongoDB |
| `ci`       | `String`   | **Requirido**. Clave única para la persona      |
| `nombre`   | `String`   | Nombre de la persona                            |
| `apellido` | `String`   | Apellido de la persona                          |
| `edad`     | `Int`      | Edad de la persona                              |

```json
{
    "_id": ObjectId,
    "ci": "43791806",
    "nombre": "Wilson",
    "apellido": "Arriola",
    "edad": 3
}
```


## - <span style="color: #db291d">POST</span> Agregar domiclio

    Agrega una dirección asociada a una persona. Se pasa como parámetro la cédula de la
    persona y un objeto de dirección. Se genera automáticamente un id ùnico asociado al
    domicilio. Si la persona (cédula) no existe en el sistema, se retorna el error 402
    con el mensaje: “No existe una persona con la cédula aportada como parámetro".

```http
  POST /api/v1/direccion
```
### respuestas:
- <span style="color: #1ddb2c">200</span> - Datos insertados
- <span style="color: #db291d">402</span> - No existe una persona con la cédula aportada como parámetro

| Parameter      | Type       | Description                                             |
|:---------------|:-----------|:--------------------------------------------------------|
| `_id`          | `ObjectId` | **Requirido**. Clave única generado por MongoDB         |
| `ci`           | `String`   | **Requirido**. Clave foránea para referiri a la persona |
| `departamento` | `String`   | **Indice** Nombre del departamento                      |
| `localidad`    | `String`   | **Indice** Nombre de la localidad                       |
| `barrio`       | `String`   | **Indice** Nombre de del barrio                         |
| `calle`        | `String`   | Nombre de la vía principal                              |
| `nro`          | `String`   | Número de puerta                                        |
| `apartamento`  | `String`   | Número de apartamento                                   |
| `padron`       | `String`   | Número de padrón                                        |
| `ruta`         | `Int`      | Número de ruta                                          |
| `km`           | `String`   | Km de la ruta donde se ubica el punto                   |
| `letra`        | `String`   | Indentificativo de la ruta                              |

```json
{
    "_id": ObjectId,
    "ci": "43791806",
    "departamento": "Montevideo",
    "localidad": "Carrasco",
    "barrio": "Residencial",
    "calle": "Av. Principal",
    "nro": "123",
    "apartamento": "A2",
    "padron": "5678",
    "ruta": "R10",
    "km": 5,
    "letra": "B"
}
```

## - <span style="color: #1ddb2c">GET</span> Consultar domiclio
    Obtiene todas las direcciones asociadas a una persona. Se pasa como parámetro la cédula
    de la persona. Las direcciones se listan de forma tal que las últimas ingresadas se 
    muestran primero. Este endpoint debe ofrecer la posibilidad de obtener los resultados de
    forma paginada.. Si la persona (cédula) no existe en el sistema, se retorna el error 402
    con el mensaje: “No existe una persona con la cédula aportada como parámetro.

```http
  GET /api/v1/direccion/{ci}
```
### respuestas:
- <span style="color: #1ddb2c">200</span> - Datos retornados
- <span style="color: #db291d">402</span> - No existe una persona con la cédula aportada como parámetro


## - <span style="color: #1ddb2c">GET</span> Obtener domicilios
    Obtener Domicilios por Criterio : Obtiene todos los domicilios asociados a un criterio
    de búsqueda, que puede ser por: Barrio, Localidad, Departamento. Los criterios se
    pueden combinar. El criterio se pasa como parámetro.

```http
    GET /api/v1/direccion/direccion?departamento={xxxx}&localidad={xxxx}&barrio={xxxx}
```

# <span style="color: #10a1ff">Autores</span>

Lista de autores del proyecto: [participantes](https://github.com/warriol/dockerRest/graphs/contributors).

<a href="https://github.com/warriol/dockerRest/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=warriol/dockerRest" width="25%"/>
</a>