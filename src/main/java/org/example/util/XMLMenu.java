package org.example.util;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class XMLMenu {


    public static void validateXML(String xmlFilePath, String xsdFilePath) throws Exception {
        File xmlFile = new File(xmlFilePath);
        File xsdFile = new File(xsdFilePath);


        if (!xmlFile.exists()) {
            System.out.println("File doesnt exist: " + xmlFilePath);
            return;
        }

        if (!xsdFile.exists()) {
            System.out.println("File doesnt exist: " + xsdFilePath);
            return;
        }


        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile);
        Validator validator = schema.newValidator();

        validator.validate(new javax.xml.transform.stream.StreamSource(xmlFile));
        System.out.println("XML has been validated");
    }


    public static void parseXMLWithStAX(String xmlFilePath) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(xmlFilePath);
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        while (reader.hasNext()) {
            int eventType = reader.next();

            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                System.out.println("Element: " + elementName);

                for (int i = 0; i < reader.getAttributeCount(); i++) {
                    System.out.println("Atribute: " + reader.getAttributeLocalName(i) + " = " + reader.getAttributeValue(i));
                }
            }
        }
    }


    public static void showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);


        String basePathXML = "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "example" + File.separator + "XML";
        String basePathXSD = "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "example" + File.separator + "XSD";

        while (true) {
            System.out.println(" --- XML Menu ---");
            System.out.println("Select XML:");
            System.out.println("1. Person");
            System.out.println("2. Banker");
            System.out.println("3. Customer");
            System.out.println("4. Department");
            System.out.println("5. Account");
            System.out.println("6. Exit");

            int fileOption = scanner.nextInt();
            scanner.nextLine();

            String xmlFile = "";
            String xsdFile = "";
            String menuChoice = "";


            switch (fileOption) {
                case 1:
                    xmlFile = basePathXML + File.separator + "person.xml";
                    xsdFile = basePathXSD + File.separator + "person.xsd";
                    break;
                case 2:
                    xmlFile = basePathXML + File.separator + "banker.xml";
                    xsdFile = basePathXSD + File.separator + "banker.xsd";
                    break;
                case 3:
                    xmlFile = basePathXML + File.separator + "customer.xml";
                    xsdFile = basePathXSD + File.separator + "customer.xsd";
                    break;
                case 4:
                    xmlFile = basePathXML + File.separator + "department.xml";
                    xsdFile = basePathXSD + File.separator + "department.xsd";
                    break;
                case 5:
                    xmlFile = basePathXML + File.separator + "account.xml";
                    xsdFile = basePathXSD + File.separator + "account.xsd";
                    break;
                case 6:
                    System.out.println("Going back to main menu...");
                    return;
                default:
                    System.out.println("whoops, not valid option");
                    continue;
            }


            while (true) {
                System.out.println("\n --- Options with " + xmlFile + " ---");
                System.out.println("1. Validate XML");
                System.out.println("2. Parse XML");
                System.out.println("3. Go back");

                menuChoice = scanner.nextLine();
                switch (menuChoice) {
                    case "1":
                        validateXML(xmlFile, xsdFile);
                        break;
                    case "2":
                        parseXMLWithStAX(xmlFile);
                        break;
                    case "3":
                        return;
                    default:
                        System.out.println("Whoops, try again.");
                }
            }
        }
    }
}
