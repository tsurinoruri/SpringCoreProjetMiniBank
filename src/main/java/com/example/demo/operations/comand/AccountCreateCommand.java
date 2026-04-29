package com.example.demo.operations.comand;

import com.example.demo.console.ConsoleListener;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AccountCreateCommand implements OperationCommand {
    private final ConsoleListener console;
    private final UserService userService;
    private final AccountService accountService;

    public AccountCreateCommand(ConsoleListener console, UserService userService, AccountService accountService) {
        this.console = console;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        int userId = console.readPositiveInt("Enter user ID:", "User ID");
        try {
            var user = userService.findUserById(userId);
            var newAccount = accountService.createAccount(user);
            System.out.println("✓ Account #" + newAccount.getId() + " created with balance " + newAccount.getMoneyAmount());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}