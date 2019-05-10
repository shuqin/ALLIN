package cc.lovesq.study.metap

import groovy.util.logging.Log

@Log
class AutoGeneratingTestsPlain {

    def static generateTest(testData) {

        def orderSearchParam = new OrderSearchParam()
        testData.params.each { pprop, pvalue ->
            orderSearchParam."$pprop" = pvalue
        }
        log.info(orderSearchParam.toString())
        def result = mockSearch(orderSearchParam)
        assert result.code == 200
        assert result.msg == 'success'
        result.orders.each { order ->
            testData.validations.each { vdField, vdValue ->
                assert order."$vdField" == vdValue
            }
        }
    }

    static void main(args) {
        AutoGeneratingTestsPlain.generateTest(
                [
                    params: [
                        'orderTypeDesc': ['NORMAL'],
                        'recName': 'qin'
                    ],
                    validations: [
                            'order_type': 0,
                            'rec_name': 'qin'
                    ]
                ]
        )
    }

    def static mockSearch(orderSearchParam) {
        def results = new Expando(msg: 'success' , code: 200)
        results.orders = (1..20).collect {
            new Expando(order_type:0 , rec_name: 'qin')
        }
        results
    }

}

/*
 '''
 New way:

 define test data metap structure:
 {
 params: {
 'orderType': 'NORMAL',
 'recName': 'qin'
 },
 validations: {
 'order_type': 0,
 'rec_name': 'qin'
 }

 }

 parse metap structure and auto generates interface tests

 '''

 '''
 @TestMethod
  String testSearchOrderType() {

  conditions: orderTypeDesc = 'someType' eg. NORMAL
  return validations:   order_type = 'value for someType' eg. 0 for each order

  def orderTypeMap = ["NORMAL"            : 0,
  "GROUP"             : 10]

  getFinalResult orderTypeMap.collect {
  orderTypeDesc, returnValue ->
  GeneralOrderSearchParam orderSearchParam = ParamUtil.
  buildGeneralOrderSearchParam(kdtId)
  orderSearchParam.getOrderSearchParam().setOrderTypeDesc([orderTypeDesc])
  PlainResult<SearchResultModel> searchResult = generalOrderSearchService.
  search(orderSearchParam)
  assertSearchResult(searchResult, 'order_type', returnValue, orderSearchParam)
  }

  }

 @TestMethod
  String testSearchOrderState() {

  conditions: stateDesc = 'someState' eg. TOPAY
  return validations:   state = 'value for someState' eg. 1 for each order

  def orderStateMap = ["TOPAY"  : 1,
  "SUCCESS": 100]

  getFinalResult orderStateMap.collect {
  orderState, returnValue ->
  GeneralOrderSearchParam orderSearchParam = ParamUtil.
  buildGeneralOrderSearchParam(kdtId)
  orderSearchParam.getOrderSearchParam().setStateDesc([orderState])
  PlainResult<SearchResultModel> searchResult = generalOrderSearchService.
  search(orderSearchParam)
  assertSearchResult(searchResult, 'state', returnValue, orderSearchParam)
  }

  }

 @TestMethod
  String testCombinedFieldsSearch() {

  conditions: recName = qin && orderTypeDesc = NORMAL
  return validations:   rec_name = 'qin' , order_type = 0 for each order

  def compositeSearch = [new SingleSearchTestCase('recName', 'rec_name', 'qin',
  'qin'), new SingleSearchTestCase(
  'orderTypeDesc', 'order_type',
  'NORMAL', 0)]
  commonGeneralOrderSearchTest(new CompositeSearchTestCase(compositeSearch))
  return GlobalConstants.SUCCESS

  }


  '''
*/