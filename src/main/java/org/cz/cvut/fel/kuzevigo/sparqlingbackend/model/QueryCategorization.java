package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"categoryInCategorizations"})
public class QueryCategorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

//    @JsonIgnore
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    Categorization categorization;

    @OneToOne(cascade = CascadeType.MERGE)
    QueryDocument queryDocument;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<CategoryInCategorization> categoryInCategorizations;
}