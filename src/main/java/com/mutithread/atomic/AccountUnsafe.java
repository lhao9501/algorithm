package com.mutithread.atomic;

public class AccountUnsafe implements Account {

    private Integer balance;

    public AccountUnsafe(Integer account) {
        this.balance = account;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
        balance -= amount;
    }

    public static void main(String[] args) {
        Account.demo(new AccountUnsafe(10000));
    }
}
