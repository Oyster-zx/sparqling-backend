package org.cz.cvut.fel.kuzevigo.sparqlingbackend.controller;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO ikuzevanov popis
 * <br>Historie:<br>
 * {{SVN-LOG}}
 *
 * @author ikuzevanov on 2019-11-15.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class QueryDocumentController {

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @GetMapping("/queryDocuments")
    Iterable<QueryDocument> getQueryDocuments(@RequestParam(value = "names", required = false) List<String> names, Pageable pageable) {
        if (names != null) {
            return queryDocumentRepository.findAllByIdIn(names, pageable);
        } else {
            return queryDocumentRepository.findAll();
        }
    }
}