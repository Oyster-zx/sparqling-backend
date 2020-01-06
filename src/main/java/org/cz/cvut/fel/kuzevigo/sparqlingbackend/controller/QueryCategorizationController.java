package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryInCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto.QueryCategorizationDto;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin( origins = {"http://localhost:3000", "http://194.1.237.231:3000"})
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
    Iterable<QueryCategorization> getQueryCategorizations(@RequestParam(value = "categorizationId") Long categorizationId,
                                                          @RequestParam(value = "categoriesIds") List<Long> categoriesIds) {
        Categorization categorization = categorizationRepository.findById(categorizationId).get();
        return categorization.getQueryCategorizations().stream().filter(queryCategorization ->
                queryCategorization.getCategories().stream().map(Category::getId)
                        .collect(Collectors.toList()).containsAll(categoriesIds)).collect(Collectors.toList());
    }

    @PostMapping("/queryCategorization")
    public QueryCategorization createQueryCategorization(@RequestBody QueryCategorizationDto dto) {
        QueryCategorization queryCategorization = QueryCategorization.builder().build();
        queryCategorization.setCategorization(categorizationRepository.findById(dto.getCategorizationId()).orElseThrow(NoSuchElementException::new));
        queryCategorization.setQueryDocument(queryDocumentRepository.save(dto.getQueryDocument()));
        queryCategorization.setCategories(dto.getCategories());
        queryCategorization = queryCategorizationRepository.save(queryCategorization);
        return queryCategorization;
    }

    @PutMapping("/queryCategorization")
    public ResponseEntity saveQueryCategorization(@RequestBody QueryCategorization dto) {
        QueryCategorization queryCategorization = queryCategorizationRepository.findById(dto.getId()).get();
        queryCategorization.setQueryDocument(dto.getQueryDocument());
        queryCategorization.setCategories(dto.getCategories());
        queryCategorizationRepository.save(queryCategorization);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/queryCategorization")
    public ResponseEntity deleteQueryCategorization(@RequestParam(value = "queryCategorizationId") Long queryCategorizationId) {
        queryCategorizationRepository.deleteById(queryCategorizationId);
        return ResponseEntity.ok().build();
    }
}