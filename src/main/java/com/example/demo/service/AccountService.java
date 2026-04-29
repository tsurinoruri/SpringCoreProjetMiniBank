package com.example.demo.service;


import com.example.demo.config.AccountProperties;
import com.example.demo.model.Account;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountService {
    private Map<Integer, Account> accounts;
    private int accountIdCounter;
    private final AccountProperties accountProperties;

    @Autowired
    public AccountService(AccountProperties accountProperies){
        this.accountProperties = accountProperies;
        this.accounts = new HashMap<>();
        this.accountIdCounter = 0;
    }

    public Account createAccount(User user){
        if(user == null){
            throw new IllegalArgumentException("User must not be null");
        }
        accountIdCounter++;
        Account newAccount = new Account(accountIdCounter, user.getId(), accountProperties.getDefaultAmount());
        accounts.put(accountIdCounter, newAccount);
        return newAccount;
    }

    public Optional<Account> findAccountById(Integer id) {
        validatePositiveId(id, "account id");
        return Optional.ofNullable(accounts.get(id));
    }

    public List<Account> getUserAccounts(Integer userId) {
        return accounts.values().stream()
                .filter(it -> userId.equals(it.getUserId()))
                .toList();
    }

    public void withdraw(Integer fromAccountId, Integer amount) {
        validatePositiveId(fromAccountId, "account id");
        validatePositiveAmount(amount);
        Account account = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(fromAccountId)));

        if (amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "insufficient funds on account id=%s, moneyAmount=%s, attempted withdraw=%s"
                            .formatted(account.getId(), account.getMoneyAmount(), amount)
            );
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    public void deposit(Integer toAccountId, Integer amount) {
        validatePositiveId(toAccountId, "account id");
        validatePositiveAmount(amount);
        Account account = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(toAccountId)));

        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public Account closeAccount(Integer accountId) {
        validatePositiveId(accountId, "account id");
        Account accountToClose = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
        var userId = accountToClose.getUserId();
        var userAccounts = getUserAccounts(userId);
        if (userAccounts.size() == 1) {
            throw new IllegalStateException("Can't close the only one account");
        }
        accounts.remove(accountId);

        var accountToTransferMoney = userAccounts.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();

        var newAmount = accountToTransferMoney.getMoneyAmount() + accountToClose.getMoneyAmount();
        accountToTransferMoney.setMoneyAmount(newAmount);
        return accountToClose;
    }

    public void transfer(int fromAccountId, int toAccountId, int amount) {
        validatePositiveId(fromAccountId, "source account id");
        validatePositiveId(toAccountId, "target account id");
        validatePositiveAmount(amount);
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("source and target account id must be different");
        }
        Account accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(fromAccountId)));
        Account accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(toAccountId)));

        if (amount > accountFrom.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "insufficient funds on account id=%s, moneyAmount=%s, attempted transfer=%s"
                            .formatted(accountFrom.getId(), accountFrom.getMoneyAmount(), amount)
            );
        }
        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amount);

        int amountToTransfer = accountTo.getUserId() == accountFrom.getUserId()
                ? amount
                : (int) Math.round(amount * (1 - accountProperties.getDefaultCommission()));
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amountToTransfer);
    }

    private void validatePositiveId(Integer id, String fieldName) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(fieldName + " must be > 0");
        }
    }

    private void validatePositiveAmount(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }
}