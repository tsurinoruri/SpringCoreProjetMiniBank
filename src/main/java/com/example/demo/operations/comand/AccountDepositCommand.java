package com.example.demo.operations.comand;

import com.example.demo.console.ConsoleListener;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final ConsoleListener console;
    private final AccountService accountService;

    public AccountDepositCommand(ConsoleListener console, AccountService accountService) {
        this.console = console;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        int accountId = console.readPositiveInt("Enter account ID:", "Account ID");
        int amount = console.readPositiveInt("Enter amount to deposit:", "Amount");

        try {
            accountService.deposit(accountId, amount);
            System.out.println("✓ Deposit successful!");
            var account = accountService.findAccountById(accountId).orElse(null);
            if (account != null) {
                System.out.println("  New balance: " + account.getMoneyAmount());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}