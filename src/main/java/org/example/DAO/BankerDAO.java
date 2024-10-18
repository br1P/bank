package org.example.DAO;

import org.example.model.Banker;
import org.example.model.Person;
import org.example.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankerDAO implements DAO<Banker> {

    @Override
    public Banker get(int id) throws SQLException {
        String sql = "SELECT b.BankerID, b.LicenseNumber, b.DepartmentID, b.Salary, p.PersonID, p.Name, p.LastName " +
                "FROM Banker b " +
                "JOIN Person p ON b.PersonID = p.PersonID " +
                "WHERE b.BankerID = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Person person = new Person(
                            rs.getInt("PersonID"),
                            rs.getString("Name"),
                            rs.getString("LastName")
                    );

                    return new Banker(
                            rs.getInt("BankerID"),
                            person,
                            rs.getString("LicenseNumber"),
                            rs.getInt("DepartmentID"),
                            rs.getDouble("Salary")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Banker> getAll() throws SQLException {
        List<Banker> bankers = new ArrayList<>();
        String sql = "SELECT b.BankerID, b.LicenseNumber, b.DepartmentID, b.Salary, p.PersonID, p.Name, p.LastName " +
                "FROM Banker b " +
                "JOIN Person p ON b.PersonID = p.PersonID";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Person person = new Person(
                        rs.getInt("PersonID"),
                        rs.getString("Name"),
                        rs.getString("LastName")
                );

                bankers.add(new Banker(
                        rs.getInt("BankerID"),
                        person,
                        rs.getString("LicenseNumber"),
                        rs.getInt("DepartmentID"),
                        rs.getDouble("Salary")
                ));
            }
        }
        return bankers;
    }

    @Override
    public int save(Banker banker) throws SQLException {
        return insert(banker);
    }

    @Override
    public int insert(Banker banker) throws SQLException {
        String insertPersonSQL = "INSERT INTO Person (Name, LastName) VALUES (?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {

            personStatement.setString(1, banker.getPerson().getName());
            personStatement.setString(2, banker.getPerson().getLastName());

            int affectedRows = personStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = personStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int personID = generatedKeys.getInt(1);

                        String insertBankerSQL = "INSERT INTO Banker (PersonID, LicenseNumber, DepartmentID, Salary) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement bankerStatement = connection.prepareStatement(insertBankerSQL, Statement.RETURN_GENERATED_KEYS)) {
                            bankerStatement.setInt(1, personID);
                            bankerStatement.setString(2, banker.getLicenseNumber());
                            bankerStatement.setInt(3, banker.getDepartmentID());
                            bankerStatement.setDouble(4, banker.getSalary());

                            int bankerAffectedRows = bankerStatement.executeUpdate();

                            if (bankerAffectedRows > 0) {
                                try (ResultSet generatedBankerKeys = bankerStatement.getGeneratedKeys()) {
                                    if (generatedBankerKeys.next()) {
                                        return generatedBankerKeys.getInt(1);  // Return the generated BankerID
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;  // Return -1 if insertion failed
    }

    @Override
    public int update(Banker banker) throws SQLException {
        String updatePersonSQL = "UPDATE Person SET Name = ?, LastName = ? WHERE PersonID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(updatePersonSQL)) {

            personStatement.setString(1, banker.getPerson().getName());
            personStatement.setString(2, banker.getPerson().getLastName());
            personStatement.setInt(3, banker.getPerson().getPersonID());

            int affectedRows = personStatement.executeUpdate();

            if (affectedRows > 0) {
                String updateBankerSQL = "UPDATE Banker SET LicenseNumber = ?, DepartmentID = ?, Salary = ? WHERE BankerID = ?";
                try (PreparedStatement bankerStatement = connection.prepareStatement(updateBankerSQL)) {
                    bankerStatement.setString(1, banker.getLicenseNumber());
                    bankerStatement.setInt(2, banker.getDepartmentID());
                    bankerStatement.setDouble(3, banker.getSalary());
                    bankerStatement.setInt(4, banker.getBankerID());

                    return bankerStatement.executeUpdate();
                }
            }
        }
        return -1;  // Return -1 if update failed
    }

    @Override
    public int delete(Banker banker) throws SQLException {
        String sql = "DELETE FROM Banker WHERE BankerID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, banker.getBankerID());
            return statement.executeUpdate();
        }
    }
}
