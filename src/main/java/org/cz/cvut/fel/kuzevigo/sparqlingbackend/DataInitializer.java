package org.cz.cvut.fel.kuzevigo.sparqlingbackend;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemeRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentListRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Categorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationScheme;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocumentList;
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
    QueryDocumentListRepository queryDocumentListRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategorizationSchemeRepository categorizationSchemeRepository;

    @Autowired
    QueryCategorizationRepository queryCategorizationRepository;

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

        QueryDocument document3 = QueryDocument.builder().title("All museums in Barcelona with coordinates")
                .description("All museums in Barcelona with coordinates and some more description")
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

        QueryDocumentList queryDocumentList = QueryDocumentList.builder().title("Test queries").build();
        queryDocumentList.setQueryDocuments(Arrays.asList(document1, document2, document3));
        queryDocumentListRepository.save(queryDocumentList);

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

        QueryCategorization queryCategorization1 = QueryCategorization.builder().queryDocument(document1)
                .categories(Arrays.asList(culture, museums, britain)).build();
        queryCategorization1 = queryCategorizationRepository.save(queryCategorization1);
        QueryCategorization queryCategorization2 = QueryCategorization.builder().queryDocument(document2)
                .categories(Arrays.asList(culture, museums, barcelona)).build();
        queryCategorization2 = queryCategorizationRepository.save(queryCategorization2);
        QueryCategorization queryCategorization3 = QueryCategorization.builder().queryDocument(document3)
                .categories(Arrays.asList(culture, museums, barcelona, coordinates)).build();
        queryCategorization3 = queryCategorizationRepository.save(queryCategorization3);

        CategorizationScheme categorizationScheme = CategorizationScheme.builder().title("Wikidata tutorial").build();
        categorizationScheme.setCategories(Arrays.asList(culture, museums, britain, barcelona, coordinates));
        categorizationSchemeRepository.save(categorizationScheme);

        Categorization categorization = Categorization.builder().queryDocumentList(queryDocumentList)
                .categorizationScheme(categorizationScheme)
                .queryCategorizations(Arrays.asList(queryCategorization1, queryCategorization2, queryCategorization3)).build();
        categorizationRepository.save(categorization);
    }
}