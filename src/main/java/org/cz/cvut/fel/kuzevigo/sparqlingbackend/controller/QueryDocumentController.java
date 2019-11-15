package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
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
public class QueryDocumentController {

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @GetMapping("/queryDocuments")
    Iterable<QueryDocument> getQueryDocuments() {
        return queryDocumentRepository.findAll();
    }
}