package cc.lovesq.study.metap


class PutingAllTogether {

    static void main(args) {
        def testDatas = AutoGeneratingTestData.declaredFields.grep { it.name.endsWith('TestData') }.collect { it.get(AutoGeneratingTestData.class) }

        def testCases = AutoGeneratingTestData.generateAllTestCases(testDatas)
        AutoGeneratingTestsUsingMetap.generateTests(testCases)
    }
}
