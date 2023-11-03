package com.tnosql.v2.api.controller;

import com.tnosql.v2.api.model.Direccion;
import com.tnosql.v2.api.service.DireccionService;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SpringBootApplication
public class DireccionController {
    /**
     * instancia de DireccionService
     */
    private final DireccionService direccionService;
    /**
     * recurso para guardar una direccion en una persona ya existente
     */
    @Operation(summary = "Guardar un objeto direccion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objeto guardado correctamente",
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "402", description = "No existe una persona con la cédula aportada como parámetro",
            content = @Content)
    })
    @PostMapping("/direccion")
    public ResponseEntity<?> save(@RequestBody Direccion direccion) {
        boolean saved =direccionService.save(direccion);
        Map<String, String> response = new HashMap<>();
        Integer codigo=0;
        if (saved) {
            codigo=200;
            response.put("codigo", "200");
            response.put("estado", "OK");
            response.put("mensaje", "El domicilio se guardó correctamente");
        } else {
            codigo=402;
            response.put("codigo", "402");
            response.put("estado", "error");
            response.put("mensaje", "No existe una persona con la cédula aportada como parámetro");
        }
        return ResponseEntity.status(codigo).body(response);
    }
    /**
     * recurso para obtener todas las direcciones de una persona
     */
    @Operation(summary = "Buscar un objeto direccion por cedula.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objeto encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "402", description = "No existe domicilios para la cédula aportada como parámetro",
            content = @Content)
    })
    @GetMapping("/direccion/{ci}")
    public ResponseEntity<?> findByCi(@PathVariable String ci) {
        List<Direccion> aretornar= direccionService.findByCi(ci);
        if(!aretornar.isEmpty()){
            return ResponseEntity.ok(aretornar);
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("codigo", "402");
            response.put("estado", "error");
            response.put("mensaje", "No existe domicilios para la cédula aportada como parámetro");

            return ResponseEntity.status(401).body(response);
        }
    }
    /**
     * recurso para obtener domcilios segun criterios
     */
    @Operation(summary = "Buscar direcciones por criterios")
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
        return direccionService.findByDepartamentoAndLocalidadAndBarrio(departamento, localidad, barrio);
    }
}
