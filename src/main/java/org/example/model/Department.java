package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department {

    @JsonProperty("DepartmentID")
    private int departmentID;

    @JsonProperty("DepartmentName")
    private String departmentName;

    public Department() {}

    public Department(int departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }
   // @XmlElement(name="DepartmentID")
    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    //@XmlElement(name="DepartmentName")
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
