package org.example.JAXB;



import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class UnmarshallerUtil {

    public static <T> T unmarshal(File file, Class<T> clazz) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(clazz);


        Unmarshaller unmarshaller = context.createUnmarshaller();


        T object = (T) unmarshaller.unmarshal(file);
        System.out.println("XML deserialized: " + file.getAbsolutePath());
        return object;
    }
}
