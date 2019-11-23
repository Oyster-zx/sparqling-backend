package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"categories", "categorization"})
public class QueryCategorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonIgnore
    @ManyToOne
    Categorization categorization;

    @OneToOne(cascade = CascadeType.MERGE)
    QueryDocument queryDocument;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Category> categories;
}