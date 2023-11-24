package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Homework {
    /**
     * 0.1. Посмотреть разные статьи на Хабр.ру про Stream API
     * 0.2. Посмотреть видеоролики на YouTube.com Тагира Валеева про Stream API
     * aa
     * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
     * 1.1 Найти максимальное
     * 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
     * 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
     * aa
     * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
     * 2.1 Создать список из 10-20 сотрудников
     * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
     * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
     * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
     * 2.5 * Из списока сорудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
     */
    public static void main(String[] args) {
        List<Integer> list = Stream.generate(() -> ThreadLocalRandom.current().nextInt(1, 1_000_001))
                .limit(10)
                .toList();
        System.out.println(list);

        System.out.println("Максимальное значение: " + list.stream().max(Integer::compare).get());
        System.out.println("Сумма чисел больших, чем 500_000," +
                "умноженных на 5,\n" +
                "и уменьшенных на 150 : " +
                list.stream().filter(s -> s > 500_000)
                        .map(s -> s * 5 - 150).mapToInt(Integer::intValue).sum());
        System.out.println("Количество чисел, квадрат которых меньше," +
                " чем 100_000 : " + (long) list.stream().
                filter(s -> Math.pow(s, 2) < 100_000).toList().size());
        // Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
        // Создать список из 10-20 сотрудников
        List<Employee> employees = generateRandomEmployees();
        // Вывести список всех различных отделов (department) по списку сотрудников
        System.out.println("Departments: ");
        List<String> deps = employees.stream()
                .map(Employee::getDepartment).distinct()
                .toList();
        for (String dep : deps) {
            System.out.println("List of employees of Department: " + dep);
            employees.stream()
                    .filter(d -> Objects.equals(d.getDepartment(), dep))
                    .forEach(System.out::println);
        }
        // Всем сотрудникам, чья зарплата меньше 10_000,
        // повысить зарплату на 20%
        employees.stream()
                .filter(e -> e.getSalary() < 10_000)
                .forEach(e -> e.setSalary(e.getSalary() * 1.20));
        System.out.println(employees);
        // Из списка сотрудников с помощью стрима создать Map<String, List<Employee>>
        // с отделами и сотрудниками внутри отдела
        Map<String, List<Employee>> depsWithEmployees = new HashMap<>();
        for (String dep : deps) {
            depsWithEmployees.put(dep, employees.stream()
                    .filter(d -> Objects.equals(d.getDepartment(), dep)).toList());
        }
        depsWithEmployees.forEach((key, value) ->
                System.out.println(key + " : " + value));
//        Из списока сорудников с помощью стрима создать Map<String, Double> с отделами
//        и средней зарплатой внутри отдела
        Map<String, Double> depsWithSalaries = new HashMap<>();
        for (String dep : deps) {
            Double mean = employees.stream()
                    .filter(d -> d.getDepartment().equals(dep))
                    .map(s -> s.getSalary()).mapToDouble(a -> a).average().getAsDouble();
            depsWithSalaries.put(dep, mean);
        }
        depsWithSalaries.forEach((key, value) ->
                System.out.println("Department : " + key + "  " +
                        "Average salary : " + value));
    }

    private static List<Employee> generateRandomEmployees() {
        return List.of(
                new Employee("John Deere", 70, 1000, "Sales"),
                new Employee("Daniel Default", 60, 3000, "Sales"),
                new Employee("Erwin Hopkins", 34, 1500, "Sales"),
                new Employee("Ann Smith", 45, 1100, "Sales"),
                new Employee("Marine Le pain", 32, 1600, "Sales"),
                new Employee("Sara Black", 20, 1700, "Sales"),

                new Employee("Jane Fonda", 28, 2700, "HR"),
                new Employee("Liz McConner", 29, 1700, "HR"),
                new Employee("Ryan Pall", 21, 1200, "HR"),

                new Employee("Sim Fain", 25, 3200, "IT"),
                new Employee("Ken Wild", 27, 4200, "IT"),
                new Employee("Alex Boldwin", 23, 3000, "IT"),
                new Employee("Chris Ree", 29, 2000, "IT")

        );
    }

    static Integer myComparator(Integer a, Integer b) {
        return (a - b);
    }

}
