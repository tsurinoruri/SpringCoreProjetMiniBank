package com.example.demo.console;

import com.example.demo.operations.ConsoleOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsoleListener {
    private final Scanner scanner;

    @Autowired
    public ConsoleListener(Scanner scanner){
        this.scanner = scanner;
    }

    public ConsoleOperationType readOperationType() {
        while (true) {
            System.out.println("Enter command:");
            String value = scanner.nextLine().trim();

            if (value.isBlank()) {
                System.out.println("Error: command must not be blank");
                printAvailableCommands();
                continue;
            }

            try {
                // Преобразуем строку в enum
                return ConsoleOperationType.valueOf(value.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: unknown command");
                printAvailableCommands();
            }
        }
    }

    public void printAvailableCommands() {
        String availableCommands = Arrays.stream(ConsoleOperationType.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        System.out.println("Available commands: " + availableCommands);
    }

    public String readRequiredString(String prompt, String fieldName) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }
            System.out.println("Error: " + fieldName + " must not be blank");
        }
    }

    public int readPositiveInt(String prompt, String fieldName) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine().trim();

            if (value.isBlank()) {
                System.out.println("Error: " + fieldName + " must not be blank");
                continue;
            }

            try {
                int parsed = Integer.parseInt(value);
                if (parsed <= 0) {
                    System.out.println("Error: " + fieldName + " must be > 0");
                    continue;
                }
                return parsed;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + fieldName + " must be a number");
            }
        }
    }


    public void close() {

        scanner.close();
    }
}