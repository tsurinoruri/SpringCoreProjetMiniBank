package com.example.demo.operations.comand;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        var users = userService.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found");
            return;
        }

        System.out.println("\n=== USERS ===");
        for (User user : users) {
            System.out.println("User ID: " + user.getId() + ", Login: " + user.getLogin());
            System.out.println("  Accounts:");
            if (user.getAccountList().isEmpty()) {
                System.out.println("    No accounts");
            } else {
                for (Account account : user.getAccountList()) {
                    System.out.println("    Account #" + account.getId() +
                            ", Balance: " + account.getMoneyAmount());
                }
            }
        }
        System.out.println("=============\n");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}