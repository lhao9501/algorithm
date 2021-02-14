package com.othertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Generate {

    // 常用于取
    public void print(List<? extends Number> list) {
        Number n = list.get(0);   // 父类引用指向子类对象
        Object o = list.get(0);
    }

    // 常用于存
    public void print2(List<? super Integer> list) {
        list.add(new Integer(1));    // 父类引用指向子类对象
        new Object();
    }

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("john", 23));
        list.add(new Person("mary", 22));
        list.add(new Person("honery", 25));
        list.add(new Person("hello", 21));
        list.add(new Person("hello", 21));
        list.add(new Person("world", 21));

        List<String> personNames = list.stream().filter(person -> person.getAge() > 20)
                .sorted((p1, p2) -> String.valueOf(p1.getAge()).compareTo(String.valueOf(p2.getAge())))
                .map(Person:: getName).distinct()
                .collect(toList());
        System.out.println(Arrays.toString(personNames.toArray()));

        Map<Integer, List<Person>> map = list.stream().collect(groupingBy(Person::getAge));
        System.out.println(map);
        map.forEach((k, v) -> {

        });


        List<Person> list2 = new ArrayList<>();
        list2.add(new Person("ww", 24));
        list2.add(new Person("qq", 24));

        Stream<Person> stream = Stream.of(list, list2).flatMap(List::stream/*(children) -> children.stream()*/);
        System.out.println(Arrays.toString(stream.map(Person::getName).toArray()));



        List<String> personNames2 = Arrays.stream(new Person[] {new Person("zs", 1), new Person("ls", 2)})
                .filter(person -> person.getAge() >= 2)
                .map(Person::getName)
                .collect(toList());

        System.out.println(Arrays.toString(personNames2.toArray()));

        Map<Integer, String> personNames3 = Arrays.stream(new Person[] {new Person("zs", 1), new Person("ls", 2)})
                .filter(person -> person.getAge() >= 1)
//                .map(com.othertest.Person::getName)
                .collect(toMap((p1) -> Integer.valueOf(p1.getAge()), (p) -> p.toString()));

//        Map.Entry<Integer, String> entry = (Map.Entry) personNames3.entrySet();
        for (Map.Entry entry1 : personNames3.entrySet()) {
            System.out.println(entry1.getKey());
            System.out.println(entry1.getValue());
        }

        System.out.println(personNames3);
    }
}
