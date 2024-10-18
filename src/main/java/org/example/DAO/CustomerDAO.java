package org.example.DAO;

import org.example.model.Customer;
import org.example.model.Person;
import org.example.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {

    @Override
    public Customer get(int id) throws SQLException {
        String sql = "SELECT * FROM Customer c JOIN Person p ON c.PersonID = p.PersonID WHERE c.CustomerID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Person person = new Person(rs.getInt("PersonID"), rs.getString("Name"), rs.getString("LastName"));
                return new Customer(rs.getInt("CustomerID"), person, rs.getInt("AccountID"));
            }
        }
        return null;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer c JOIN Person p ON c.PersonID = p.PersonID";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Person person = new Person(rs.getInt("PersonID"), rs.getString("Name"), rs.getString("LastName"));
                Customer customer = new Customer(rs.getInt("CustomerID"), person, rs.getInt("AccountID"));
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public int save(Customer customer) throws SQLException {
        if (customer.getCustomerID() > 0) {
            return update(customer);
        } else {
            return insert(customer);
        }
    }

    @Override
    public int insert(Customer customer) throws SQLException {
        String sqlPerson = "INSERT INTO Person (Name, LastName) VALUES (?, ?)";
        String sqlCustomer = "INSERT INTO Customer (PersonID, AccountID) VALUES (?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement psPerson = connection.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psCustomer = connection.prepareStatement(sqlCustomer, Statement.RETURN_GENERATED_KEYS)) {

            // Insert Person
            psPerson.setString(1, customer.getPerson().getName());
            psPerson.setString(2, customer.getPerson().getLastName());
            psPerson.executeUpdate();
            ResultSet rsPerson = psPerson.getGeneratedKeys();
            if (rsPerson.next()) {
                customer.getPerson().setPersonID(rsPerson.getInt(1));
            }

            // Insert Customer
            psCustomer.setInt(1, customer.getPerson().getPersonID());
            psCustomer.setInt(2, customer.getAccountID());
            psCustomer.executeUpdate();
            ResultSet rsCustomer = psCustomer.getGeneratedKeys();
            if (rsCustomer.next()) {
                return rsCustomer.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int update(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET AccountID = ? WHERE CustomerID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customer.getAccountID());
            ps.setInt(2, customer.getCustomerID());
            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(Customer customer) throws SQLException {
        String sql = "DELETE FROM Customer WHERE CustomerID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerID());
            return ps.executeUpdate();
        }
    }
}
