package com.tnosql.v2.api.service;

import com.tnosql.v2.api.model.Direccion;
import com.tnosql.v2.api.repository.IDatosPersonaRepository;
import com.tnosql.v2.api.repository.IDireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionService {

    /**
     * inyeccion de dependencias
     */
    private final IDireccionRepository IDireccionRepository;
    /**
     * inyeccion de dependencias
     */
    private final IDatosPersonaRepository IDatosPersonaRepository;

    /**
     * metodo para obtener todas las direcciones
     */
    public boolean existeCi(String ci) {
        return IDatosPersonaRepository.existsById(ci);
    }

    /**
     * metodo para guardar una direccion, luego de comprobar que no existe en la base de datos
     * recibe como paremetros una ci y un objeto direccion
     */
    public boolean save(Direccion direccion) {
        if (existeCi(direccion.getCi())) {
            IDireccionRepository.save(direccion);
            return true;
        } else {
            return false;
        }
    }

    /**
     * lista direccion segun un parametro ci
     */
    @Cacheable("direccionesCi")
    public List<Direccion> findByCi(String ci) {
        return IDireccionRepository.findByCi(ci);
    }

    /**
     * lista de Direcciones segun un criterio
     * argumentos: departamento, localidad, barrio
     */
    @Cacheable("direccionesParam")
    public List<Direccion> findByDepartamentoOrLocalidadOrBarrio(String departamento, String localidad, String barrio) {
        return IDireccionRepository.findByDepartamentoOrLocalidadOrBarrio(departamento, localidad, barrio);
    }

}
