package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface QueryCategorizationRepository extends CrudRepository<QueryCategorization, Long> {

}
