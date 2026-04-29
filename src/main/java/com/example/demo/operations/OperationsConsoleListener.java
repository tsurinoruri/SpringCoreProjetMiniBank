package com.example.demo.operations;

import com.example.demo.console.ConsoleListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final ConsoleListener consoleInput;
    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private boolean running;

    public OperationsConsoleListener(
            List<OperationCommand> operationCommandList,
            ConsoleListener consoleInput
    ) {
        // ДОБАВЬТЕ ЭТУ ДИАГНОСТИКУ:
        System.out.println("=== ЗАГРУЗКА КОМАНД ===");
        System.out.println("Найдено команд в списке: " + operationCommandList.size());
        for (OperationCommand cmd : operationCommandList) {
            System.out.println("  - " + cmd.getOperationType() + " -> " + cmd.getClass().getSimpleName());
        }
        System.out.println("======================");

        this.commandMap = operationCommandList.stream()
                .collect(Collectors.toMap(OperationCommand::getOperationType, it -> it));
        this.consoleInput = consoleInput;
        this.running = true;
    }


    private void init() {
        System.out.println("MiniBank started. Type EXIT to stop.");
        consoleInput.printAvailableCommands();
    }

    private void process() {
        while (running) {
            var nextOperation = consoleInput.readOperationType();
            processNextCommand(nextOperation);
            if (nextOperation == ConsoleOperationType.EXIT) {
                running = false;
            }
        }
    }

    public void runBank() {
        init();
        process();
    }

    private void processNextCommand(ConsoleOperationType operationType) {
        try {
            OperationCommand command = commandMap.get(operationType);
            if (command == null) {
                throw new IllegalStateException("No command handler for " + operationType);
            }
            command.execute();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            System.out.println("Error: " + message);
        }
    }

}
