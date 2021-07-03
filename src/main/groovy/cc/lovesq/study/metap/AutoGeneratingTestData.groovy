package cc.lovesq.study.metap

class AutoGeneratingTestData {

    public static final orderTypeTestData = ['condField': 'orderTypeDesc', 'validationField': 'order_type',
                                             'valuePair': [["NORMAL"]: 0,
                                                           ["GROUP"] : 10]]

    public static final stateTestData = ['condField': 'stateDesc', 'validationField': 'state',
                                         'valuePair': ["TOPAY"  : 1,
                                                       "SUCCESS": 100]]

    def static generateAllTestCases(testDatas) {
        testDatas.collect {
            generateTestCases(it)
        }.flatten()
    }

    def static generateTestCases(testData) {

        testData.valuePair.collect { key, value ->
            def searchCondField = testData['condField']
            def validationField = testData['validationField']

            return [
                    params     : [
                            "$searchCondField": key
                    ],
                    validations: [
                            "$validationField": value
                    ]
            ]
        }
    }

    static void main(args) {
        println AutoGeneratingTestData.generateTestCases(orderTypeTestData)
        println AutoGeneratingTestData.generateTestCases(stateTestData)
    }
}
