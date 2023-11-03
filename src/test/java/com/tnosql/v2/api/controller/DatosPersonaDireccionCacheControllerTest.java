package com.tnosql.v2.api.controller;

import com.github.javafaker.Faker;
import com.tnosql.v2.api.model.DatosPersona;
import com.tnosql.v2.api.model.Direccion;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DatosPersonaDireccionCacheControllerTest {

    private RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/v1";

    @Test
    public void testSavePersonasYDomicilios() {
        int cantidadPersonas = 500;
        int cantidadDomicilios;

        List<String> departamentos = Arrays.asList(
                "artigas", "canelones", "cerro largo", "colonia", "durazno", "flores", "florida", "lavalleja",
                "maldonado", "montevideo", "paysandú", "río negro", "rivera", "rocha", "salto", "san josé", "soriano",
                "tacuarembó", "treinta y tres");
        List<String> ciudadesUruguay = Arrays.asList(
                "cerro", "tarariras", "san jose de mayo", "ciudadela", "Las piedras", "centro", "barros blancos",
                "prado", "melilla", "pueblo chico", "nacimiento", "cabo polonio", "cerro chato", "cerro pelado",
                "trinidad", "surazno", "merin", "pando", "minas", "salinas");

        for (int i = 0; i < cantidadPersonas; i++) {
            // Generar un número de cédula aleatorio dentro del rango especificado
            long ci = (long) (Math.random() * (55000009 - 10000000) + 10000000);

            // Crear datos de prueba para la persona con Faker
            Faker faker = new Faker();
            DatosPersona datosPersona = new DatosPersona();
            datosPersona.setCi(String.valueOf(ci));
            datosPersona.setNombre(faker.name().firstName());
            datosPersona.setApellido(faker.name().lastName());
            datosPersona.setEdad(faker.number().numberBetween(1, 100));

            // Realizar la solicitud POST para guardar la persona
            ResponseEntity<?> responsePersona = restTemplate.postForEntity(baseUrl + "/personas", datosPersona, ResponseEntity.class);

            // Verificar la respuesta
            assertEquals(200, responsePersona.getStatusCodeValue()); // Ajusta según tu respuesta esperada

            cantidadDomicilios = (int) (Math.random() * 6) + 1;

            for (int i2 = 0; i2 < cantidadDomicilios; i2++) {
                Random random = new Random();

                // Crear datos de prueba para el domicilio con Faker
                Direccion direccion = new Direccion();
                direccion.setCi(String.valueOf(ci));
                direccion.setDepartamento(departamentos.get(random.nextInt(departamentos.size())));
                direccion.setLocalidad(ciudadesUruguay.get(random.nextInt(ciudadesUruguay.size())));
                direccion.setBarrio(faker.address().cityName());
                direccion.setCalle(faker.address().streetName());
                direccion.setNro(faker.address().buildingNumber());
                direccion.setApartamento(faker.address().secondaryAddress());
                direccion.setPadron(faker.number().digits(5));
                direccion.setRuta(faker.address().streetName());
                direccion.setKm(faker.number().numberBetween(1, 100));
                direccion.setLetra(faker.letterify("?"));

                // Realizar la solicitud POST para guardar el domicilio
                ResponseEntity<?> responseDomicilio = restTemplate.postForEntity(baseUrl + "/direccion", direccion, ResponseEntity.class);

                // Verificar la respuesta
                assertEquals(200, responseDomicilio.getStatusCodeValue());
            }
        }
    }
}
