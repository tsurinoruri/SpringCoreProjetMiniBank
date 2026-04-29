package com.example.demo.operations.comand;

import com.example.demo.console.ConsoleListener;
import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserCreateCommand implements OperationCommand {
    private final ConsoleListener console;
    private final UserService userService;

    public UserCreateCommand(ConsoleListener console, UserService userService) {
        this.console = console;
        this.userService = userService;
    }

    @Override
    public void execute() {
        String login = console.readRequiredString("Enter login:", "Login");
        try {
            userService.createUser(login);
            System.out.println("✓ User created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}