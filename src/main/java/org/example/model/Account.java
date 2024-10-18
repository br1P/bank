package org.example.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Account")
@XmlType(propOrder = { "accountID", "customerID", "accountNumber","balance" })
public class Account {

    private int accountID;
    private int customerID;
    private String accountNumber;
    private double balance;

    public Account() {
    };

    public Account(int accountID, int customerID, String accountNumber, double balance) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }


    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @XmlElement(name="AccountID")
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @XmlElement(name="CustomerID")
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @XmlElement(name="AccountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @XmlElement(name="Balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", customerID=" + customerID +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
