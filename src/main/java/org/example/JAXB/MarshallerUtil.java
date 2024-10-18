package org.example.JAXB;



import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;

public class MarshallerUtil {

    public static void marshal(Object object, File file, Class<?>... classes) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classes);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(object, file);
        System.out.println("XML generated in: " + file.getAbsolutePath());
    }
}
