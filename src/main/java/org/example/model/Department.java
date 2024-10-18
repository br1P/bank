package org.example.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Department")
@XmlType(propOrder = { "departmentID", "departmentName" })
public class Department {

    private int departmentID;
    private String departmentName;


    public Department(){};
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }



    public Department(int departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    @XmlElement(name="DepartmentID")
    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @XmlElement(name="DepartmentName")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentID +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
