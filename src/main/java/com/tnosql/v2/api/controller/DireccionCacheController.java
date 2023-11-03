package com.tnosql.v2.api.controller;

import com.tnosql.v2.api.model.Direccion;
import com.tnosql.v2.api.service.DireccionCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cache")
@RequiredArgsConstructor
@SpringBootApplication
public class DireccionCacheController {

    /**
     * instancia de DireccionService
     */
    private final DireccionCacheService direccionCacheService;
    private static final Logger logger = Logger.getLogger(DireccionCacheController.class.getName());

    /**
     * recurso para obtener todas las direcciones de una persona
     */
    @Operation(summary = "Buscar un objeto direccion por cedula (CACHEABLE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto encontrado",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "402", description = "No existe domicilios para la cédula aportada como parámetro",
                    content = @Content)
    })
    @GetMapping("/direccion/{ci}")
    public ResponseEntity<?> findByCi(@PathVariable String ci) {
        try {
            List<Direccion> aretornar= direccionCacheService.findByCi(ci);
            if(!aretornar.isEmpty()){
                return ResponseEntity.ok(aretornar);
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("codigo", "402");
                response.put("estado", "error");
                response.put("mensaje", "No existe domicilios para la cédula aportada como parámetro");

                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            // Log any exceptions
            logger.severe("Error en findByCi: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("codigo", "500");
            response.put("estado", "error");
            response.put("mensaje",  e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    /**
     * recurso para obtener domcilios segun criterios
     */
    @Operation(summary = "Buscar direcciones por criterios (CACHEABLE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron resultados",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "402", description = "Sin resultados",
                    content = @Content)
    })
    @GetMapping("/direccion")
    public List<Direccion> findByDepartamentoOrLocalidadOrBarrio(
            @RequestParam(name = "departamento", required = false) String departamento,
            @RequestParam(name = "localidad", required = false) String localidad,
            @RequestParam(name = "barrio", required = false) String barrio) {
        return direccionCacheService.findByDepartamentoAndLocalidadAndBarrio(departamento, localidad, barrio);
    }
}
