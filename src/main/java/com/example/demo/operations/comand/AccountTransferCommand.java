package com.example.demo.operations.comand;

import com.example.demo.console.ConsoleListener;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final ConsoleListener console;
    private final AccountService accountService;

    public AccountTransferCommand(ConsoleListener console, AccountService accountService) {
        this.console = console;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        int fromAccountId = console.readPositiveInt("Enter source account ID:", "Source Account ID");
        int toAccountId = console.readPositiveInt("Enter target account ID:", "Target Account ID");
        int amount = console.readPositiveInt("Enter amount to transfer:", "Amount");

        try {
            accountService.transfer(fromAccountId, toAccountId, amount);
            System.out.println("✓ Transfer successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}