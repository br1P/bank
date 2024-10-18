package org.example.JAXB;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.model.Person;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Persons")
public class PersonList {
    private List<Person> persons;

    public PersonList() {
        persons = new ArrayList<>();
    }


    @XmlElement(name = "Person")
    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }
}
