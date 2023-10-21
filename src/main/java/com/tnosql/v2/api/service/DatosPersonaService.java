package com.tnosql.v2.api.service;

import com.tnosql.v2.api.model.DatosPersona;
import com.tnosql.v2.api.repository.IDatosPersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatosPersonaService {

    /**
     * instancia de DatosPersonaRepository
     */
    private final IDatosPersonaRepository IDatosPersonaRepository;

    /**
     * metodo para controlar que ci no existe en la base de datos
     */
    public boolean existeCi(String ci) {
        return IDatosPersonaRepository.existsById(ci);
    }

    /**
     * metodo para obtener una lista las personas
     */
    public List<DatosPersona> findAll() {
        return IDatosPersonaRepository.findAll();
    }

    /**
     * metodo para guardar una persona,
     * luego de comprobar que no existe en la base de datos
     */
    public boolean save(DatosPersona datosPersona) {
        if (!existeCi(datosPersona.getCi())) {
            IDatosPersonaRepository.save(datosPersona);
            return true;
        }else {
            return false;
        }
    }

}
