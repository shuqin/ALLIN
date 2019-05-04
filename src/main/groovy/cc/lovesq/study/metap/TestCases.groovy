package cc.lovesq.study.metap

class TestCases {

    def testA() { println 'do testA' }
    def testB() { println 'do testB' }
    def getTestData() { println "getTestData" }

    static void main(args) {
        def testCases = new TestCases()
        def testMethods = testCases.metaClass.methods.collect { it.name }.grep(~/^test\w+/)

        // 动态访问方法
        testMethods.each {
            testCases."$it"()
        }
    }
}
