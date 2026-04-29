package com.example.demo.operations.comand;

import com.example.demo.operations.ConsoleOperationType;
import com.example.demo.operations.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements OperationCommand {

    @Override
    public void execute() {
        System.out.println("Shutting down MiniBank...");
        // Выход обрабатывается в OperationsConsoleListener
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.EXIT;
    }
}