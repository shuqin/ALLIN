package zzz.study.function;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import shared.utils.ReflectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectorsToMapDemo {

    public static void main(String[] args) {

        //test();
        //test2();
        //test3();
        //test4();

        test5();
    }

    private static void test() {
        List<Person> persons = Arrays.asList(new Person("qin", 32), new Person("ni", 24));
        Map<String, Integer> personAgeMap = persons.stream().collect(Collectors.toMap(
                Person::getName, Person::getAge
        ));
        System.out.println("personAgeMap: " + personAgeMap);

        List<Person> anotherPersons = Arrays.asList(new Person("su", 24), new Person("ni", 25));
        Map<String, Integer> anotherPersonAgeMap = anotherPersons.stream().collect(Collectors.toMap(
                Person::getName, Person::getAge
        ));

        Map<String, Integer> merged = persons.stream().collect(Collectors.toMap(
                Person::getName, Person::getAge, (age1, age2) -> age1, () -> anotherPersonAgeMap
        ));
        System.out.println("merged: " + merged);
    }

    public static void test2() {
        List<Person> persons = Arrays.asList(new Person("qin", 32), new Person("ni", 24));
        System.out.println(getValues(persons, "name"));
        System.out.println(getValues(persons, "age"));

        System.out.println(getValues(persons, Person::getName));
        System.out.println(getValues(persons, Person::getAge));

    }

    public static void test3() {
        List<Person> persons = Lists.newArrayList(new Person("qin", 32), new Person("ni", 24));
        System.out.println(remove(persons, Person::getAge, age -> age < 30));
        System.out.println(remove(persons, Person::getName, name -> "qin".equals(name)));
    }

    public static void test4() {
        List<Person> persons = Lists.newArrayList(new Person("qin", 32), new Person("ni", 24));
        System.out.println(remove(persons, p -> p.getAge() < 30));
        System.out.println(remove(persons, p -> "qin".equals(p.getName())));
    }

    public static void test5() {
        List<Person> persons = Lists.newArrayList(new Person("qin", 32), new Person("ni", 24));
        System.out.println(removee(persons, p -> p.getAge() < 30));
        System.out.println(removee(persons, p -> "qin".equals(p.getName())));
    }

    public static List getValues(List<Person> persons, String fieldName) {
        List values = new ArrayList();
        for (Person p: persons) {
            values.add(ReflectionUtil.getValue(p, fieldName));
        }
        return values;
    }

    public static <T> List<T> getValues(List<Person> persons, Function<Person, T> fieldFunc) {
        return persons.stream().map(fieldFunc).collect(Collectors.toList());
    }

    public static List<Person> remove(List<Person> persons, Predicate<Person> test) {
        Iterator<Person> personIterator = persons.iterator();
        while (personIterator.hasNext()) {
            Person p = personIterator.next();
            if (test.test(p)) {
                personIterator.remove();
            }
        }
        return persons;
    }

    public static <T> List<T> removee(List<T> objList, Predicate<T> test) {
        Iterator<T> personIterator = objList.iterator();
        while (personIterator.hasNext()) {
            T t = personIterator.next();
            if (test.test(t)) {
                personIterator.remove();
            }
        }
        return objList;
    }

    public static <T> List<Person> remove(List<Person> persons, Function<Person, T> fieldFunc, Predicate<T> test) {
        Iterator<Person> personIterator = persons.iterator();
        while (personIterator.hasNext()) {
            T t = fieldFunc.apply(personIterator.next());
            if (test.test(t)) {
                personIterator.remove();
            }
        }
        return persons;
    }

}

@AllArgsConstructor
@Data
class Person {
    private String name;
    private Integer age;
}
