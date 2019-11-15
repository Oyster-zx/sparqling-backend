package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemaRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO ikuzevanov popis
 * <br>Historie:<br>
 * {{SVN-LOG}}
 * @author ikuzevanov on 2019-11-15.
 */
@RestController
public class CategorizationSchemaController {

    @Autowired
    CategorizationSchemaRepository categorizationSchemaRepository;

    @GetMapping("/categorizationSchemas")
    Iterable<CategorizationSchema> getCategorizationSchemas() {
        return categorizationSchemaRepository.findAll();
    }
}