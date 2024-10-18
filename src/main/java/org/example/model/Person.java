package org.example.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="Person")
@XmlType(propOrder = { "personID", "name", "lastName" })
public class Person {

    private int personID;
    private String name;
    private String lastName;


    public Person() {}

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Person(int personID, String name, String lastName) {
        this.personID = personID;
        this.name = name;
        this.lastName = lastName;
    }


    @XmlElement(name="PersonID")
    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    @XmlElement(name="Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
