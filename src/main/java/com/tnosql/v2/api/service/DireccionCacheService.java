// public class DireccionCacheService
package com.tnosql.v2.api.service;

import com.tnosql.v2.api.model.Direccion;
import com.tnosql.v2.api.repository.IDireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionCacheService {
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * inyeccion de dependencias
     */
    @Autowired
    private final IDireccionRepository IDireccionRepository;

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
    public List<Direccion> findByDepartamentoAndLocalidadAndBarrio(String departamento, String localidad, String barrio) {
        Criteria criteria = new Criteria();

        if (departamento != null) {
            criteria = criteria.and("departamento").is(departamento);
        }
        if (localidad != null) {
            criteria = criteria.and("localidad").is(localidad);
        }
        if (barrio != null) {
            criteria = criteria.and("barrio").is(barrio);
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Direccion.class);
    }

}
