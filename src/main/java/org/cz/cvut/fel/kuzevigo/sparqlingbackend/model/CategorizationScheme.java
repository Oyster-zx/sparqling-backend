package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import java.util.Set;

import javax.persistence.*;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer.CategorySerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategorizationScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column
    String title;
    @Column
    String description;

    @JsonSerialize(using = CategorySerializer.class)
    @OneToMany(fetch = FetchType.EAGER)
    Set<Category> categories;
}