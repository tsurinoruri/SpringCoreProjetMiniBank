package com.example.demo.operations.comand;

import com.example.demo.console.ConsoleListener;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final ConsoleListener console;
    private final AccountService accountService;

    public AccountCloseCommand(ConsoleListener console, AccountService accountService) {
        this.console = console;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        int accountId = console.readPositiveInt("Enter account ID to close:", "Account ID");

        try {
            var closedAccount = accountService.closeAccount(accountId);
            System.out.println("✓ Account #" + closedAccount.getId() + " closed successfully!");
            System.out.println("  Remaining balance (" + closedAccount.getMoneyAmount() +
                    ") was transferred to another account");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}