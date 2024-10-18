package org.example.JAXB;

import org.example.model.Person;


import jakarta.xml.bind.JAXBException;
import java.io.File;

public class TesterJAXB {

    public static void main(String[] args) {
        try {

            File xmlFile = new File("C:/Users/bruno/Desktop/Bank2/src/main/java/org/example/XML/person.xml");// Route of xml here

            PersonList personList = new PersonList(); // create object



            if (xmlFile.exists()) {

                personList = UnmarshallerUtil.unmarshal(xmlFile, PersonList.class);
            } else {

                System.out.println("File not found creating a new one.");
            }


            Person newPerson = new Person(199, "Ann", "Martin");
            personList.addPerson(newPerson);


            MarshallerUtil.marshal(personList, xmlFile, PersonList.class);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
