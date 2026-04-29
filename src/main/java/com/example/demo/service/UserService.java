package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {
    private final AccountService accountService;
    private Map<Integer, User> users;
    private Set<String> takenLoggins;
    private int idCounter;

    @Autowired
    public UserService(AccountService accountService){
        this.accountService = accountService;
        this.idCounter = 0;
        this.takenLoggins = new HashSet<>();
        this.users = new HashMap<>();
    }

    public User createUser(String login){
        String normalizelogin = validateLogin(login);
        if(takenLoggins.contains(login)){
            throw new IllegalArgumentException("User already exist with this login: " + login);
        }
        idCounter++;
        var user = new User(idCounter, login, new ArrayList<>());
        var defaultAccount = accountService.createAccount(user);
        user.getAccountList().add(defaultAccount);

        users.put(idCounter, user);
        takenLoggins.add(normalizelogin);
        return user;
    }

    public User findUserById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("user id must be > 0");
        }
        var user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException("No such user with id=%s".formatted(id));
        }
        return user;
    }

    public List<User> findAll() {
        return users.values().stream().toList();
    }

    private String validateLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("login must not be blank");
        }
        return login.trim();
    }
}