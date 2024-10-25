package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    @JsonProperty("CustomerID")
    private int customerID;

    @JsonProperty("Person")
    private Person person; // Complex object

    @JsonProperty("AccountID")
    private int accountID;

    public Customer() {}

    public Customer(int customerID, Person person, int accountID) {
        this.customerID = customerID;
        this.person = person;
        this.accountID = accountID;
    }

    public Customer(Person person, int accountID) {
        this.person = person;
        this.accountID = accountID;
    }

    //@XmlElement(name="CustomerID")
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

   // @XmlElement(name="PersonID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //@XmlElement(name="AccountID")
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
