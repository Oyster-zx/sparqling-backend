package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto;

import lombok.Builder;
import lombok.Data;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.QueryDocument;

import java.util.List;

@Data
@Builder
public class QueryCategorizationDto {

    Long id;
    QueryDocument queryDocument;
    List<Category> categories;
}
