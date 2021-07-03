package zzz.study.reactor;

import com.google.common.collect.Sets;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;

/**
 * @Description RxJava基本Demo
 * @Date 2021/1/23 12:28 下午
 * @Created by qinshu
 */
public class RxJavaBasic {

    public static void main(String[] args) {
        //testBasicFlow();
        testDirectSubscribe();
    }

    public static void testBasicFlow() {
        for (int i = 0; i < 2; i++) {
            ObservableOnSubscribe observableOnSubscribe = new MyEmitter();
            Observable observable = Observable.create(observableOnSubscribe);

            Observer observer = new MyObserver();
            observable.subscribe(observer);
            System.out.println();
        }
    }

    public static void testDirectSubscribe() {
        Observer observer = new RepeatedSubscribeMyObserver();
        Observable.fromArray("I", "Have", "a", "dream").subscribe(observer);
        Observable.fromArray("changed").subscribe(observer);
        Observable.just(1, 2, 3).subscribe(observer);
        Observable.range(1, 4).subscribe(observer);


        Iterable<? extends ObservableSource<? extends Integer>> observableSourceSet = Sets.newHashSet(
                Observable.fromArray(3, 4, 5),
                Observable.range(10, 3)
        );
        Observable.merge(observableSourceSet).subscribe(observer);

        Observable.amb(observableSourceSet).subscribe(observer);

        Observable.range(1, 10).filter(x -> x % 2 == 0).subscribe(observer);

        Observable.range(1, 10).map(x -> x * x).subscribe(observer);
        Observable.range(1, 10).flatMap(x -> Observable.just(x * x)).subscribe(observer);

        Observable.range(1, 10).scan(1, (x, y) -> x * y).subscribe(observer);

        Observable.just("i", "love", "you", "ni").zipWith(Observable.just(28, 520, 25, 999), (x, y) -> new Person(x, y))
                .subscribe(observer);

        Observable.just(28, 520, 25, 999).groupBy(i -> (i > 100 ? "old" : "new")).subscribe(new GroupedRepeatedSubscribeMyObserver());

    }

}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}



