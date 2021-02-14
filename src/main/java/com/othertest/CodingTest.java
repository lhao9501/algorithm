package com.othertest;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;  // 静态导入  直接导入使用的方法

public class CodingTest {
    public static void main(String[] args) {

        //  [1, 2, 3]   [4, 5]  ===>   [(1, 4), (1, 5), (2, 4), (2, 5), (3, 4), (3, 5)]
        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(4, 5);

        List<String> number3 = Arrays.asList("10", "5", "8");

        List<int[]> pairs = number1.stream().flatMap(i -> number2.stream().map(j -> new int[]{i, j})).collect(toList());
//        pairs.forEach(x -> Arrays.stream(x).forEach(y -> System.out.println(y)));

        Optional<Integer> optional = number1.stream().map(x -> x * x).filter(x -> x % 3 == 0).findFirst();  // 有顺序的
//        optional.ifPresent(x -> System.out.println(x));  // 9
//        System.out.println(number1.stream().reduce(0, (a, b) -> a + b));  // 6 求和  BinaryOperator
//        System.out.println(number1.stream().reduce(0, Integer::sum));  // 6 求和
        System.out.println();
//        number3.stream().map(Integer::parseInt).reduce(Integer::max).ifPresent(System.out::println);   // max

        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH) );

//        menu.stream().sorted(Comparator.comparing(com.othertest.Dish::getCalories).reversed()).map(com.othertest.Dish::getName).forEach(System.out::println);

        menu.stream().mapToInt(Dish::getCalories).sorted().forEach(System.out::println);  // 数值流 避免了装箱 拆箱操作
        IntSummaryStatistics intSummaryStatistics = menu.stream().mapToInt(Dish::getCalories).summaryStatistics();

        if (menu.stream().anyMatch(Dish::isVegetarian)) {   // anyMatch  只要存在一个就可以
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        Optional<Dish> op = menu.stream().filter(Dish::isVegetarian).findAny();  // 不在乎顺序
        if (op.isPresent()) {
            System.out.println(op.get());
        }
        op.ifPresent(x -> System.out.println(x.getName()));

        menu.stream().collect(counting());
        System.out.println();
        menu.stream().collect(maxBy(Comparator.comparingInt(Dish::getCalories))).ifPresent(x -> System.out.println(x.getName()));

        System.out.println(menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum)));
        System.out.println(menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get());


        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        dishesByType.forEach((k, v) -> System.out.println("k1: " + k + ", v1: " + v));

        Map<String, List<Dish>> dishesByType2 = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) return "dish";
            else if (dish.getCalories() <= 700) return "normal";
            else return "fat";
        }));
        dishesByType2.forEach((k, v) -> System.out.println("k2: " + k + ", v2: " + v));

        Map<Dish.Type, Set<String>> dishesByType3 = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
                                                                if (dish.getCalories() <= 400) return "dish";
                                                                else if (dish.getCalories() <= 700) return "normal";
                                                                else return "fat";
                                                            }, toCollection(HashSet::new))));

        dishesByType3.forEach((k, v) -> System.out.println("k3: " + k + ", v3: " + v));


        Map<Dish.Type, Dish> dishesByType4 = menu.stream().collect(groupingBy(Dish::getType, Collectors.collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

        Dish[] value = new Dish[menu.size()];
        menu.toArray(value);
        for (int i = 0; i < value.length;) {
            System.out.println(value[i++].getName());
        }


    }
}
