$('#bookOrderButton').click(function(event) {

   var shopId = $('#shopId').text();
   var goodsId = $('#goodsId').text() + Math.floor(Math.random()*10000);
   var price = $('#price').text();
   var title = $('#title').text();
   var desc = $('#desc').text();
   var choice = $('#choice').text();
   var userId = $('#userId').text();

   var deliveryType = $("input[name='deliveryType']:checked").val();
   var serviceKeys = $('#serviceKeys').text() + "," + deliveryType;
   var isCodPay = $("input[name='codpay']:checked").val();

   var priceNum = parseInt(price) * 100;

   var bookInfo = {
       'goods': {
            'shopId': shopId,
            'goodsId': goodsId,
            'price': priceNum,
            'title': title,
            'desc': desc,
            'serviceKeys' : serviceKeys,
            'choice': choice
        },
        'order': {
            'shopId': shopId,
            'userId': userId,
            'deliveryType': deliveryType,
            'price': priceNum,
            'isCodPay' : isCodPay,
        }
   };

   var succTodo = function(result) {

       var orderNo = result.data.orderNo;
       var goodsId = result.data.goodsId;

       $('#bookArea').css("display", "none");
       $('#goodsnapshot').css('display', 'block');
       $('#orderNoResult').text("订单号：" + orderNo);

       $('#snapshotBtn').click(function(event) {
           sendDetailRequest(orderNo, goodsId);
       });

       $('#goodsnapshot').removeAttr('hidden');
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

$('#cancelBtn').click(function(event){
    $('#orderNoResult').text('');
    $('#goodsnapshot').css('display', 'none');
    $('#bookArea').css("display", "block");
    $('#goodsnapshotContent').css('visibility', 'hidden');
    $('#snapshotBtn').unbind('click');
})

function sendDetailRequest(orderNo, goodsId) {

    var query = {
        'orderNo': orderNo,
        'goodsId': goodsId
    }

    var succTodoForSnapshot = function(resp) {

        $('#pricesnapshot').text(resp.data.priceYuan);
        $('#titlesnapshot').text(resp.data.goodsInfo.title);
        $('#choicesnapshot').text(resp.data.goodsInfo.choice);
        $('#servicesnapshots').empty();

        var goodsServiceSnapshots = resp.data.goodsServiceSnapshots;
        for (i=0; i < goodsServiceSnapshots.length; i++) {
            var snapshot = goodsServiceSnapshots[i];
            var serviceDivStr = "<div class='gs'><div class='st'>" + snapshot.title + "</div>"
                                              + "<div class='sd'>" + snapshot.desc + "</div></div>"
            $('#servicesnapshots').append(serviceDivStr);
        }

        $('#goodsnapshotContent').css('visibility', 'visible');

    }

    var failTodoForSnapshot = function(resp) {
        alert('查看快照失败!');
    };

    var jqXHRForSnapshot = jQuery.ajax({
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: 'http://localhost:8080/api/goodsnapshot/detail',
        data: JSON.stringify(query),
        timeout: 90000,
        type: 'POST'
    });
    if (succTodoForSnapshot != null && (typeof succTodoForSnapshot === 'function')) {
        jqXHRForSnapshot.done(succTodoForSnapshot);
    }
    if (failTodoForSnapshot != null && (typeof failTodoForSnapshot === 'function')) {
        jqXHRForSnapshot.fail(failTodoForSnapshot);
    }
    return jqXHRForSnapshot;

}