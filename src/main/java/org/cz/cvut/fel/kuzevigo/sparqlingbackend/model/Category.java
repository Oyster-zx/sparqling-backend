package org.cz.cvut.fel.kuzevigo.sparqlingbackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer.CategoryDeserializer;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer.CategorySerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@EqualsAndHashCode(exclude = "subTerms")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;

    @JsonSerialize(using = CategorySerializer.class)
    @JsonDeserialize(using = CategoryDeserializer.class)
    @JsonIgnoreProperties("subTerms")
    @OneToMany
    Set<Category> subTerms;
}