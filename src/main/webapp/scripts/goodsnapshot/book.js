var goodsId = $('#goodsId').text();
var price = $('#price').text();
var title = $('#title').text();
var desc = $('#desc').text();
var choice = $('#choice').text();
var serviceKeys = $('#serviceKeys').text();
var userId = $('#userId').text();

var priceNum = parseInt(price) * 100;

$('#bookOrderButton').click(function(event) {

   var bookInfo = {
           'goods': {
                'goodsId': goodsId,
                'price': priceNum,
                'title': title,
                'desc': desc,
                'serviceKeys' : serviceKeys,
                'choice': choice
            },
            'order': {
                'orderNo': "E202009000000123400001",
                'userId': userId,
                'bookTime': 1598889900,
                'deliveryType': "express",
                'price': priceNum,
                'isCodPay' : false,
                'isSecuredOrder': true,
                'hasRetailShop' : true,
                'localDeliveryBasePrice': 1,
                'localDeliveryPrice': 2
            }
   };

   var succTodo = function(result) {
     alert("下单成功");
   };

   var failTodo = function(resp) {
     alert('下单失败!');
   };

   var jqXHR = jQuery.ajax({
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		url: 'http://localhost:8080/api/goodsnapshot/save',
   		data: JSON.stringify(bookInfo),
   	    timeout: 90000,
   	    type: 'POST'
   	});
   	if (succTodo != null && (typeof succTodo === 'function')) {
   		jqXHR.done(succTodo);
   	}
   	if (failTodo != null && (typeof failTodo === 'function')) {
   		jqXHR.fail(failTodo);
   	}
   	return jqXHR;

});