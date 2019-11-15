package org.cz.cvut.fel.kuzevigo.sparqlingbackend;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        QueryDocument document1 = QueryDocument.builder().title("Museums in Brittany").description("Museums in Brittany")
                .code("#added before 2016-10\n"
                        + "SELECT DISTINCT ?museumLabel ?museumDescription ?villeId ?villeIdLabel (?villeIdLabel AS ?ville) ?coord ?lat ?lon\n"
                        + "WHERE\n"
                        + "{\n"
                        + "  ?museum wdt:P539 ?museofile.  # french museofile Id\n"
                        + "  ?museum wdt:P131* wd:Q12130. # in Brittany\n"
                        + "  ?museum wdt:P131 ?villeId. #city of the museum\n"
                        + "  # ?object wdt:P166 wd:Q2275045 # that have french label \"musées de France\"\n"
                        + "  OPTIONAL {?museum wdt:P856 ?link.}     # official website\n"
                        + "  OPTIONAL {?museum wdt:P625 ?coord .} # geographic coord\n"
                        + "  OPTIONAL {\n"
                        + "    ?museum p:P625 ?statement.\n"
                        + "    ?statement psv:P625 ?node.\n"
                        + "    ?node wikibase:geoLatitude ?lat.\n"
                        + "    ?node wikibase:geoLongitude ?lon.\n"
                        + "   }\n"
                        + "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"fr\". } #french label\n"
                        + "}\n"
                        + "ORDER BY  ?villeIdLabel").build();
        queryDocumentRepository.save(document1);

        Category culture = Category.builder().name("Culture").build();
        categoryRepository.save(culture);
        Category museums = Category.builder().name("Museum").parentCategory(culture).build();
        categoryRepository.save(museums);
        Category britain = Category.builder().name("Britain").parentCategory(museums).build();
        categoryRepository.save(britain);
        Category barcelona  = Category.builder().name("Barcelona").parentCategory(museums).build();
        categoryRepository.save(barcelona);
        Category coordinates  = Category.builder().name("Coordinates").parentCategory(barcelona).build();
        categoryRepository.save(coordinates);


    }
}