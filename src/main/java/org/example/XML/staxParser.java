package org.example.XML;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

public class staxParser {

    public void parseXML(String xmlFile) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlFile));

            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("starting element: " + reader.getLocalName());
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (reader.hasText()) {
                            System.out.println("text: " + reader.getText().trim());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("ending element: " + reader.getLocalName());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
