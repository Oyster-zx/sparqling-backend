package org.cz.cvut.fel.kuzevigo.sparqlingbackend.dto;

import lombok.Builder;
import lombok.Data;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;

import java.util.List;

@Data
@Builder
public class QueryDocumentDto {

    Long id;
    String title;
    String description;
    String code;
}
