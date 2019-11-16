package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import java.util.List;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationScheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;

@RepositoryRestResource
public interface QueryCategorizationRepository extends CrudRepository<QueryCategorization, Long> {

}
