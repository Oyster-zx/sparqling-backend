package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationScheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategorizationSchemeRepository extends CrudRepository<CategorizationScheme, Long> {

}
