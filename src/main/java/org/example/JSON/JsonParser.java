package org.example.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser<T> {

    private Class<T> type;
    private ObjectMapper objectMapper;

    public JsonParser(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }


    public void serialize(T newObject, String pathJson) {
        File file = new File(pathJson);
        List<T> objects = new ArrayList<>();

        if (file.exists() && file.length() > 0) {
            try {
                objects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
            } catch (IOException e) {
                System.err.println("Error reading JSON: " + e.getMessage());
            }
        }

        objects.add(newObject);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, objects);
        } catch (IOException e) {
            System.err.println("Error writing file JSON: " + e.getMessage());
        }
    }

    public List<T> deserialize(String pathJson) {
        File file = new File(pathJson);
        List<T> objects = new ArrayList<>();

        if (file.exists() && file.length() > 0) {
            try {
                objects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            }
        }

        return objects;
    }
}
