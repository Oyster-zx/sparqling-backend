package org.cz.cvut.fel.kuzevigo.sparqlingbackend;

import java.util.Arrays;
import java.util.HashSet;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategorizationSchemeRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryCategorizationRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentListRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.QueryDocumentRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Categorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategorizationScheme;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.CategoryInCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryCategorization;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocumentList;
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
                        "#All museums (including subclass of museum) in Barcelona with coordinates\n" +
                        "SELECT DISTINCT ?item ?name ?coord ?lat ?lon\n" +
                        "WHERE\n" +
                        "{\n" +
                        " hint:Query hint:optimizer \"None\" .\n" +
                        " ?item wdt:P131* wd:Q1492 .\n" +
                        " ?item wdt:P31/wdt:P279* wd:Q33506 .\n" +
                        " ?item wdt:P625 ?coord .\n" +
                        " ?item p:P625 ?coordinate .\n" +
                        " ?coordinate psv:P625 ?coordinate_node .\n" +
                        " ?coordinate_node wikibase:geoLatitude ?lat .\n" +
                        " ?coordinate_node wikibase:geoLongitude ?lon .\n" +
                        " SERVICE wikibase:label {\n" +
                        " bd:serviceParam wikibase:language \"ca\" .\n" +
                        " ?item rdfs:label ?name\n" +
                        " }\n" +
                        "}\n" +
                        "ORDER BY ASC (?name)").build();
        document3 = queryDocumentRepository.save(document3);

        QueryDocumentList queryDocumentList = QueryDocumentList.builder().title("Test queries").build();
        queryDocumentList.setQueryDocuments(new HashSet<>(Arrays.asList(document1, document2, document3)));
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

        culture.setSubTerms(new HashSet<>(Arrays.asList(museums)));
        culture = categoryRepository.save(culture);

        museums.setSubTerms(new HashSet<>(Arrays.asList(britain, barcelona)));
        museums = categoryRepository.save(museums);

        barcelona.setSubTerms(new HashSet<>(Arrays.asList(coordinates)));
        barcelona = categoryRepository.save(barcelona);

        QueryCategorization queryCategorization1 = QueryCategorization.builder().queryDocument(document1).build();
        queryCategorization1.setCategories(new HashSet<>(Arrays.asList(
                culture,
                museums,
                britain
        )));
        queryCategorization1 = queryCategorizationRepository.save(queryCategorization1);

        QueryCategorization queryCategorization2 = QueryCategorization.builder().queryDocument(document2).build();
        queryCategorization2.setCategories(new HashSet<>(Arrays.asList(
                culture,
                museums,
                barcelona
        )));
        queryCategorization2 = queryCategorizationRepository.save(queryCategorization2);

        QueryCategorization queryCategorization3 = QueryCategorization.builder().queryDocument(document3).build();
        queryCategorization3.setCategories(new HashSet<>(Arrays.asList(
                culture,
                museums,
                barcelona,
                coordinates
        )));
        queryCategorization3 = queryCategorizationRepository.save(queryCategorization3);

        CategorizationScheme categorizationScheme = CategorizationScheme.builder().title("Wikidata tutorial").build();
        categorizationScheme.setCategories(new HashSet<>(Arrays.asList(culture, museums, britain, barcelona, coordinates)));
        categorizationScheme = categorizationSchemeRepository.save(categorizationScheme);

        Categorization categorization = Categorization.builder().queryDocumentList(queryDocumentList)
                .categorizationScheme(categorizationScheme).build();
        categorization = categorizationRepository.save(categorization);
        categorization.setQueryCategorizations(new HashSet<>(Arrays.asList(queryCategorization1, queryCategorization2, queryCategorization3)));
        queryCategorization1.setCategorization(categorization);
        queryCategorization2.setCategorization(categorization);
        queryCategorization3.setCategorization(categorization);
        categorization = categorizationRepository.save(categorization);
        queryCategorization1 = queryCategorizationRepository.save(queryCategorization1);
        queryCategorization2 = queryCategorizationRepository.save(queryCategorization2);
        queryCategorization3 = queryCategorizationRepository.save(queryCategorization3);

        System.out.println();
    }
}