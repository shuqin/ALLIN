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
	"sSearch": "搜索",
	"bSort":true,
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

function createTableRegion(container) {
	var tableContent = 
		'<div id="tableRegion"></div>' +
		'<div id="paginationBar">' +
     	  '<div class="dataTables_info"></div>' + 
     	  	'<div class="dataTables_paginate paging_full_numbers">' +
     	  	'<a tabindex="0" class="first paginate_button paginate_button_disabled">首页</a>' +
     	  	'<a tabindex="0" class="previous paginate_button paginate_button_disabled">前页</a>' +
     	  	'第 <input type="text" name="pageNum" id="pageNum"/> 页 <button type="button" id="gotopage">GO</button>' +
     	  	'<a tabindex="0" class="next paginate_button paginate_button_disabled">后页</a>' +
     	  	'<a tabindex="0" class="last paginate_button paginate_button_disabled">尾页</a>' +
          '</div>' +
        '</div>';
	container.append(tableContent);
}

/* Get the row data which are currently selected */
var fnGetSelected = function(lastUpdatedData, parentTrElem)
{
  	var selectedDTIndex = parentTrElem[0]._DT_RowIndex;
  	return lastUpdatedData[selectedDTIndex];
};

var setButtonDisabled = function(BtnClass, isShow) {
    if (isShow) {
		   $('.'+BtnClass).removeClass('paginate_button_disabled');
	   }
	   else {
		   $('.'+BtnClass).addClass('paginate_button_disabled');
	   }
};

/**
 * 
 * 服务端分页通用实现
 * @param config 表格配置, 配置列信息
 * {
 * 	'requestOptions': {                                       // 请求选项
 *      'url': 'url',                                         // 请求 URL
 *      'params': 'params'                                    // 请求参数
 *   },
 *  'pageSize': pageSize                                          //  每页记录数, 可选   
 *  'pageNum': pageNum                                            //  当前页码                                          
 *  'containerId': 'divId'                                        // 分页表格所在的父容器ID, 若不指定，为当前 body
 *  'tableConfig': {
 *  	'columnNames': [colName1, colName2, ..., colNameN],       // 列标题
 *  	'columnFields': [colField1, colField2, ..., colFieldN],   // 列字段
 *  	'columnRenders': [{'colField1': func1, 'colField2': func2, ..., 'colFieldN': funcN}],  // 列渲染函数
 *  	'dataTableConfig': {}             // dataTable 配置 
 *   } 
 * }
 * 
 * NOTE:
 * 服务端返回的结果:
 * {'showFirst': true or false , 'showLast': true or false, 
 * 	'showNext': true or false, 'showPrevious': true or false
 * 	'total': num, 'totalPages': num, 'pageSize':num, 'pageNum': num, 
 *  'data': [{'colField1': xxx, 'colField2': yyy, ..., 'colFieldN': 'NNN'}]
 * }
 */
var showPagingGrid = function(config) {
    
	var container = null;
	var containerId = config['containerId']
	if (containerId != null) {
		container = $('#'+containerId);
	}
	else {
		container = $(document.body);
	}
	
	var pageSize = config['pageSize'] || 10;
	
	$('#tableRegion').remove();
	$('#paginationBar').remove();
	
	createTableRegion(container);
    $('#paginationBar').hide();
     
    var drawpagingTable = function(pageNum, result) {
    	   
    	   $('#tableRegion').empty();
    	   $('#paginationBar').show();
    	   
    	   var data = result.data;
    	   var showFirst = result.showFirst;
    	   var showLast = result.showLast;
    	   var showNext = result.showNext;
    	   var showPrevious = result.showPrevious;
    	   var total = result.total; 
    	   var totalPages = result.totalPages;
    	   var pageSize = result.pageSize;
    	   var pageNumResult = result.pageNum;
    	   var currStart = (pageNumResult-1)*pageSize+1;
    	   var currEnd = pageNumResult*pageSize;
    	   
    	   $('.dataTables_info').html('[正在显示第 ' + currStart + ' - ' + currEnd + ' 条数据] [' + '共 ' + total + ' 条记录, ' + totalPages + ' 页]');
    	   $('#pageNum').val(pageNumResult); 
    	   
    	   setButtonDisabled('first', showFirst);
    	   setButtonDisabled('previous', showPrevious);
    	   setButtonDisabled('next', showNext);
    	   setButtonDisabled('last', showLast);
    	   
    	   parentRegion = $('#tableRegion');
    	   
    	   tableConfig = config['tableConfig'];
 		   tableReturned = createTableStr(tableConfig['columnNames'], tableConfig['columnFields'], data, tableConfig['columnRenders']);
 		   parentRegion.append(tableReturned[1]);
 		   var lastTable = $('#' + tableReturned[0]).dataTable(tableConfig['dataTableConfig']);
 		  
 		   // 隐藏 dataTable 的数据显示
 		   $('#tableRegion > .dataTables_wrapper > .dataTables_info').css('display', 'none');
 		   
 		   $('.first').unbind('click');
		   $('.last').unbind('click');
		   $('.previous').unbind('click');
		   $('.next').unbind('click');
		   
		   $('.first').click(function(event) {
        	   if (!$(this).hasClass('paginate_button_disabled')) {
        		   config['pageNum'] = 1;
            	   showPagingGrid(config);
        	   }
           });

           $('.last').click(function(event) {
        	   if (!$(this).hasClass('paginate_button_disabled')) {
        		   config['pageNum'] = totalPages;
            	   showPagingGrid(config);
        	   }
           });
           
           $('.previous').click(function(event) {
        	   if (!$(this).hasClass('paginate_button_disabled')) {
        		   config['pageNum'] = pageNumResult-1;
            	   showPagingGrid(config);
        	   }
           });
           
           $('.next').click(function(event) {
        	   if (!$(this).hasClass('paginate_button_disabled')) {
        		   config['pageNum'] = pageNumResult+1;
            	   showPagingGrid(config);
        	   }
           });
 		   
           var afterTableDrawed = function() {
        	   
        	   $('#'+tableReturned[0]+' tr').removeClass('row_selected');
        	    
               // 设置选中行的样式
		   	   $('#'+tableReturned[0]+' tr').click(function(event) {
		   	       $('#'+tableReturned[0]+' tr').removeClass('row_selected');
		   		   $(this).addClass('row_selected'); 
		       });
  	        	
  	           $('.paginate_button').click(function(event) {
      			   afterTableDrawed();
      		   });
  	           
           };
           
           afterTableDrawed();
 	       
      };
       
     $('#gotopage').click(function(event) {
    	 
    	   var pageNum = $('#pageNum').val();
   
    	   pageNum = pageNum.replace(/\s+/g, '');
    	   if (pageNum == '' || isNaN(pageNum)) {
    		   alert('页码不能为空或非数字!');
    		   return ;
    	   }
   
    	   $('#tableRegion').empty();
    	   $('#tableRegion').html('请稍等, 正在加载数据...');
    	   
    	   config['pageNum'] = parseInt(pageNum);
    	   showPagingGrid(config);
     });
     
     var requestOptions = config['requestOptions'];
     var pageNum = config['pageNum'] || 1;
     requestOptions['params']['pageNum'] = pageNum;
     requestOptions['params']['pageSize'] = pageSize;
     
     var succTodo = function(result) {
    	 drawpagingTable(pageNum, result);
     };
     
     var failTodo = function(resp) {
    	 alert('未能获取数据!');
     };
     
     doAjaxRequest(requestOptions, succTodo, failTodo);
     
}


/**
 * 
 * 基于 DataTable 的客户端分页通用实现
 * 
 * @param config 表格配置, 配置列信息
 * {
 * 	'requestOptions': {                                       // 请求选项
 *      'url': 'url',                                         // 请求 URL
 *      'params': 'params'                                    // 请求参数
 *   },
 *  'pageSize': pageSize                                          //  每页记录数, 可选                                             
 *  'containerId': 'divId'                                        // 分页表格所在的父容器ID, 若不指定，为当前 body
 *  'tableConfig': {
 *  	'columnNames': [colName1, colName2, ..., colNameN],       // 列标题
 *  	'columnFields': [colField1, colField2, ..., colFieldN],   // 列字段
 *  	'columnRenders': [{'colField1': func1, 'colField2': func2, ..., 'colFieldN': funcN}],  // 列渲染函数
 *  	'dataTableConfig': {}             // dataTable 配置 
 *   } 
 * }
 * 
 * NOTE:
 * 服务端返回的结果:
 * {
 * 	'total': num,  
 *  'data': [{'colField1': xxx, 'colField2': yyy, ..., 'colFieldN': 'NNN'}]
 * }
 */
var showPagingGridCliently = function(config) {
    
	var container = null;
	var containerId = config['containerId']
	if (containerId != null) {
		container = $('#'+containerId);
	}
	else {
		container = $(document.body);
	}
	
    var drawpagingTable = function(result) {
    	   
    	   container.empty();
    	   
    	   tableConfig = config['tableConfig'];
 		   tableReturned = createTableStr(tableConfig['columnNames'], tableConfig['columnFields'], result.data, tableConfig['columnRenders']);
 		   container.append(tableReturned[1]);
 		   var lastTable = $('#' + tableReturned[0]).dataTable(tableConfig['dataTableConfig']);
 		  
           var afterTableDrawed = function() {
        	   
        	   $('#'+tableReturned[0]+' tr').removeClass('row_selected');
        	    
               // 设置选中行的样式
		   	   $('#'+tableReturned[0]+' tr').click(function(event) {
		   	       $('#'+tableReturned[0]+' tr').removeClass('row_selected');
		   		   $(this).addClass('row_selected'); 
		       });
  	        	
  	           $('.paginate_button').click(function(event) {
      			   afterTableDrawed();
      		   });
  	           
           };
           
           afterTableDrawed();
     };
       
     var requestOptions = config['requestOptions'];
     
     var succTodo = function(result) {
    	 drawpagingTable(result);
     };
     
     var failTodo = function(resp) {
    	 alert('未能获取数据!');
     };
     
     doAjaxRequest(requestOptions, succTodo, failTodo);
     
}
