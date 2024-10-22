package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "Banker")
public class Banker {

    @JsonProperty("bankerID")
    private int bankerID;

    @JsonProperty("person")
    private Person person; // Complex object

    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @JsonProperty("departmentID")
    private int departmentID;

    @JsonProperty("salary")
    private double salary;

    public Banker() {}

    public Banker(int bankerID, Person person, String licenseNumber, int departmentID, double salary) {
        this.bankerID = bankerID;
        this.person = person;
        this.licenseNumber = licenseNumber;
        this.departmentID = departmentID;
        this.salary = salary;
    }

    public Banker(Person person, String licenseNumber, int departmentID, double salary) {
        this.person = person;
        this.licenseNumber = licenseNumber;
        this.departmentID = departmentID;
        this.salary = salary;
    }

    // @XmlElement(name="BankerID")
    public int getBankerID() {
        return bankerID;
    }

    public void setBankerID(int bankerID) {
        this.bankerID = bankerID;
    }
   // @XmlElement(name="PersonID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
   // @XmlElement(name="LicenseNumber")
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
   // @XmlElement(name="DepartmentID")
    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
  //  @XmlElement(name="Salary")
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Banker{" +
                "bankerID=" + bankerID +
                ", person=" + person +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", departmentID=" + departmentID +
                ", salary=" + salary +
                '}';
    }
}
