package org.cz.cvut.fel.kuzevigo.sparqlingbackend;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemaRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Categorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationSchema;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    QueryDocumentRepository queryDocumentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategorizationSchemaRepository categorizationSchemaRepository;

    @Autowired
    CategorizationRepository categorizationRepository;

    @Override
    public void run(String... args) throws Exception {
        QueryDocument document1 = QueryDocument.builder().title("Museums in Brittany").description("Museums in Brittany with some more description")
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
        document1 = queryDocumentRepository.save(document1);

        QueryDocument document2 = QueryDocument.builder().title("All museums in Barcelona").description("All museums in Barcelona with some more description")
                .code("#added before 2016-10\n" +
                        "\n" +
                        "#All museums (including subclass of museum) in Barcelona\n" +
                        "SELECT DISTINCT ?item ?name ?lat ?lon\n" +
                        "WHERE\n" +
                        "{\n" +
                        " hint:Query hint:optimizer \"None\" .\n" +
                        " ?item wdt:P131* wd:Q1492 .\n" +
                        " ?item wdt:P31/wdt:P279* wd:Q33506 .\n" +
                        " SERVICE wikibase:label {\n" +
                        " bd:serviceParam wikibase:language \"ca\" .\n" +
                        " ?item rdfs:label ?name\n" +
                        " }\n" +
                        "}\n" +
                        "ORDER BY ASC (?name)").build();
        document2 = queryDocumentRepository.save(document2);

        QueryDocument document3 = QueryDocument.builder().title("All museums in Barcelona with coordinates").description("All museums in Barcelona with coordinates and some more description")
                .code("#added before 2016-10\n" +
                        "\n" +
                        "#All museums (including subclass of museum) in Barcelona\n" +
                        "SELECT DISTINCT ?item ?name ?lat ?lon\n" +
                        "WHERE\n" +
                        "{\n" +
                        " hint:Query hint:optimizer \"None\" .\n" +
                        " ?item wdt:P131* wd:Q1492 .\n" +
                        " ?item wdt:P31/wdt:P279* wd:Q33506 .\n" +
                        " SERVICE wikibase:label {\n" +
                        " bd:serviceParam wikibase:language \"ca\" .\n" +
                        " ?item rdfs:label ?name\n" +
                        " }\n" +
                        "}\n" +
                        "ORDER BY ASC (?name)").build();
        document3 = queryDocumentRepository.save(document3);

        Category culture = Category.builder().name("Culture").build();
        culture = categoryRepository.save(culture);
        Category museums = Category.builder().name("Museum").build();
        museums = categoryRepository.save(museums);
        Category britain = Category.builder().name("Britain").build();
        britain = categoryRepository.save(britain);
        Category barcelona = Category.builder().name("Barcelona").build();
        barcelona = categoryRepository.save(barcelona);
        Category coordinates = Category.builder().name("Coordinates").build();
        coordinates = categoryRepository.save(coordinates);

        culture.setSubTerms(Arrays.asList(museums));
        culture = categoryRepository.save(culture);

        museums.setSubTerms(Arrays.asList(britain, barcelona));
        museums = categoryRepository.save(museums);

        barcelona.setSubTerms(Arrays.asList(coordinates));
        barcelona = categoryRepository.save(barcelona);

        Categorization categorization1 = Categorization.builder().queryDocument(document1)
                .categories(Arrays.asList(culture, museums, britain)).build();
        categorization1 = categorizationRepository.save(categorization1);
        Categorization categorization2 = Categorization.builder().queryDocument(document2)
                .categories(Arrays.asList(culture, museums, barcelona)).build();
        categorization2 = categorizationRepository.save(categorization2);
        Categorization categorization3 = Categorization.builder().queryDocument(document3)
                .categories(Arrays.asList(culture, museums, barcelona, coordinates)).build();
        categorization3 = categorizationRepository.save(categorization3);

        CategorizationSchema categorizationSchema = CategorizationSchema.builder().name("schema1").build();
        categorizationSchema.setCategorizations(new ArrayList<>());
        categorizationSchema.getCategorizations().add(categorization1);
        categorizationSchema.getCategorizations().add(categorization2);
        categorizationSchema.getCategorizations().add(categorization3);
        categorizationSchemaRepository.save(categorizationSchema);
    }
}