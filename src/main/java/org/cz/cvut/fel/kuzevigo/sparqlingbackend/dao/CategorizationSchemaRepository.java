package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationSchema;

@RepositoryRestResource
public interface CategorizationSchemaRepository extends CrudRepository<CategorizationSchema, Long> {

}
