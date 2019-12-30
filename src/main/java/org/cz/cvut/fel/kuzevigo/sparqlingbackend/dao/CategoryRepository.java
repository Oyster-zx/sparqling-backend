package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);

    @Query("select distinct schema.categories from CategorizationScheme schema join schema.categories where schema.id = :categorizationSchemaId")
    List<Category> findByCategorizationSchemaId(Long categorizationSchemaId);
}
