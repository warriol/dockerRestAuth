package com.tnosql.v2.api.repository;

import com.tnosql.v2.api.model.Direccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDireccionRepository extends MongoRepository<Direccion, String> {

    /**
     * lista direccion segun un parametro ci
     */
    List<Direccion> findByCi(String ci);

    /**
     * lista de Direcciones segun un criterio
     * argumentos: departamento, localidad, barrio
     */
    List<Direccion> findByDepartamentoOrLocalidadOrBarrio(String departamento, String localidad, String barrio);
}
