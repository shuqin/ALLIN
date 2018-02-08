package cc.lovesq.study

/**
 * Created by shuqin on 18/2/3.
 */
class DataProcessPatternsTest {

    def static main(args) {
        def senior = new Employ("name":"senior", "age":30, "salary": 19000)
        def junior = new Employ("name":"junior", "age":24, "salary": 9000)
        def fresh = new Employ("name":"fresh", "age":19, "salary": 6000)
        def isSeniorSalary = { it.salary > 15000 }
        def salaryDesc = { it.name + " salary is good." }
        def seniorDesc = DataProcessPatterns.getValue(isSeniorSalary, salaryDesc, senior, "em..")
        def juniorDesc = DataProcessPatterns.getValue(isSeniorSalary, salaryDesc, junior, "em..")

        println(seniorDesc)
        println(juniorDesc)

        def youngst = DataProcessPatterns.selectFirstMatched( {it.age < 20}, [senior,junior,fresh], null)
        println youngst.name
    }


}

class Employ {
    String name
    int age
    double salary

}
