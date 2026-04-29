package com.example.demo.operations;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
