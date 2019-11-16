package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;

import java.util.List;

@RepositoryRestResource
public interface QueryDocumentRepository extends CrudRepository<QueryDocument, Long> {
    Page<QueryDocument> findAllByIdIn(List<String> ids, Pageable pageable);
}