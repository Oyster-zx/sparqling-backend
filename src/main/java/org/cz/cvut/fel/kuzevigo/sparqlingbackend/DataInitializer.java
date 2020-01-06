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

        QueryDocument document4 = QueryDocument.builder().title("Cathedrals in Paris")
                .description("Cathedrals in Paris")
                .code("SELECT ?item ?itemLabel ?placeLabel ?coords ?image\n" +
                        "WHERE\n" +
                        "{\n" +
                        "  ?item wdt:P31 wd:Q2977 .\n" +
                        "  ?item wdt:P131 ?place .\n" +
                        "  ?place wdt:P131 wd:Q90 .\n" +
                        "  OPTIONAL { ?item wdt:P625 ?coords . }\n" +
                        "  OPTIONAL { ?item wdt:P18 ?image . }\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"fr\" . }\n" +
                        "} ORDER BY ?placeLabel ?itemLabel").build();
        document4 = queryDocumentRepository.save(document4);

        QueryDocument document5 = QueryDocument.builder().title("Churches in church district Wittenberg")
                .description("Churches in church district Wittenberg")
                .code("#defaultView:Map{\"layer\": \"?pbLabel\"}\n" +
                        "SELECT ?item ?itemLabel ?pbLabel (SAMPLE(?cat) AS ?cat) (SAMPLE(?coord) AS ?coord) (SAMPLE(?img) AS ?img)\n" +
                        "WHERE {\n" +
                        "  wd:Q75849591 wdt:P527 [ wdt:P527 ?item; wdt:P361 ?pb ].\n" +
                        "  ?pb wdt:P31 wd:Q76598130.\n" +
                        "  ?item wdt:P625 ?coord.\n" +
                        "  OPTIONAL { ?item wdt:P373 ?cat. }\n" +
                        "  OPTIONAL { ?item wdt:P18 ?img. }\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"de\". }\n" +
                        "} GROUP BY ?item ?itemLabel ?pbLabel").build();
        document5 = queryDocumentRepository.save(document5);

        QueryDocument document6 = QueryDocument.builder().title("Museums in Antwerp")
                .description("Museums in Antwerp")
                .code("#defaultView:Map\n" +
                        "SELECT ?item ?itemLabel ?coordinates\n" +
                        "WHERE\n" +
                        "{\n" +
                        "  ?item wdt:P31/wdt:P279* wd:Q33506 ;\n" +
                        "        wdt:P131 wd:Q12892 ;\n" +
                        "        wdt:P625 ?coordinates .\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"nl, en\" }\n" +
                        "  }").build();
        document6 = queryDocumentRepository.save(document6);

        QueryDocument document7 = QueryDocument.builder().title("Louvre artworks in display cases\n")
                .description("Louvre artworks in display cases\n")
                .code("#defaultView:ImageGrid\n" +
                        "SELECT ?item ?itemLabel ?itemDescription ?image WHERE {\n" +
                        "  #part1: objects in cases\n" +
                        "  {\n" +
                        "  ?item wdt:P276             ?case     .\n" +
                        "  ?case wdt:P31            wd:Q3561331 .\n" +
                        "  \n" +
                        "  ?case wdt:P276             ?room     .\n" +
                        "  ?room wdt:P31/wdt:P279*  wd:Q180516  . # wd:Q15206795\n" +
                        "  \n" +
                        "  ?room wdt:P466             ?dep      .\n" +
                        "  ?dep  wdt:P361+          wd:Q19675\n" +
                        "  }       \n" +
                        "  \n" +
                        "  OPTIONAL { ?item wdt:P18 ?image } # Optionally with an image\n" +
                        "\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],en,fr\" }\n" +
                        "}").build();
        document7 = queryDocumentRepository.save(document7);

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
        Category churches = Category.builder().name("Churches").build();
        churches = categoryRepository.save(churches);
        Category paris = Category.builder().name("Paris").build();
        paris = categoryRepository.save(paris);
        Category wittenberg = Category.builder().name("Wittenberg").build();
        wittenberg = categoryRepository.save(wittenberg);
        Category antwerp = Category.builder().name("Antwerp").build();
        antwerp = categoryRepository.save(antwerp);
        Category louvre = Category.builder().name("Louvre").build();
        louvre = categoryRepository.save(louvre);

        culture.setSubTerms(new HashSet<>(Arrays.asList(museums, churches)));
        culture = categoryRepository.save(culture);

        museums.setSubTerms(new HashSet<>(Arrays.asList(britain, barcelona, antwerp, louvre)));
        museums = categoryRepository.save(museums);

        churches.setSubTerms(new HashSet<>(Arrays.asList(paris, wittenberg)));
        churches = categoryRepository.save(churches);

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

        QueryCategorization queryCategorization4 = QueryCategorization.builder().queryDocument(document4).build();
        queryCategorization4.setCategories(new HashSet<>(Arrays.asList(
                culture,
                churches,
                paris
        )));
        queryCategorization4 = queryCategorizationRepository.save(queryCategorization4);


        QueryCategorization queryCategorization5 = QueryCategorization.builder().queryDocument(document5).build();
        queryCategorization5.setCategories(new HashSet<>(Arrays.asList(
                culture,
                churches,
                wittenberg
        )));
        queryCategorization5 = queryCategorizationRepository.save(queryCategorization5);

        QueryCategorization queryCategorization6 = QueryCategorization.builder().queryDocument(document6).build();
        queryCategorization6.setCategories(new HashSet<>(Arrays.asList(
                culture,
                museums,
                antwerp
        )));
        queryCategorization6 = queryCategorizationRepository.save(queryCategorization6);

        QueryCategorization queryCategorization7 = QueryCategorization.builder().queryDocument(document7).build();
        queryCategorization7.setCategories(new HashSet<>(Arrays.asList(
                culture,
                museums,
                louvre
        )));
        queryCategorization7 = queryCategorizationRepository.save(queryCategorization7);

        CategorizationScheme categorizationScheme = CategorizationScheme.builder().title("Wikidata tutorial").build();
        categorizationScheme.setCategories(new HashSet<>(Arrays.asList(culture, museums, britain, barcelona, coordinates,
                churches, paris, wittenberg, antwerp, louvre)));
        categorizationScheme = categorizationSchemeRepository.save(categorizationScheme);

        Categorization categorization = Categorization.builder().categorizationScheme(categorizationScheme).build();
        categorization = categorizationRepository.save(categorization);
        categorization.setQueryCategorizations(new HashSet<>(Arrays.asList(queryCategorization1, queryCategorization2, queryCategorization3
                , queryCategorization4, queryCategorization5, queryCategorization6, queryCategorization7)));
        queryCategorization1.setCategorization(categorization);
        queryCategorization2.setCategorization(categorization);
        queryCategorization3.setCategorization(categorization);
        queryCategorization4.setCategorization(categorization);
        queryCategorization5.setCategorization(categorization);
        queryCategorization6.setCategorization(categorization);
        queryCategorization7.setCategorization(categorization);
        categorization = categorizationRepository.save(categorization);
        queryCategorization1 = queryCategorizationRepository.save(queryCategorization1);
        queryCategorization2 = queryCategorizationRepository.save(queryCategorization2);
        queryCategorization3 = queryCategorizationRepository.save(queryCategorization3);
        queryCategorization4 = queryCategorizationRepository.save(queryCategorization4);
        queryCategorization5 = queryCategorizationRepository.save(queryCategorization5);
        queryCategorization6 = queryCategorizationRepository.save(queryCategorization6);
        queryCategorization7 = queryCategorizationRepository.save(queryCategorization7);

        System.out.println();
    }
}