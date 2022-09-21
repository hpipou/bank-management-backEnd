package io.bank.management.service;

public interface TransferMoneyService {
    void transferMoney(Long idSender, Double money, Long idSendTo);
}
