package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
