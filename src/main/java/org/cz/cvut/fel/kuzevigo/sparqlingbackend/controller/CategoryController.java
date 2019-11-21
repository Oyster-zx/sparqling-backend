package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemeRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO ikuzevanov popis
 * <br>Historie:<br>
 * {{SVN-LOG}}
 * @author ikuzevanov on 2019-11-15.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QueryCategorizationRepository queryCategorizationRepository;

    @Autowired
    CategorizationSchemeRepository categorizationSchemeRepository;

    @GetMapping("/categories")
    Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }
}