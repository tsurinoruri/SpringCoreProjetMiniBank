package com.example.demo.model;

import java.util.List;

public class User {
    private int id;
    private String login;
    private List<Account> accountList;

    public User(int id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public  int getId() {
        return this.id;
    }

    public String getLogin(){
        return this.login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        this.accountList.add(account);
    }

    public void removeAccount(Account account) {
        this.accountList.remove(account);
    }

    @Override
    public String toString() {
        return "User created: User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accounts=" + accountList +
                '}';
    }
}
