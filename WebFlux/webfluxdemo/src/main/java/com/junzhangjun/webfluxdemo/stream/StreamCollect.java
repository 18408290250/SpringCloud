package com.junzhangjun.webfluxdemo.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 收集器
 * 收集器的作用就是将我们的Stream流处理后的数据收集起来，
 * 它可以将数据收集到集合类中，比如List Set Map中，
 * 或者是将我们处理后的数据进行在处理，处理成一条数据比如说求和操作
 */
public class StreamCollect {
    public static void main(String[] args) {
        // 测试数据
        List<Student> students = Arrays.asList(
                new Student("aa", 10, Gender.MALE, Grade.ONE),
                new Student("bb", 9, Gender.MALE, Grade.THREE),
                new Student("cc", 8, Gender.FEMALE, Grade.TWO),
                new Student("dd", 13, Gender.FEMALE, Grade.FOUR),
                new Student("ee", 7, Gender.FEMALE, Grade.THREE),
                new Student("ff", 13, Gender.MALE, Grade.ONE),
                new Student("gg", 13, Gender.FEMALE, Grade.THREE),
                new Student("hh", 9, Gender.FEMALE, Grade.TWO));

        // 得到所有学生的年龄列表
        // 尽量使用方法引用,s -> s.getAge() --> Student::getAge , 不会多生成一个类似lambda$0这样的函数
        Set<Integer> ages = students.stream().map(Student::getAge)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("所有学生的年龄:" + ages);
        System.out.println();

        // 统计汇总信息
        IntSummaryStatistics agesSummaryStatistics = students.stream()
                .collect(Collectors.summarizingInt(Student::getAge));
        System.out.println("年龄汇总信息:" + agesSummaryStatistics);
        System.out.println();

        // 分块
        Map<Boolean, List<Student>> genders = students.stream().collect(
                Collectors.partitioningBy(s -> s.getGender() == Gender.MALE));
         System.out.println("男女学生列表:" + genders);
        System.out.println();


        // 分组
        Map<Grade, List<Student>> grades = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade));
        System.out.println("学生班级列表:" + grades);
        System.out.println();

        // 得到所有班级学生的个数
        Map<Grade, Long> gradesCount = students.stream().collect(Collectors
                .groupingBy(Student::getGrade, Collectors.counting()));
        System.out.println("班级学生个数列表:" + gradesCount);

    }
}

class Student {
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 班级
     */
    private Grade grade;

    public Student(String name, int age, Gender gender, Grade grade) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", age=" + age + ", gender=" + gender
                + ", grade=" + grade + "]";
    }
}

/**
 * 性别
 */
enum Gender {
    MALE, FEMALE
}

/**
 * 班级
 */
enum Grade {
    ONE, TWO, THREE, FOUR;
}
