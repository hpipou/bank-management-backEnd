package io.bank.management.service;

import io.bank.management.entity.Account;
import io.bank.management.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransferMoneyServiceImpl implements TransferMoneyService {

    private AccountRepository accountRepository;

    public TransferMoneyServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void transferMoney(Long idSender, Double money, Long idSendTo) {

        Account accountIdSender=accountRepository.findByOwner(idSender);
        Double newBalanceIdSender=accountIdSender.getBalance()-money;
        accountIdSender.setBalance(newBalanceIdSender);
        accountRepository.save(accountIdSender);

        Account accountIdSendTo=accountRepository.findByOwner(idSendTo);
        Double newBalableIdSendTo=accountIdSendTo.getBalance()+money;
        accountIdSendTo.setBalance(newBalableIdSendTo);
        accountRepository.save(accountIdSendTo);
    }
}
