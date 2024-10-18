package org.example.DAO;

import org.example.model.Person;
import org.example.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DAO<Person> {

    @Override
    public Person get(int id) throws SQLException {
        String query = "SELECT * FROM Person WHERE PersonID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("PersonID"),
                            rs.getString("Name"),
                            rs.getString("LastName")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Person> getAll() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM Person";
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt("PersonID"),
                        rs.getString("Name"),
                        rs.getString("LastName")
                ));
            }
        }
        return persons;
    }

    @Override
    public int save(Person person) throws SQLException {
        return insert(person);
    }

    @Override
    public int insert(Person person) throws SQLException {
        String query = "INSERT INTO Person (Name, LastName) VALUES (?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getLastName());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int update(Person person) throws SQLException {
        String query = "UPDATE Person SET Name = ?, LastName = ? WHERE PersonID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getLastName());
            stmt.setInt(3, person.getPersonID());
            return stmt.executeUpdate();
        }
    }

    @Override
    public int delete(Person person) throws SQLException {
        String query = "DELETE FROM Person WHERE PersonID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getPersonID());
            return stmt.executeUpdate();
        }
    }
}
