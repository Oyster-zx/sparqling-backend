package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto.QueryCategorizationDto;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto.QueryDocumentDto;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Categorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO ikuzevanov popis
 * <br>Historie:<br>
 * {{SVN-LOG}}
 *
 * @author ikuzevanov on 2019-11-15.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://194.1.237.231:3000"})
public class QueryDocumentController {

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    QueryCategorizationRepository queryCategorizationRepository;

    @GetMapping("/queryDocuments")
    Iterable<QueryCategorization> getQueryDocuments(@RequestParam(value = "categorizationId") Long categorizationId,
                                                    @RequestParam(value = "categoriesIds") List<Long> categoriesIds) {
        Categorization categorization = categorizationRepository.findById(categorizationId).get();
        return categorization.getQueryCategorizations().stream().filter(queryCategorization ->
                queryCategorization.getCategories().stream().map(Category::getId)
                        .collect(Collectors.toList()).containsAll(categoriesIds)).collect(Collectors.toList());
    }
}