package org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.dao.CategoryRepository;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CategoryDeserializer extends JsonDeserializer<Set<Category>> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Set<Category> deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        Set<Category> categories = new HashSet<>();
        for (Iterator<JsonNode> it = node.iterator(); it.hasNext(); ) {
            JsonNode value = it.next();
            categories.add(categoryRepository.findByName(value.textValue()));
        }
        return categories;
    }
}