package com.tnosql.v2.api.repository;

import com.tnosql.v2.api.model.Direccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RedisHash("Direccion")
public interface IDireccionRepository extends MongoRepository<Direccion, String> {
    /**
     * lista direccion segun un parametro ci
     */
    List<Direccion> findByCi(String ci);
    /**
     * lista de Direcciones segun un criterio
     * argumentos: departamento, localidad, barrio
     */
    List<Direccion> findByDepartamentoAndLocalidadAndBarrio(String departamento, String localidad, String barrio);
    List<Direccion> findByDepartamentoAndLocalidad(String departamento, String localidad);
    List<Direccion> findByLocalidadAndBarrio(String localidad, String barrio);
    List<Direccion> findByDepartamentoAndBarrio(String departamento, String barrio);
    List<Direccion> findByDepartamento(String departamento);
    List<Direccion> findByLocalidad(String localidad);
    List<Direccion> findByBarrio(String barrio);
    List<Direccion> findByDepartamentoOrLocalidadOrBarrio(String departamento, String localidad, String barrio);
}
