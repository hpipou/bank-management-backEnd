package io.bank.management.service;

import io.bank.management.entity.Account;
import io.bank.management.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account addNewAccount(Account account) {
        account.setDateTime(new Date());
        return accountRepository.save(account);
    }

    @Override
    public Account editAccount(Account account) {
        Account account1=accountRepository.findByOwner(account.getOwner());
        account1.setBalance(account.getBalance());
        account1.setType(account.getType());
        return accountRepository.save(account1);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account= accountRepository.findByOwner(id);
        accountRepository.deleteById(account.getId());
    }

    @Override
    public Account showOneAccount(Long id) {
        return accountRepository.findByOwner(id);
    }

    @Override
    public List<Account> showAllAccountes() {
        return accountRepository.findAll();
    }
}
