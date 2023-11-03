package com.tnosql.v2.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Document(collection = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion implements Serializable {
    @Id
    private String id;                      // Identificador único generado por MongoDB
    @Indexed
    private String ci;                      // Clave de identificación única de la persona
    @Indexed
    private String departamento;
    @Indexed
    private String localidad;
    @Indexed
    private String barrio;
    private String calle;
    private String nro;
    private String apartamento;
    private String padron;
    private String ruta;
    private int km;
    private String letra;
    /**
     * No se construyen GETTERS y SETTERS porque Lombok los genera automáticamente
     */
}
