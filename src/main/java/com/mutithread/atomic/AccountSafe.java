package com.mutithread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountSafe implements Account {
    // 余额  原子类
    private AtomicInteger balance;

    public AccountSafe(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        /*while (true) {
            int prev = balance.get();
            int next = prev - amount;
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }*/

        balance.addAndGet(-1 * amount);
    }

    public static void main(String[] args) {
//        Account.demo(new AccountSafe(10000));
        AccountSafe a = new AccountSafe(100);
        a.acc();
    }

    public void acc() {
        int x = 10;
//        System.out.println(balance.getAndAccumulate(x, (p, i) -> p + i));
        System.out.println(balance.accumulateAndGet(x, (p, i) -> p + i));
        System.out.println(balance);
    }
}
