package cc.lovesq.study.curry

class GeneralCaller {


    static void main(args) {
        def generalCaller = { param, handler -> handler(param) }
        def orderProcessor = generalCaller.curry(new OrderParam(orderNo: 'E0001', shopId: 55))

        orderProcessor(this.&cancel)
        orderProcessor(this.&complete)

        def listSorter = generalCaller.rcurry(this.&sort)
        println listSorter([4, 7, 2, 6, 1, 3])
        println listSorter(["i", "have", "a", "dream"])

    }

    def static sort(list) {
        list.sort()
        return list
    }

    def static cancel(order) {
        println("cancel: " + order.orderNo)
    }

    def static complete(order) {
        println("complete: " + order.orderNo)
    }

}

class OrderParam {
    def orderNo
    def shopId
}
