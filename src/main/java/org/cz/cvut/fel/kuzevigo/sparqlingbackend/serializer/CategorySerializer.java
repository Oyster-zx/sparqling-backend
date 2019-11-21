package org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer;

import java.io.IOException;
import java.util.Set;

import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class CategorySerializer extends StdSerializer<Set<Category>> {

    public CategorySerializer() {
        this(null);
    }

    public CategorySerializer(Class<Set<Category>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Category> categories, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        categories.forEach(category -> {
            try {
                jsonGenerator.writeString(category.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
    }
}