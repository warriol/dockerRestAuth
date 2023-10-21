package com.tnosql.v2.api.controller;

import com.github.javafaker.Faker;
import com.tnosql.v2.api.model.DatosPersona;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class DatosPersonaControllerTest {

    private RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/v1";

    @Test
    public void testSavePersona() {
        int cantidadPersonas = 500;

        for (int i = 0; i < cantidadPersonas; i++) {

            // Generar un número de cédula aleatorio dentro del rango especificado
            long ci = (long) (Math.random() * (55000009 - 10000000) + 10000000);

            // Crear datos de prueba con Faker
            Faker faker = new Faker();
            DatosPersona datosPersona = new DatosPersona();
            datosPersona.setCi(String.valueOf(ci));
            datosPersona.setNombre(faker.name().firstName());
            datosPersona.setApellido(faker.name().lastName());
            datosPersona.setEdad(faker.number().numberBetween(1, 100));

            // Realizar la solicitud POST para guardar la persona
            ResponseEntity<?> response = restTemplate.postForEntity(baseUrl + "/personas", datosPersona, ResponseEntity.class);

            // Verificar la respuesta
            assertEquals(200, response.getStatusCodeValue());

        }
    }

}
