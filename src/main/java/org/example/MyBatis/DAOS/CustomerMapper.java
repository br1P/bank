package org.example.MyBatis.DAOS;

import org.apache.ibatis.annotations.*;
import org.example.model.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("SELECT * FROM Customer c " +
            "JOIN Person p ON c.PersonID = p.PersonID " +
            "WHERE CustomerID = #{customerID}")
    @Results({
            @Result(property = "customerID", column = "CustomerID"),
            @Result(property = "person.personID", column = "PersonID"),
            @Result(property = "person.name", column = "Name"),
            @Result(property = "person.lastName", column = "LastName"),
            @Result(property = "accountID", column = "AccountID")
    })
    Customer get(int customerID);

    @Select("SELECT * FROM Customer c " +
            "JOIN Person p ON c.PersonID = p.PersonID")
    @Results({
            @Result(property = "customerID", column = "CustomerID"),
            @Result(property = "person.personID", column = "PersonID"),
            @Result(property = "person.name", column = "Name"),
            @Result(property = "person.lastName", column = "LastName"),
            @Result(property = "accountID", column = "AccountID")
    })
    List<Customer> getAll();

    @Insert("INSERT INTO Customer (PersonID, AccountID) " +
            "VALUES (#{person.personID}, #{accountID})")
    @Options(useGeneratedKeys = true, keyProperty = "customerID")
    void insert(Customer customer);

    @Update("UPDATE Customer SET AccountID = #{accountID}, PersonID = #{person.personID} " +
            "WHERE CustomerID = #{customerID}")
    void update(Customer customer);

    @Delete("DELETE FROM Customer WHERE CustomerID = #{customerID}")
    void delete(int customerID);
}
