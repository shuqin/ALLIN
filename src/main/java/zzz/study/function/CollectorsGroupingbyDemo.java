package zzz.study.function;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorsGroupingbyDemo {

    public static void main(String[] args) {

        List<Employee> employList = Arrays.asList(
                new Employee("su", "mid", "engine"),
                new Employee("lan", "mid", "prod"),
                new Employee("qin", "data", "engine"),
                new Employee("yu", "mid", "engine"),
                new Employee("ming", "data", "engine")
        );


        // Map[department, Map[position, List[name]]]
        Map<String, Map<String, List<String>>> groupedEmployees =
                employList.stream().collect(
                        Collectors.groupingBy(Employee::getDepartment,
                                Collectors.groupingBy(Employee::getPosition, new EmployNameListCollector())
                        ));

        System.out.println("groupedEmployees: " + groupedEmployees);

    }

}

class EmployNameListCollector implements Collector<Employee, List<String>, List<String>> {

    @Override
    public Supplier<List<String>> supplier() {
        return () -> new ArrayList<>();
    }

    @Override
    public BiConsumer<List<String>, Employee> accumulator() {
        return (list, e) -> list.add(e.getName());
    }

    @Override
    public BinaryOperator<List<String>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<String>, List<String>> finisher() {
        return i -> i;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}


@AllArgsConstructor
@Data
class Employee {
    private String name;
    private String department;
    private String position;

}