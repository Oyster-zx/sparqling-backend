package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryInCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto.QueryCategorizationDto;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategoryInCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class QueryCategorizationController {

    @Autowired
    QueryCategorizationRepository queryCategorizationRepository;

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @Autowired
    CategoryInCategorizationRepository categoryInCategorizationRepository;

    @GetMapping("/queryCategorizations")
    Iterable<QueryCategorization> getQueryCategorizations() {
        return queryCategorizationRepository.findAll();
    }

    @PostMapping("/createQueryCategorization")
    public ResponseEntity createQueryCategorization(@RequestBody QueryCategorizationDto dto) {
        QueryCategorization queryCategorization = QueryCategorization.builder().build();
        queryCategorization.setCategorization(categorizationRepository.findById(dto.getCategorizationId()).orElseThrow(NoSuchElementException::new));
        queryCategorization.setQueryDocument(queryDocumentRepository.save(dto.getQueryDocument()));
        queryCategorization.setCategories(dto.getCategories());
        queryCategorization = queryCategorizationRepository.save(queryCategorization);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateQueryCategorization")
    public ResponseEntity saveQueryCategorization(@RequestBody QueryCategorization dto) {
        QueryCategorization queryCategorization = queryCategorizationRepository.findById(dto.getId()).get();
        queryCategorization.setQueryDocument(dto.getQueryDocument());
        queryCategorization.setCategories(dto.getCategories());
        queryCategorizationRepository.save(queryCategorization);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteQueryCategorization/{id}")
    public ResponseEntity deleteQueryCategorization(@PathVariable Long id) {
        queryCategorizationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}