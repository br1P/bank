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

    // Método para serializar y agregar al archivo JSON
    public void serialize(T newObject, String pathJson) {
        File file = new File(pathJson);
        List<T> objects = new ArrayList<>();

        // Leer el archivo existente si existe
        if (file.exists() && file.length() > 0) {
            try {
                objects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            }
        }

        // Agregar el nuevo objeto
        objects.add(newObject);

        // Escribir la lista de objetos de nuevo al archivo
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, objects);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }

    // Método para deserializar el archivo JSON
    public List<T> deserialize(String pathJson) {
        File file = new File(pathJson);
        List<T> objects = new ArrayList<>();

        // Leer y deserializar el archivo
        if (file.exists() && file.length() > 0) {
            try {
                // Cambiamos esta línea para asegurarnos de que estamos deserializando a la lista del tipo correcto
                objects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            }
        }

        return objects;
    }
}
