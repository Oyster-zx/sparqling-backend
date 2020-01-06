package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Categorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin( origins = {"http://localhost:3000", "http://194.1.237.231:3000"})
public class CategorizationController {

    @Autowired
    CategorizationRepository categorizationRepository;

    @GetMapping("/categorizations")
    Iterable<Categorization> getCategorizations(){
        return categorizationRepository.findAll();
    }
}