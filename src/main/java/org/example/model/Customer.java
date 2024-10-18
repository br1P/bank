package org.example.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Customer")
@XmlType(propOrder = { "customerID", "person", "accountID"})
public class Customer {

    private int customerID;
    private Person person;
    private int accountID;


    public Customer(){};
    public Customer(Person person, int accountID) {
        this.person = person;
        this.accountID = accountID;
    }

    public Customer(int customerID, Person person, int accountID) {
        this.customerID = customerID;
        this.person = person;
        this.accountID = accountID;
    }

    @XmlElement(name="CustomerID")
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @XmlElement(name="PersonID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlElement(name="AccountID")
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", person=" + person +
                ", accountID=" + accountID +
                '}';
    }
}
