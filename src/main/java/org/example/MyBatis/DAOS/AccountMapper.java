package org.example.MyBatis.DAOS;

import org.apache.ibatis.annotations.*;
import org.example.model.Account;
import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM Accounts WHERE AccountID = #{accountID}")
    Account getAccountById(int accountID);

    @Select("SELECT * FROM Accounts")
    List<Account> getAllAccounts();

    @Insert("INSERT INTO Accounts (CustomerID, AccountNumber, Balance) VALUES (#{customerID}, #{accountNumber}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "accountID")
    void insertAccount(Account account);

    @Update("UPDATE Accounts SET CustomerID = #{customerID}, AccountNumber = #{accountNumber}, Balance = #{balance} WHERE AccountID = #{accountID}")
    void updateAccount(Account account);

    @Delete("DELETE FROM Accounts WHERE AccountID = #{accountID}")
    void deleteAccount(int accountID);
}
