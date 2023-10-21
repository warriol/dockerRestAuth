package com.tnosql.v2.api.controller;

import com.tnosql.v2.api.model.DatosPersona;
import com.tnosql.v2.api.service.DatosPersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SpringBootApplication
public class DatosPersonaController {

    /**
     * instancia de DatosPersonaService
     */
    private final DatosPersonaService datosPersonaService;

    /**
     * recurso para obtener una lista de personas
     */
    @Operation(summary = "Obtener una lista de personas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de personas",
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @GetMapping("/personas")
    public List<DatosPersona> findAll() {
        return datosPersonaService.findAll();
    }

    /**
     * recurso para guardar una persona
     */
    @Operation(summary = "Guardar un objeto persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objeto guardado correctamente",
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "La Persona ya Existe",
            content = @Content)
    })
    @PostMapping("/personas")
    public ResponseEntity<?> save(@RequestBody DatosPersona datosPersona) {
        boolean saved = datosPersonaService.save(datosPersona);
        if (saved) {
            return ResponseEntity.ok().build(); // HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La Persona ya Existe"); // HTTP 401 Unauthorized
        }
    }

}
