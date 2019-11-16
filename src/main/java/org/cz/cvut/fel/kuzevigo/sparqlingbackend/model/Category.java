package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer.CategorySerializer;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "subTerms")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;

    @JsonSerialize(using = CategorySerializer.class)
    @JsonIgnoreProperties("subTerms")
    @OneToMany
    List<Category> subTerms;
}