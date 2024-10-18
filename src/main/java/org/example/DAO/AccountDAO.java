package org.example.DAO;

import org.example.model.Account;
import org.example.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account> {

    @Override
    public Account get(int id) throws SQLException {
        String sql = "SELECT * FROM Accounts WHERE AccountID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Account(rs.getInt("AccountID"), rs.getInt("CustomerID"),
                        rs.getString("AccountNumber"), rs.getDouble("Balance"));
            }
        }
        return null;
    }

    @Override
    public List<Account> getAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Account account = new Account(rs.getInt("AccountID"), rs.getInt("CustomerID"),
                        rs.getString("AccountNumber"), rs.getDouble("Balance"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    @Override
    public int save(Account account) throws SQLException {
        if (account.getAccountID() > 0) {
            return update(account);
        } else {
            return insert(account);
        }
    }

    @Override
    public int insert(Account account) throws SQLException {
        String sql = "INSERT INTO Accounts (CustomerID, AccountNumber, Balance) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, account.getCustomerID());
            ps.setString(2, account.getAccountNumber());
            ps.setDouble(3, account.getBalance());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int update(Account account) throws SQLException {
        String sql = "UPDATE Accounts SET Balance = ? WHERE AccountID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getAccountID());
            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(Account account) throws SQLException {
        String sql = "DELETE FROM Accounts WHERE AccountID = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, account.getAccountID());
            return ps.executeUpdate();
        }
    }
}
