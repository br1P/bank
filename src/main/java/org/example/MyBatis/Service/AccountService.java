package org.example.MyBatis.Service;

import org.example.MyBatis.DAOS.AccountMapper;  // Asegúrate de que la importación sea correcta
import org.example.model.Account;

import java.util.List;

public class AccountService {

    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public Account getAccountById(int id) {
        return accountMapper.getAccountById(id);
    }

    public List<Account> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }

    public void createAccount(Account account) {
        accountMapper.insertAccount(account);
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
    }

    public void deleteAccount(int accountID) {
        accountMapper.deleteAccount(accountID);
    }
}
