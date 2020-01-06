package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemeRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationScheme;
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
@CrossOrigin( origins = {"http://localhost:3000", "http://194.1.237.231:3000"})
public class CategorizationSchemaController {

    @Autowired
    CategorizationSchemeRepository categorizationSchemeRepository;

    @GetMapping("/categorizationSchemas")
    Iterable<CategorizationScheme> getCategorizationSchemas() {
        return categorizationSchemeRepository.findAll();
    }
}