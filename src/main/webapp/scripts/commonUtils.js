/*
 * 从 url 中提取参数并放置于 Map 中
 */
var extractParams = function(url) {
	var params = url.substring(url.indexOf('?')+1);
	var paramArray = params.split('&');
	var i, key_value, paramMap={};
	for (i=0; i<paramArray.length;i++) {
		key_value = paramArray[i].split('=');
		paramMap[key_value[0]] = key_value[1]; 
	}
	return paramMap;
};

/*
 * 登录身份验证检测
 */
var reloginMsg = '系统检测到您的身份标识已经失效, 请点击确定按钮重新获得身份标识!';
var isPassloginCheck = function() {
	rediectedToLoginCallback = function() {
		parent.document.location = rediectUrl;
	}
	if (document.cookie == null || document.cookie == '') {
		alert(reloginMsg);
		return false;
	}
	return true;
};

/*
 * 将给定日期范围划分为两段, 给定日期范围不超过七天
 * NOTE: beginDate, endDate 均为整数, 格式为 yyyyMmDd 
 */
var divideDateRange = function(beginDate, endDate) {
	
	var midDate = Math.floor((endDate+beginDate+1)/2);
	
	// 给定日期范围为连续的合法日期
	if (endDate - beginDate <= 6) {
		return [beginDate, midDate-1, midDate, endDate];                                                                                                                                                                                     
	}
	
	// 给定的日期范围跨月份
	return [beginDate, midDate-1, midDate, endDate];    
	
};

/*
 * 获取当前时间
 */
var getNow = function() {
	var now = new Date();
	return date2str(now);
};

var date2str = function(date) {
	var yy = date.getYear();
	if (yy < 1900) {
		yy = yy + 1900;
	}
	var MM = date.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = date.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = date.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = date.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = date.getSeconds();
	if(ss<10) ss = '0' + ss;
	return yy + '-' + MM + '-' + dd + ' ' + hh + ':' + mm + ':' + ss;
}

/*
 * 时间转换函数, 将 xxxx-xx-xx xx:xx:xx 格式的日期字符串转化为 javascript Date 对象
 */
var dateConvertFunc = function(datestr) {
	var yyyyMMddStr = datestr.split(' ')[0];
    var hhmmssStr = datestr.split(' ')[1];
    var yyyyMMddArray = yyyyMMddStr.split('-');
    var hhmmssArray = hhmmssStr.split(':'); 
    var yyyy = parseInt(yyyyMMddArray[0]);
    var MM = parseInt(yyyyMMddArray[1]);
    var dd = parseInt(yyyyMMddArray[2]);
    var hh = parseInt(hhmmssArray[0]);
    var mm = parseInt(hhmmssArray[1]);
    var ss = parseInt(hhmmssArray[2]);
    var date = new Date();
    date.setFullYear(yyyy, MM-1, dd);
    date.setHours(hh, mm, ss);
	return date;
};


/**
 * 将时间戳转化为日期格式 'yyyy-mm-dd hh:mm:ss'
 */
var timestamp2datetime = function(timestamp) {
	return date2str(new Date(timestamp));
}

/**
 * 将时间戳转化为日期格式 'yyyy.mm.dd'
 */
var timestamp2dateymd = function(timestamp) {
	var date = new Date(timestamp);
	return date.getFullYear() + '.' + (date.getMonth()+1) + '.' + date.getDate();
}

var obtainTimeStamp = function(dateTimeStr) {
	if (dateTimeStr == null || dateTimeStr == '') { return 0; }
	return Math.floor((new Date(dateTimeStr)).getTime() / 1000);
}

/*
 * 设置 jquery ui datepicker 外观
 */
timeFormatObj = {

    showSecond: true,  
    changeMonth: true,   
    timeFormat: 'HH:mm:ss',  
    dateFormat: 'yy-mm-dd',
    
    stepHour: 1,
	stepMinute: 5,
	stepSecond: 5
};


/*
 * jquery ajax 相关
 */
/**
 * 调用 jquery ajax 的基本方法
 * requestOptions   请求选项对象, 可指定如下属性:
 *                     url :    请求URL
 *                     params:  请求参数
 *                     timeout: 超时参数,  默认为 90s
 *                     method:  请求方法,  如果不指定为 POST, 默认使用 GET
 * succTodo            请求成功的回调函数， 
 *                     参数    resultJson    返回的JSON形式的结果串
 * failTodo            请求失败后的回调函数
 *                     参数    resultJson    返回的JSON形式结果串   
 * 
 */
var doAjaxRequest = function(requestOptions, succTodo, failTodo) {
	
	var jqXHR = jQuery.ajax({
		dataType: "json",
		url: requestOptions.url,
		data: requestOptions.params,
	    timeout: requestOptions.timeout || 90000,
	    type: requestOptions.method || 'GET',
	    async: requestOptions.async || true
	});
	if (succTodo != null && (typeof succTodo === 'function')) {
		jqXHR.done(succTodo);
	}
	if (failTodo != null && (typeof failTodo === 'function')) {
		jqXHR.fail(failTodo);
	}
	return jqXHR;
}

Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}

var createTableStr = function(columnTitle, columnNames, tableData, colRenders) {
	   
	   var tableId = 'table-' + unrepeatedNumber();
	   var tableStr = '<table class="aTable" id="' + tableId + '">';
	   var rowData = {};
	   var columnName = '';
	   var colRender = null;
	   var colRenderValue = '';
	   var colRenders = colRenders || {};
	   var i = 0, columnNum = (columnTitle == null ? 0 : columnTitle.length);
	   
	   var k = 0, rows = (tableData == null ? 0 : tableData.length);
	   
	   // 添加头标题
	   tableStr += '<thead><tr>';
	   for (i = 0 ; i < columnNum; i++) {
		   tableStr += '<th>' + columnTitle[i] + '</th>';
	   }
	   tableStr += '</tr></thead><tbody>';
	   
	   // 添加每一行
	   for (k=0; k<rows; k++) {
		   tableStr += '<tr>';
		   rowData = tableData[k];
		   for (i = 0 ; i < columnNum; i++) {
			   columnName = columnNames[i];
			   colRender = colRenders[columnName];
			   if (colRender != null && typeof colRender === 'function') {
				   colRenderValue = colRender(rowData);
			   }
			   else {
				   colRenderValue = rowData[columnName];
			   }
    		   tableStr += '<td>' + colRenderValue + '</td>';
    	   }
		   tableStr += '</tr>';
	   }
	   tableStr += '</tbody>';
	   return [tableId, tableStr];
	   
};

var tableConfigObj = {
		"sPaginationType": "full_numbers",
		"bLengthChange": false,
		"bFilter": false, 
		"bScrollInfinite": true,
		"sScrollY": "100%",
		"oLanguage":{
			"sLengthMenu":"显示 _MENU_ 条记录", 
			"sZeroRecords":"没有检索到数据", 
			"sInfo":"[正在显示第  _START_ - _END_ 条] [共 _TOTAL_ 条数据]", 
			"sInfoEmpty":"没有数据", 
			"sProcessing":"正在加载数据......", 
			
			"sInfoFiltered": "(过滤自 _MAX_ 条记录)", 
			"oPaginate":{ 
				"sFirst":"首页", 
				"sPrevious":"前页", 
				"sNext":"后页", 
				"sLast":"尾页" 
			}
		}
	};

var pagingtableConfigObj = {
		"sPaginationType": "full_numbers",
		"bLengthChange": false,
		"bFilter": false, 
		"oLanguage":{
			"sLengthMenu":"显示 _MENU_ 条记录", 
			"sZeroRecords":"没有检索到数据", 
			"sInfo":"[正在显示第  _START_ - _END_ 条] [共 _TOTAL_ 条数据]", 
			"sInfoEmpty":"没有数据", 
			"sProcessing":"正在加载数据......", 
			
			"sInfoFiltered": "(过滤自 _MAX_ 条记录)", 
			"oPaginate":{ 
				"sFirst":"首页", 
				"sPrevious":"前页", 
				"sNext":"后页", 
				"sLast":"尾页" 
			}
		}	
}

/**
 * 生成无重复随机数
 */
var unrepeatedNumber = function() {
	return Math.floor(Math.random()*3000);
}

/**
 * jquery 信息提示对话框
 * @param msg      要显示的消息
 * @param okCallback 单击"确定"按钮后的回调函数 
 */
var showMessageDialog = function(msg, okCallback) {
	if ($('#dialog-message').length == 0) {
		$(document.body).append('<div id="dialog-message"></div>');
	}
	$('#dialog-message').empty();
	$('#dialog-message').append(msg);
	$('#dialog-message').dialog({
		show: true,  
		dialogClass: "no-close",
		title: '提示信息',
	    modal: true,
	    closable: false,
	    buttons: [
	        {
	            text:'确定',
	            click: okCallback || 
		            function() {
		               $( this ).dialog("close");
		            }   
	        }
	    ]
	});
}

/**
 * jquery 操作确认对话框
 * @param msg          要显示的确认操作提示
 * @param confirmFunc  点击"确定"按钮后调用的函数
 */
var showConfirmDialog = function(msg, confirmFunc) {
	if ($('#dialog-message').length == 0) {
		$(document.body).append('<div id="dialog-message"></div>');
	}
	$('#dialog-message').empty();
	$('#dialog-message').append(msg);
	$('#dialog-message').dialog({
		show: true, 
		dialogClass: "no-close",
		title: '确认提示',
	    modal: true,
	    buttons: [
	        {
	    	    text: '确定', 
	    	    click: confirmFunc || function() {}
	        },
	        {
	        	text: '取消',
	        	click: function() {
			        $( this ).dialog( "close" );
			    }
	        }
	    ]
	});
}


/**
 *  绘图框架中的时间范围选择判断
 *  @param days 支持指定 days 天数范围内的查询, 不指定默认为30
 *  NOTE: 有些页面图表很多, 为保证性能, 仅支持一天范围内的查询
 */
var obtainTimeRange =  function(days) {
	
	var beginTimeStamp = 0, endTimeStamp = 0 , lastTimeValue = 0; 
	var now = new Date();
	var choice = $('input[name="timeSelect"]:checked').val();
	if (choice == 'lastTime') {
		endTimeStamp = Math.floor(now.getTime() / 1000);
  	    lastTimeValue = $('#lastTimeSelect').val(); 
  	    beginTimeStamp = endTimeStamp - parseInt(lastTimeValue) * 60 * 60;
	}
	else if (choice == 'timeRange') {
	    beginTimeStamp = obtainTimeStamp($('#beginDateTimepicker').val());
	    endTimeStamp = obtainTimeStamp($('#endDateTimepicker').val());
	}
	
	if (beginTimeStamp == 0 || endTimeStamp == 0) {
		showMessageDialog('时间范围不完整, 请选择时间!');
		return null;
	}
	
	if (beginTimeStamp - endTimeStamp >= 0) {
		showMessageDialog('开始时间必须小于结束时间!'); 
		return null;
	}
	
	if (days == null || days == 0) {
		days = 30;
	}
	
	if (endTimeStamp - beginTimeStamp > days*24*60*60) {
		if (days == 1) {
			showMessageDialog('只支持一天范围内的自定义查询! 单击图表放大显示后可支持一周范围内的查询'); 
		}
		else if (days == 7) {
			showMessageDialog('只支持一周范围内的自定义查询!'); 
		}
		else {
			showMessageDialog('只支持一月范围内的自定义查询!'); 
		}
		return null;
	}
	return [beginTimeStamp, endTimeStamp];
}

var numRegex = /^[0-9]+(\.[0-9]+)?$/;

var isNumber = function(numStr)
{
	if (numStr == null || numStr == '') {
		return false;
	}
	return numRegex.test(numStr);
}

/**
 * 将 chartData 按照指定字段插入排序, 适合于基本有序的列表
 * @chartData 对象数组 eg. [{'fieldName': 'xxx', 'field2', 'xxx'}, {'fieldName': 'xxx', 'field2', 'xxx'}]
 * @fieldName 指定字段名称
 */
var insertSort = function(chartData, fieldName) {  
	var temp = {}, j=0;
	var len = (chartData == null ? 0 : chartData.length);
	if (len == 0 || len == 1) {
		return chartData;
	}
	for(var i=1; i< len; i++) {
	    if(chartData[i][fieldName] < chartData[i-1][fieldName]) {
	        temp = chartData[i];
	        j = i-1;
	        do {
	    	    chartData[j+1] = chartData[j];
	            j--;
	        } while (j>-1 && (temp[fieldName] < chartData[j][fieldName]));
	        chartData[j+1] = temp;
	    }
	}
	return chartData;
}