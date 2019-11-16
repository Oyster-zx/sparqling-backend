package org.cz.cvut.fel.kuzevigo.sparqlingbackend.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.cz.cvut.fel.kuzevigo.sparqlingbackend.model.Category;

import java.io.IOException;
import java.util.List;


public class CategorySerializer extends StdSerializer<List<Category>> {

    public CategorySerializer() {
        this(null);
    }

    public CategorySerializer(Class<List<Category>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Category> categories, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
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