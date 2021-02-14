package com.mutithread.atomic;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class DecimalAccountSafe implements DecimalAccount {
    AtomicReference<BigDecimal> balance;

    public DecimalAccountSafe(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountSafe(new BigDecimal("10000")));
    }
}
