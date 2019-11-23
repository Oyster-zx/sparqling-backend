package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    CategorizationScheme categorizationScheme;

    @OneToMany(mappedBy = "categorization",
            fetch = FetchType.EAGER)
    Set<QueryCategorization> queryCategorizations;

    @JsonIgnore
    @ManyToOne
    QueryDocumentList queryDocumentList;
}