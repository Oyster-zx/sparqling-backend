package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin( origins = "http://localhost:3000")
public class QueryCategorizationController {

    @Autowired
    QueryCategorizationRepository queryCategorizationRepository;

    @GetMapping("/queryCategorizations")
    Iterable<QueryCategorization> getQueryCategorizations(){
        return queryCategorizationRepository.findAll();
    }
}