var goodsId = $('#goodsId').text() + Math.floor(Math.random()*10000);
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
                'orderNo': "E20200900000000" + userId + Math.floor(Math.random()*10000),
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
       alert(result.data.orderNo);
       var orderNo = result.data.orderNo;
       var goodsId = result.data.goodsId;

       var query = {
           'orderNo': orderNo,
           'goodsId': goodsId
       }

       var succTodo = function(resp) {
           $('#goodsnapshot').text(JSON.stringify(resp.data));
       }

       var failTodo = function(resp) {
           alert('查看快照失败!');
         };

         var jqXHR = jQuery.ajax({
         		dataType: "json",
         		contentType: "application/json; charset=utf-8",
         		url: 'http://localhost:8080/api/goodsnapshot/detail',
         		data: JSON.stringify(query),
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