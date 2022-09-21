package io.bank.management.service;

import io.bank.management.entity.Account;

import java.util.List;

public interface AccountService {

    Account addNewAccount(Account account);
    Account editAccount(Account account);
    void deleteAccount(Long id);
    Account showOneAccount(Long id);
    List<Account> showAllAccountes();

}
