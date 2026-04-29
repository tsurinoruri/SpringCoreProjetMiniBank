package com.example.demo;

import com.example.demo.operations.OperationsConsoleListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.demo")) {
			OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
			consoleListener.runBank();

		}
	}
}
