package org.example.DAO;
import org.example.model.Department;
import org.example.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO implements DAO<Department> {

    @Override
    public Department get(int id) throws SQLException {
        String sql = "SELECT * FROM Department WHERE DepartmentID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Department(
                            rs.getInt("DepartmentID"),
                            rs.getString("DepartmentName")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Department";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                departments.add(new Department(
                        rs.getInt("DepartmentID"),
                        rs.getString("DepartmentName")
                ));
            }
        }
        return departments;
    }

    @Override
    public int save(Department department) throws SQLException {
        return insert(department);
    }

    @Override
    public int insert(Department department) throws SQLException {
        String sql = "INSERT INTO Department (DepartmentName) VALUES (?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, department.getDepartmentName());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated DepartmentID
                    }
                }
            }
        }
        return -1;  // Return -1 if insertion failed
    }

    @Override
    public int update(Department department) throws SQLException {
        String sql = "UPDATE Department SET DepartmentName = ? WHERE DepartmentID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, department.getDepartmentName());
            statement.setInt(2, department.getDepartmentID());

            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Department department) throws SQLException {
        String sql = "DELETE FROM Department WHERE DepartmentID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, department.getDepartmentID());
            return statement.executeUpdate();
        }
    }
}
