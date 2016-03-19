package cc.lovesq.study.metap

class Car {
    def check() { System.out.println "check called..." }

    def start() { System.out.println "start called..." }

    def drive() { System.out.println "drive called..." }

    static void main(margs) {
        Car.metaClass.invokeMethod = { String name, args ->
            System.out.print("Call to $name intercepted... ")
            if (name != 'check') {
                System.out.print("running filter... ")
                Car.metaClass.getMetaMethod('check').invoke(delegate, null)
            }
            def validMethod = Car.metaClass.getMetaMethod(name, args)
            if (validMethod != null) {
                validMethod.invoke(delegate, args)
            } else {
                Car.metaClass.invokeMissingMethod(delegate, name, args)
            }
        }

        Car car = new Car()

        car.start()
        car.drive()
        car.check()
    }
}


