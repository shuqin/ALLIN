package zzz.study.function;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsToMapDemo {

    public static void main(String[] args) {
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

}

@AllArgsConstructor
@Data
class Person {
    private String name;
    private Integer age;
}
