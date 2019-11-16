package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "categories")
public class QueryCategorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    QueryDocument queryDocument;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Category> categories;
}