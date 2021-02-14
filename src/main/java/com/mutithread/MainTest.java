package com.mutithread;


import com.mutithread.atomic.UnsafeAccessor;
import sun.misc.Unsafe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainTest {
    static Teacher t = new Teacher();

    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
        /*for (int i = 0; i < 3; i++) {
            new com.othertest.Person().start();
        }

        for (int i = 1; i <= 3; i++) {
            new Postman(i, "信息内容" + i).start();
        }*/
        Unsafe UNSAFE = UnsafeAccessor.getInstance();
        long nameOffset = UNSAFE.objectFieldOffset(Teacher.class.getDeclaredField("name"));
        long ageOffset = UNSAFE.objectFieldOffset(Teacher.class.getDeclaredField("age"));

        new Thread(() -> {
            while (true) {
                if (UNSAFE.compareAndSwapInt(t, ageOffset, 0, 33)) {
                    break;
                }
            }
            System.out.println("t1: " + t);
        }, "t1").start();

//        Thread.sleep(500);
        new Thread(() -> {
            while (true) {
                if (UNSAFE.compareAndSwapObject(t, nameOffset, null, "王五")) {
                    break;
                }
            }
            System.out.println("t2: " + t);
        }, "t2").start();

        Thread.sleep(1000);

        System.out.println(UNSAFE.compareAndSwapInt(t, ageOffset, 0, 22));
        System.out.println(UNSAFE.compareAndSwapObject(t, nameOffset, null, "张三"));
        System.out.println(t);

        System.out.println("----------------------------");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate localDate = dateTimeFormatter.parse("2021-01-27", LocalDate::from);
                System.out.println(localDate);
            }).start();
        }
    }
}

/**
 * 收信息
 */
class Person extends Thread {
    @Override
    public void run() {
        GuardedObject go = MailBox.createGuardedObject();
        System.out.println("开始收信：" + go.getId());
        Object mail = go.get(3000);
        System.out.println("收到的信 id：" + go.getId() + "; 内容：" + mail);
    }
}

/**
 * 发信息
 */
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBox.getGuardedObject(id);
        System.out.println("送信的id：" + id + "; 信的内容：" + mail);
        guardedObject.complete(mail);
    }
}

class Teacher {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}