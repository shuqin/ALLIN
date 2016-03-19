
/**
 * highcharts 需要在 HTML DIV 组件中绘制图形, 这里是监控指标名称与DIV区域的映射关系管理
 */
var indicator_divId_relations = {
	'vmcpu':       'VmCpuUsageperfchartdiv',    // cpu 利用率
	'vmmem':       'VmMemUsageperfchartdiv',    // mem 利用率
    'vminterflow': 'VmInterflowperfchartdiv',   // 公网进出流量
    'vmintraflow': 'VmIntraflowchartdiv',       // 私网进出流量
    'vminterdrop': 'VmInterdropperfchartdiv',   // 公网进出丢包
    'vmintradrop': 'VmIntradropchartdiv',       // 私网进出丢包
    'vmiorwflow':  'VmIorwflowperfchartdiv',    // 磁盘总读写流量
    'vmiorwtimes': 'VmIorwtimesperfchartdiv',   // 磁盘总读写次数
    'deviceiorwflow':  'DeviceIorwflowperfchartdiv',    // 磁盘读写流量
    'deviceiorwtimes': 'DeviceIorwtimesperfchartdiv',   // 磁盘读写次数
    'deviceiorwtimesmearge': 'DeviceIorwtimesMeargeperfchartdiv',   // 磁盘读写次数(Mearge)
    'deviceiorwpercent': 'DeviceIorwPercentchartdiv',   // 磁盘读写比例
    'deviceiorwlatency': 'DeviceIorwLatencychartdiv',   // 磁盘读写延时
    'deviceiorwblock': 'DeviceIorwchartdiv',   // 磁盘块分布
    'deviceiorwblockreadlayout': 'DeviceIoBlockReadchartdiv',   // 磁盘块分布
    'deviceiorwblockwritelayout': 'DeviceIoBlockWritechartdiv',   // 磁盘块分布
    'deviceiorwlatencyreadlayout': 'DeviceIoLatencyReadLayoutchartdiv',   // 磁盘延时分布
    'deviceiorwlatencywritelayout': 'DeviceIoLatencyWriteLayoutchartdiv',   // 磁盘延时分布
    'iooverlayedcount': 'ioOverlayedCountdiv',   // overlay重复度
    'flushiocount': 'flushIoCountdiv',   // flush io count
//    'deviceioutil':    'DeviceIoutilchartdiv',          // 磁盘 ioutil
//    'deviceioutil':    'DeviceIoutilchartdiv',          // 磁盘 ioutil
    'devicelatancy':    'DeviceLatencychartdiv',         // 磁盘 latency
    'nccpu':       'NcCpuUsageperfchartdiv',    // cpu利用率
    'ncload':      'NcLoadperfchartdiv',        // load
    'ncflow':       'NcNetworkflowchartdiv',     // 网络流量
    'ncpackage':    'NcPackagechartdiv',         // 网络丢包 
    'ncpps':        'NcPpschartdiv',             // 网络pps
    'ncconn':        'NcConnchartdiv',             // 网络连接数
    'nciorwflow':    'NcIorwperfchartdiv',      // NC IO读写流量
    'nciorwtimes':   'NcIorwtimeschartdiv',     // NC IO读写次数
    'ncioutil':      'NcIoutilchartdiv',        // NC ioutil
    'conn':        'Vipcurrconnchartdiv',     // vip 并发连接数
    'cps':         'Vipnewconnchartdiv',      // vip 新建连接数
    'bps':         'Vipflowchartdiv',         // vip 流量
    'pps':         'Vippackagechartdiv'       // vip 包数量
};

var vmAllIndicators = {'CPU': ['vmcpu'], 'IO': ['vmiorwflow', 'vmiorwtimes', 'vmioutil'], '网络': ['vminterflow', 'vmintraflow', 'vminterdrop', 'vmintradrop']};
var deviceAllIndicators = ['deviceiorwflow', 'deviceiorwtimes', 'deviceiorwtimesMearge','deviceiorwpercent','deviceiorwlatency','deviceiorwBlock','ioOverlayedCount','flushIoCount','deviceiorwLatencyReadLayout','deviceiorwLatencyWriteLayout','deviceiorwBlockReadLayout','deviceiorwBlockWriteLayout'];
var ncAllIndicators = {'CPU': ['nccpu', 'ncload'], '网络':['ncflow', 'ncpps', 'ncconn', 'ncpackage'], 'IO': ['nciorwflow', 'nciorwtimes', 'ncioutil']};
var vipAllIndicators = ['conn', 'cps', 'bps', 'pps' ];

var obtainDivIdForIndicator = function(indicator) {
	return indicator_divId_relations[indicator];
};

/**
 * 绘制指定时间范围内 VM 所有性能指标的曲线图
 * @param vmEntity vm实体参数, 见 drawVmSingleIndicator
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawVmIndicators = function(vmEntity, timeRange, parentRegionDivId, titleText) {
	var indicatorGroup = '', indicators = [];
	var indicatorGroupRegionDivId = '';
	for (indicatorGroup in vmAllIndicators) {
		indicatorGroupRegionDivId = indicatorGroup + '-' + unrepeatedNumber(); 
		$('#'+parentRegionDivId).append('<div id="' + indicatorGroupRegionDivId + '"><p><strong>' + indicatorGroup + ' 指标: </strong></p></div><div class="clear"></div>');
		indicators = vmAllIndicators[indicatorGroup];
		drawVmSpecIndicators(vmEntity, indicators, timeRange, indicatorGroupRegionDivId, titleText);
	}
	
};

/**
 * 绘制指定时间范围内 VM 指定的多个性能指标的曲线图
 * @param vmEntity vm实体参数, 见 drawVmSingleIndicator
 * @param specIndicators 指定的多个性能指标, 结果为指标名称的数组 eg. ['cpu', 'interflow']
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawVmSpecIndicators = function(vmEntity, specIndicators, timeRange, parentRegionDivId, titleText) {
	var i=0, indicatorNum = (specIndicators == null ? 0 : specIndicators.length);
	for (i=0; i < indicatorNum; i++) {
		drawVmSingleIndicator(vmEntity, specIndicators[i], timeRange, parentRegionDivId, titleText);
	}
};

/**
 * 绘制指定时间范围内 VM 单个性能指标的曲线图(适配层)
 * @param vmEntity vm实体参数, 结构为 
 *        {'cluster_name': 'cluster_name', 'vm_name': 'vm_name', 'iface': 'iface', 'disk_id': 'disk_id', 'devices': ['disk_id']}
 *        不同的指标需要的参数如下:
 *        vmcpu ['vmcpu'] : {'vm_name': 'vm_name', 'cluster_name':'cluster_name'}
 *        vmmem ['vmmem'] : {'vm_name': 'vm_name', 'cluster_name':'cluster_name'}
 *        vmnet ['vminterflow', 'vmintraflow', 'vminterdrop', 'vmintradrop']: {'cluster_name': 'cluster_name', 'iface': 'iface'} 
 *        vmio  ['vmiorwflow', 'vmiorwtimes', 'vmioutil']: {'cluster_name': 'cluster_name', 'devices': ['disk_id']}
 *        vmdevice: ['deviceiorwflow', 'deviceiorwtimes', 'deviceiorwpercent','deviceiorwlatency','deviceiorwBlock','deviceiorwLatencyLayout']: {'cluster_name': 'cluster_name', 'disk_id': 'disk_id'}        
 * @param indicator 指定的性能指标 
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawVmSingleIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	switch (indicator) {
	    case  'vmcpu' :
	    	drawVmCpuOrMemIndicator(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick);
	        break;
	    case  'vmmem' :
	    	drawVmCpuOrMemIndicator(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick);
	        break;
	    case  'vminterflow':
	    case  'vmintraflow':
	    case  'vminterdrop':
	    case  'vmintradrop':
	    	drawVmNetIndicator(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick);
	    	break;
	    case  'vmiorwflow':
	    case  'vmiorwtimes':
	    case  'vmioutil':
	    	drawVmIoIndicator(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick);
	    	break;
	    case  'deviceiorwflow':
	    case  'deviceiorwtimes':
//	    case  'deviceioutil':
	    	drawVmDeviceSingleIndicator(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick);
	    	break;
	    default: 
	    	break;
	}
};

/**
 * 绘制指定时间范围内 VM cpu性能指标的曲线图
 * @param vmEntity vm实体参数, 结构为 {'vm_name': 'vm_name' }         
 * @param indicator 指定的性能指标 
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawVmCpuOrMemIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([vmEntity, vmEntity.vm_name, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	
	var vm_name = vmEntity.vm_name;
	var cluster_name = vmEntity.cluster_name;
	var content = {
		'vm_name': vm_name,
		'cluster_name': cluster_name,
		'time_range': timeRange
	};
	
	var httpUrl = httpPrefix + '/controllers/vmMonitor/vmCpuMem';
	var params = {
		'vm_name': vm_name,
		'cluster_name': cluster_name,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1]
	};
	
	var requestOptions = {
		url: httpUrl,
		params: params
	};
	
	var chartDivId = createAndAppendChartDiv('VM', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartData = data.result.data;
		generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
	};
	
	var failTodo = function(){};
	doAjaxRequest(requestOptions, succTodo, failTodo);
};


/**
 * 绘制指定时间范围内 VM 单个网络性能指标的曲线图
 * @param vmEntity vm实体参数, 结构为 {'cluster_name': 'cluster_name', 'iface': 'iface'}
 * @param indicator 指定的网络性能指标 
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawVmNetIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([vmEntity, vmEntity.cluster_name, vmEntity.iface, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	var cluster_name = vmEntity.cluster_name;
	var iface = vmEntity.iface;
	
	var content = {
		'cluster_name': cluster_name,
	    'iface': iface,	
	    'time_range': timeRange
	};
	
	var httpUrl = '';
	if (indicator == 'vminterflow' || indicator == 'vmintraflow') {
		httpUrl = httpPrefix + '/controllers/vmMonitor/vmNet/flow';
	}
	else if (indicator == 'vminterdrop' || indicator == 'vmintradrop') {
		httpUrl = httpPrefix + '/controllers/vmMonitor/vmNet/package';
	}
	var params = {
		'cluster_name': cluster_name,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1],
		'iface': iface
	};
	
	var requestOptions = {
		url: httpUrl,
		params: params
	};
	
	var chartDivId = createAndAppendChartDiv('VM', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartData = data.result.data;
		generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
	};
	
	var failTodo = function(){};
	doAjaxRequest(requestOptions, succTodo, failTodo);
};

/**
 * 绘制指定时间范围内 VM 单个IO性能指标汇总的曲线图
 * @param vmEntity vm实体参数, 结构为 {'cluster_name': 'cluster_name', 'devices': ['disk_id']}
 * @param indicator 指定的性能指标 
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawVmIoIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	if(!checkParams([vmEntity, vmEntity.devices, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	
	var cluster_name = vmEntity.cluster_name;
	var devices = vmEntity.devices;
	var content = {
		'devices': devices,
	    'cluster_name': cluster_name,
	    'time_range': timeRange
	};
	
	var httpUrl = httpPrefix + '/controllers/vmMonitor/vmIoGather';
	var params = {
		'cluster_name': cluster_name,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1],
		'devices': JSON.stringify(devices)
	};
	
	var requestOptions = {
		url : httpUrl,
		params: params
	};
	var fieldFuncMapping = {
		'timestamp': firstFunc,
		'rkBs': sumFunc, 'wkBs': sumFunc, 'rs': sumFunc, 'ws': sumFunc , 'util': averageFunc
	};
	var chartDivId = createAndAppendChartDiv('VM', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartDataArray = data.result.data;
		var resultChartData = summary(chartDataArray, fieldFuncMapping);
		resultChartData = convertDate(resultChartData, 'timestamp');
		generateIndicatorCharts(chartDivId, resultChartData, indicator, content, allowClick);
	};
	var failTodo = function() {};
	doAjaxRequest(requestOptions, succTodo, failTodo);	
};

var drawVmDeviceIndicators = function(vmEntity, timeRange, parentRegionDivId, titleText) {
	drawVmDeviceSpecIndicators(vmEntity, deviceAllIndicators, timeRange, parentRegionDivId, titleText);
};

/**
 * 绘制指定时间范围内 DEVICE 指定的多个性能指标的曲线图
 * @param deviceEntity vm实体参数, 结构为 {'cluster_name': 'cluster_name', 'disk_id': 'disk_id'}
 * @param specIndicators 指定的多个性能指标, 结果为指标名称的数组 eg. ['deviceiorwflow', 'deviceiorwtimes']
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawVmDeviceSpecIndicators = function(vmEntity, specIndicators, timeRange, parentRegionDivId, titleText) {
	var i=0, indicatorNum = (specIndicators == null ? 0 : specIndicators.length);
	for (i=0; i < indicatorNum; i++) {
		sleep(100);
		drawVmDeviceSingleIndicator(vmEntity, specIndicators[i], timeRange, parentRegionDivId, titleText);
	}
};


var sleep=function(n){
	for(var i=0;i<n*1000;++i){
		//nothing
	}
};

/**
 * 绘制Device单个指标的时间趋势图
 * @param deviceEntity device实体参数, 结构为 {'disk_id': 'disk_id', 'cluster_name': 'cluster_name'}
 * @param indicator 指定的性能指标
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 * 
 */
var drawVmDeviceSingleIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([vmEntity, vmEntity.cluster_name, vmEntity.disk_id, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	var cluster_name = vmEntity.cluster_name;
	var disk_id = vmEntity.disk_id;
	
	var content = {
	    'cluster_name': cluster_name,
	    'disk_id': disk_id,
	    'time_range': timeRange
	};
	
	var httpUrl = httpPrefix + '/controllers/vmMonitor/io/iostat';
	switch(indicator) {
		case 'deviceiorwBlockReadLayout':
		case 'deviceiorwBlockWriteLayout':
			httpUrl = httpPrefix + '/controllers/vmMonitor/io/ioblock';
			break;
		case 'deviceiorwLatencyReadLayout':
		case 'deviceiorwLatencyWriteLayout':
			httpUrl = httpPrefix + '/controllers/vmMonitor/io/latency';
			break;
		default:
			httpUrl = httpPrefix + '/controllers/vmMonitor/io/iostat';
			break;
	}
	var params = {
		'cluster_name': cluster_name,
        'disk_id': disk_id,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1],
	};
	
	var requestOptions = {
		url: httpUrl,
		params: params
	};
	
	var chartDivId = createAndAppendChartDiv('DEVICE', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartData = data.result.data;
		generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
	};
	
	var failTodo = function(){};
	
	doAjaxRequest(requestOptions, succTodo, failTodo);
};

var drawVmDeviceLatencyIndicator = function(vmEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([vmEntity, vmEntity.cluster_name, vmEntity.disk_id, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	var cluster_name = vmEntity.cluster_name;
	var disk_id = vmEntity.disk_id;
	
	var content = {
	    'cluster_name': cluster_name,
	    'disk_id': disk_id,
	    'time_range': timeRange
	};
	
	var httpUrl = httpPrefix + '/controllers/vmMonitor/vmIoConsume';
	var params = {
		'cluster_name': cluster_name,
        'disk_id': disk_id,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1],
	};
	
	var requestOptions = {
		url: httpUrl,
		params: params
	};
	
	var chartDivId = createAndAppendChartDiv('DEVICE', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartData = data.result.data;
		generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
	};
	
	var failTodo = function(){};
	
	doAjaxRequest(requestOptions, succTodo, failTodo);
};

/**
 * 绘制指定时间范围内 NC 所有性能指标的曲线图
 * @param ncEntity nc实体参数, 结构为 {'host': 'nc_ip', 'cluster_name': 'cluster_name'}
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawNcIndicators = function(ncEntity, timeRange, parentRegionDivId, titleText) {
	var indicatorGroup = '', indicators = [];
	var indicatorGroupRegionDivId = '';
	
	for (indicatorGroup in ncAllIndicators) {
		indicatorGroupRegionDivId = indicatorGroup + '-' + unrepeatedNumber(); 
		
		var indicatorGroupRegionDiv = '<div id="' + indicatorGroupRegionDivId + '"><p><strong>' + indicatorGroup + ' 指标 :  </strong> ';
		if (indicatorGroup == 'IO') {
			indicatorGroupRegionDiv += ' <button type="button" value="0" class="showAllDiskInfos"> 展示所有磁盘详情 </button>';
		}
		indicatorGroupRegionDiv += '</p></div><div class="clear"></div>';
		$('#'+parentRegionDivId).append(indicatorGroupRegionDiv);
		indicators = ncAllIndicators[indicatorGroup];
		$('.showAllDiskInfos').click(function() {
			var thisButton = $(this);
			if (thisButton.attr('value') == '0') {
				thisButton.attr('value', '1');
				thisButton.text('收起所有磁盘详情');
				var ncEntityAnother = { 'host': ncEntity.host, 'cluster_name': ncEntity.cluster_name };
				$('#'+indicatorGroupRegionDivId).append('<div id="allDiskInfos"></div');
				drawNcSpecIndicators(ncEntityAnother, indicators, timeRange, 'allDiskInfos', titleText);
			}
			else {
				thisButton.attr('value', '0');
				thisButton.text('展开所有磁盘详情');
				$('#allDiskInfos').remove();
			}
		});
		drawNcSpecIndicators(ncEntity, indicators, timeRange, indicatorGroupRegionDivId, titleText);
	}
};

/**
 * 绘制指定时间范围内 NC 指定的多个性能指标的曲线图
 * @param ncEntity nc实体参数, 结构为 {'host': 'nc_ip', 'cluster_name': 'cluster_name'}
 * @param specIndicators 指定的多个性能指标, 结果为指标名称的数组 eg. ['nccpu', 'ncload']
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawNcSpecIndicators = function(ncEntity, specIndicators, timeRange, parentRegionDivId, titleText) {
	var i=0, indicatorNum = (specIndicators == null ? 0 : specIndicators.length);
	for (i=0; i < indicatorNum; i++) {
		drawNcSingleIndicator(ncEntity, specIndicators[i], timeRange, parentRegionDivId, titleText);
	}
};

/**
 * 绘制指定时间范围内 NC 单个性能指标的曲线图
 * @param ncEntity nc实体参数, 结构为 {'host': 'nc_ip', 'cluster_name': 'cluster_name'}
 * @param indicator 指定的性能指标
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawNcSingleIndicator = function(ncEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([ncEntity, ncEntity.host, ncEntity.cluster_name, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	
	var host = ncEntity.host;
	var cluster_name = ncEntity.cluster_name;
	var cpu_no = ncEntity.cpu_no;
	var disk_no = ncEntity.disk_no;
	
	var content = {
		'host':host, 
		'cluster_name':cluster_name,
		'time_range': timeRange
	};
	var httpUrl = '';
	var params = {
		'host': host,
		'cluster_name': cluster_name,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1]
	};
	
	if (cpu_no != null) {
		params.cpu_no = cpu_no;
	}
	
	if (disk_no != null) {
		params.disk_no = disk_no;
	}
	
	switch (indicator) {
	    case 'nccpu':     // cpu利用率
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncCpu';
	    	break;
	    case 'ncload':    // load
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncLoad';
	    	break;
	    case 'ncflow':     // 网络流量
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncNet/flow';
	    	break;	
	    case 'ncpps':
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncNet/flow';
	    	break;
	    case 'ncconn':
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncNet/flow';
	    	break;
	    case 'ncpackage':
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncNet/package';
	    	break;	
	    case 'nciorwflow':    // IO读写
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncIo';
	    	break;	
	    case 'nciorwtimes':    // IO读写
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncIo';
	    	break;	
	    case 'ncioutil':  // ioutil
	    	httpUrl = httpPrefix + '/controllers/ncMonitor/ncIo';
	    	break;	
	    default:
	    	break;
	}
	
	var requestOptions = {
		url: httpUrl,
		params: params
	};
	
	
	/* NC 绘图逻辑有点复杂, 这里说明一下:
	 * cpu_no == null : 绘制所有 CPU 图表, 包括 cpu = all 和 cpu = 核编号 的所有图表
	 * cpu_no == all / 核编号 :  绘制 cpu = all / 核编号 的单个图表
	 * disk_no == null : 绘制 disk 所有 disk 的图表
	 * disk_no = all : 绘制 disk 汇总后的单个图表
	 * disk_no = disk_no: 绘制 disk_no 的单个图表
	 * 
	 */
	var chartDivId = '';
	var ncioIndicatorCond = (indicator == 'nciorwflow' || indicator == 'nciorwtimes' || indicator == 'ncioutil');
	var notCreateDivForNcCpuCond = (indicator == 'nccpu' && cpu_no == null);
	var notCreateDivForNcIoCond = (ncioIndicatorCond && disk_no == null);
	var shouldCreateDivCond = (indicator == 'ncload' || indicator == 'ncflow' 
		                        || indicator == 'ncpackage' || indicator == 'ncpps' || indicator == 'ncconn'
		                        || (indicator == 'nccpu' && cpu_no != null) || (ncioIndicatorCond && disk_no != null));
	if (shouldCreateDivCond) {
		chartDivId = createAndAppendChartDiv('NC', indicator, 'chartdiv', parentRegionDivId, titleText);
	}
	
	var succTodo = function(data) {
		
		var chartData = data.result.data;
		if (notCreateDivForNcCpuCond) {
			var chartDataArray = groupByField(chartData, "cpu");
			for (var i=0; i<chartDataArray.length; i++) {
				titleText = 'CPU: ' + chartDataArray[i].cpu;
				var varcontent = {
					'host': host, 
					'cluster_name': cluster_name,
					'time_range': timeRange
				};
				chartDivId = createAndAppendChartDiv('NC', indicator, 'chartdiv', parentRegionDivId, titleText);
				varcontent.cpu_no = chartDataArray[i].cpu;
				generateIndicatorCharts(chartDivId, chartDataArray[i].data, indicator, varcontent, allowClick);
			}
		}
		else if (notCreateDivForNcIoCond) {
			var chartDataArray = groupByField(chartData, "disk");
			for (var i=0; i<chartDataArray.length; i++) {
				titleText = 'DISK: ' + chartDataArray[i].disk;
				var varcontent = {
					'host':host, 
					'cluster_name':cluster_name,
					'time_range': timeRange
				};
				chartDivId = createAndAppendChartDiv('NC', indicator, 'chartdiv', parentRegionDivId, titleText);
				varcontent.disk_no = chartDataArray[i].disk;
				generateIndicatorCharts(chartDivId, chartDataArray[i].data, indicator, varcontent, allowClick);
			}
		}
		else if (ncioIndicatorCond && disk_no == 'all') {
			var chartDataArray = groupByField(chartData, "disk");
			var ncDiskDataArray = [];
			for (var i=0; i<chartDataArray.length; i++) {
				ncDiskDataArray.push(chartDataArray[i].data);
			}
			var fieldFuncMapping = {
				'timestamp': firstFunc,
				'rkBs': sumFunc, 'wkBs': sumFunc, 'rs': sumFunc, 'ws': sumFunc , 'util': averageFunc
			};
			var resultChartData = summary(ncDiskDataArray, fieldFuncMapping);
			var varcontent = {
				'host':host, 
				'cluster_name':cluster_name,
				'time_range': timeRange
			};
			varcontent.disk_no = 'all';
			generateIndicatorCharts(chartDivId, resultChartData, indicator, varcontent, allowClick);
		}
		else if (indicator == 'nccpu' && cpu_no == 'all') {
			var varcontent = {
				'host':host, 
				'cluster_name':cluster_name,
				'time_range': timeRange
			};
			varcontent.cpu_no = 'all';
			generateIndicatorCharts(chartDivId, chartData, indicator, varcontent, allowClick);
		}
		else {
			generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
		}
			
	};
	
	var failTodo = function(){};
	doAjaxRequest(requestOptions, succTodo, failTodo);
};


/**
 * 绘制指定时间范围内 Vip 所有性能指标的曲线图
 * @param vipEntity {'vip': 'xxx', 'slbdb_configId': 'xxx'}
 * @param timeRange 时间范围 结构为 [beginTimeStamp, endTimeStamp] (精确到秒)
 * @param parentRegionDivId     绘图区域的父区域DIVID
 * @param titleText 可选的绘图标题文本
 */
var drawVipIndicators = function(vipEntity, timeRange, parentRegionDivId, titleText) {
	drawVipSpecIndicators(vipEntity, vipAllIndicators, timeRange, parentRegionDivId, titleText);
};

/**
 * 绘制VIP多个指标的时间趋势图
 * @param vipEntity {'vip': 'xxx', 'slbdb_configId': 'xxx'}
 * @param indicators 指标名称数组
 * @param timeRange 时间范围
 * @param parentRegionDivId 绘图区域的父区域 divid
 * @param titleText 可选的绘图标题文本
 */
var drawVipSpecIndicators = function(vipEntity, specIndicators, timeRange, parentRegionDivId, titleText) {
	var i=0, indicatorNum = (specIndicators == null ? 0 : specIndicators.length);
	for (i=0; i < indicatorNum; i++) {
		drawVipSingleIndicator(vipEntity, specIndicators[i], timeRange, parentRegionDivId, titleText);
	}
};

/**
 * 绘制VIP单个指标的时间趋势图
 * @param vipEntity {'vip': 'xxx', 'slbdb_configId': 'xxx'}
 * @param indicator 指标名称
 * @param timeRange 时间范围
 * @param parentRegionDivId 绘图区域的父区域 divid
 * @param titleText 可选的绘图标题文本
 * @param allowClick 是否允许点击放大行为, 可选, 若不填则默认为true
 */
var drawVipSingleIndicator = function(vipEntity, indicator, timeRange, parentRegionDivId, titleText, allowClick) {
	
	if(!checkParams([vipEntity, vipEntity.vip, vipEntity.slbdb_configId, indicator, timeRange[0], timeRange[1], parentRegionDivId])) {
		return ;
	}
	
	var vip = vipEntity.vip;
	var configId = vipEntity.slbdb_configId; 
	var content = {
		'vip': vip,
		'slbdb_configId': configId,
		'time_range': timeRange
	};
	
	var httpUrl = httpPrefix + '/controllers/slbvip/obtainVipStat';
	var params = {
		'vip': vip,
		'slbdb_configId': configId,
		'stat_type': indicator,
		'begin_time':  timeRange[0],
		'end_time': timeRange[1]
	};
	
	var requestOptions = {
		url: httpUrl,
		params: params,
		timeout: 90000
	};
	
	var chartDivId = createAndAppendChartDiv('VIP', indicator, 'chartdiv', parentRegionDivId, titleText);
	
	var succTodo = function(data) {
		var chartData = data.result.data;
		generateIndicatorCharts(chartDivId, chartData, indicator, content, allowClick);
	};
	
	var failTodo = function(){};
	doAjaxRequest(requestOptions, succTodo, failTodo);
	
};

var COLOR_CYAN = '#01DFD7';
var COLOR_GREEN = '#00ff00';
var COLOR_THIN_GREEN = '#7fb80e';
var COLOR_BLUE = '#0000ff';
var COLOR_YELLOW = '#ffe600';
var COLOR_RED = '#d71345';
var COLOR_ORANGE = '#f47920';

var latencyReadArray=['r0_1','r1_2','r2_5','r5_10','r10_15','r15_20','r20_30','r30_40','r40_50','r50_100','r100_200','r200_500','r500_1000','r1000_2000','r2000_5000','r50000_10000','r10000_'];
var latencyWriteArray=['w0_1','w1_2','w2_5','w5_10','w10_15','w15_20','w20_30','w30_40','w40_50','w50_100','w100_200','w200_500','w500_1000','w1000_2000','w2000_5000','w50000_10000','w10000_'];
var ioblockReadArray=['r0_4','r4_8','r8_16','r16_32','r32_64','r64_128','r128_256','r256_512','r512_1024','r1024_2048','r2048_'];
var ioblockWriteArray=['w0_4','w4_8','w8_16','w16_32','w32_64','w64_128','w128_256','w256_512','w512_1024','w1024_2048','w2048_'];

var yTipFormat = function(points, tipText) {
	$.each(points, function(i, point) {
		tipText += '<br/>'+ point.series.name +': '+ point.y  + '% (' + point.point.tip + ')';
	});
	return tipText;
};
var yPercentTipFormat = function(points, tipText) {
	$.each(points, function(i, point) {
		tipText += '<br/>'+ point.series.name +': '+ point.y  + '%';
	});
	return tipText;
};
var latencyReadValueFormat = function (chartData, valueField) {
	return yValueFormat(chartData,valueField,latencyReadArray);
};
var latencyWriteValueFormat = function (chartData, valueField) {
	return yValueFormat(chartData,valueField,latencyWriteArray);
};
var ioblockReadValueFormat = function (chartData, valueField) {
	return yValueFormat(chartData,valueField,ioblockReadArray);
};
var ioblockWriteValueFormat = function (chartData, valueField) {
	return yValueFormat(chartData,valueField,ioblockWriteArray);
};

var yValueFormat = function (chartData, valueField,fieldArray) {
	var valueData = [], value = 0;
	var i=0, num = (chartData == null ? 0 : chartData.length);
	var point = {};
	var tagArray = [];
	var markerSetting = { enabled: true, radius: 2};

	for (i=0; i<num; i++) {
		point = chartData[i];
		var total = 0;
		$.each(fieldArray, function(i, field) {
			total += point[field];
		});
		tagArray = point.tag;
		value = parseFloat((point[valueField]*100/total).toFixed(2));
		tip = parseFloat(point[valueField]);
		if (isInArray(valueField, tagArray)) {
			valueData.push({x: chartData[i].timestamp, y: value, "tip" : tip,color: '#ff0000', marker: markerSetting});
		}
		else {
			valueData.push({x: chartData[i].timestamp, y: value, "tip" : tip});
		}
	}
	return valueData;
};

var generateIndicatorCharts = function(chartDivId, chartData, indicator, content, allowClick) {
	
	// 确保按照时间升序排列, hicharts-api requires 
	insertSort(chartData, 'timestamp');
	
	switch(indicator) {
	    case 'vmcpu':
		    generateChart2(chartDivId, chartData, 
	        		{'title': 'CPU使用率(%)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
	        		[{'title':'cpu', 'valueField': 'cpu_util', 'lineColor': COLOR_GREEN}]);
		    break;
	    case 'vmmem':
		    generateChart2(chartDivId, chartData, 
	        		{'title': 'MEM使用率(%)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
	        		[{'title':'mem', 'valueField': 'mem_util', 'lineColor': COLOR_GREEN}]);
		    break;
		 
		// vm net relevant
	    case 'vminterflow':
			generateChart2(chartDivId, chartData, 
               		{'title': '公网进出流量(b/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
               		[{'title':'入流量', 'valueField': 'txbit_s', 'lineColor': COLOR_GREEN},
               		 {'title':'出流量', 'valueField': 'rxbit_s', 'lineColor': COLOR_BLUE}]);
	    	break;
	    case 'vmintraflow':
 			generateChart2(chartDivId, chartData, 
                 		{'title': '私网进出流量(b/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
                 		[{'title': '入流量', 'valueField': 'txbit_s', 'lineColor': COLOR_GREEN},
                 		 {'title': '出流量', 'valueField': 'rxbit_s', 'lineColor': COLOR_BLUE}]);
	    	break;
	    case 'vminterdrop':
			generateChart2(chartDivId, chartData, 
               		{'title': '公网进出丢包(次数/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
               		[{'title':'出丢包数', 'valueField': 'rxdrop', 'lineColor': COLOR_GREEN},
               		 {'title':'进丢包数', 'valueField': 'txdrop', 'lineColor': COLOR_BLUE}]);
	    	break;
	    case 'vmintradrop':
 			generateChart2(chartDivId, chartData, 
                 		{'title': '私网进出丢包(次数/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
                 		[{'title': '出丢包数', 'valueField': 'rxdrop', 'lineColor': COLOR_GREEN},
                 		 {'title': '进丢包数', 'valueField': 'txdrop', 'lineColor': COLOR_BLUE}]);
	    	break;		
	    
	    // vm io-total relevant
		case 'vmiorwflow':
		    generateChart2(chartDivId, chartData, 
		   		{'title': 'io读写 流量(kB/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
		   		[{'title': '每秒读流量', 'valueField': 'rkBs', 'lineColor': COLOR_GREEN},
		   		 {'title': '每秒写流量', 'valueField': 'wkBs', 'lineColor': COLOR_BLUE}]);
		    break;
		case 'vmiorwtimes':
		    generateChart2(chartDivId, chartData, 
		   		{'title': 'io读写 次数(次数/s)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
		   		[{'title': '每秒读次数', 'valueField': 'rs', 'lineColor': COLOR_GREEN},
		   		 {'title': '每秒写次数', 'valueField': 'ws', 'lineColor': COLOR_BLUE}]);
		    break;
		case 'vmioutil':
		    generateChart2(chartDivId, chartData, 
		  		{'title': 'io util (%)', 'indicator': indicator, 'type': 'VM', 'content': content, 'allowClick': allowClick}, 
		  		[{'title': 'ioutil', 'valueField': 'util', 'lineColor': COLOR_GREEN}]);
			break;	
	
	    // vm device relevant
		case 'deviceiorwflow':
		    generateChart2(chartDivId, chartData, 
		   		{'title': 'io读写流量(Byte/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
		   		[{'title': '读流量', 'valueField': 'io_bytes_r', 'lineColor': COLOR_GREEN},
		   		 {'title': '写流量', 'valueField': 'io_bytes_w', 'lineColor': COLOR_BLUE},
		   		 {'title': '读+写流量', 'valueField': 'io_bytes_wr', 'lineColor': COLOR_CYAN},
		   		]);
		    break;
		case 'deviceiorwpercent':
			generateChart2(chartDivId, chartData, 
					{'title': 'io读写比例(%)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick
				 	 ,'yTipFormat':yPercentTipFormat}, 
					[{'title': '读比例', 'valueField': 'r_count_p', 'lineColor': COLOR_GREEN},
					 {'title': '写比例', 'valueField': 'w_count_p', 'lineColor': COLOR_BLUE}]);
			break;
		case 'deviceiorwtimes':
		    generateChart2(chartDivId, chartData, 
		   		{'title': 'io读写次数(次/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
		   		[{'title': '读次数', 'valueField': 'io_count_r', 'lineColor': COLOR_GREEN},
		   		 {'title': '写次数', 'valueField': 'io_count_w', 'lineColor': COLOR_BLUE},
		   		 {'title': '读+写次数', 'valueField': 'io_count_wr', 'lineColor': COLOR_CYAN}]);
		    break;
		case 'deviceiorwtimesMearge':
			generateChart2(chartDivId, chartData, 
					{'title': 'io读写次数(Merge)(次/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
					[{'title': '读次数', 'valueField': 'io_count_merge_r', 'lineColor': COLOR_GREEN},
					 {'title': '写次数', 'valueField': 'io_count_merge_w', 'lineColor': COLOR_BLUE},
					 {'title': '读+写次数', 'valueField': 'io_count_merge_wr', 'lineColor': COLOR_CYAN}]);
			break;
		case 'deviceiorwlatency':
			generateChart2(chartDivId, chartData, 
					{'title': 'io平均延时(ms/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
					[{'title': '读延时', 'valueField': 'io_latency_r', 'lineColor': COLOR_GREEN},
					 {'title': '写延时', 'valueField': 'io_latency_w', 'lineColor': COLOR_BLUE},
					 {'title': '平均延时', 'valueField': 'io_latency_wr', 'lineColor': COLOR_CYAN}]);
			break;
		case 'deviceiorwBlock':
			generateChart2(chartDivId, chartData, 
					{'title': 'io平均块大小(Byte/个)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
					[{'title': '读块大小', 'valueField': 'io_block_avg_r', 'lineColor': COLOR_GREEN},
					 {'title': '写块大小', 'valueField': 'io_block_avg_w', 'lineColor': COLOR_BLUE},
					 {'title': '平均块大小', 'valueField': 'io_block_avg_wr', 'lineColor': COLOR_CYAN}]);
			break;
		case 'flushIoCount':
			generateChart2(chartDivId, chartData, 
					{'title': 'IO flush数目(个/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
					[{'title': 'with_data', 'valueField': 'flush_io_count_with_data', 'lineColor': COLOR_GREEN},
					 {'title': 'without_data', 'valueField': 'flush_io_count_without_data', 'lineColor': COLOR_BLUE},
					 {'title': 'total', 'valueField': 'flush_io_count_total', 'lineColor': COLOR_CYAN}]);
			break;
		case 'ioOverlayedCount':
			generateChart2(chartDivId, chartData, 
					{'title': 'overlay重复个数(个/s)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
					[{'title': '读重复', 'valueField': 'io_overlayed_count_r', 'lineColor': COLOR_GREEN},
					 {'title': '写重复', 'valueField': 'io_overlayed_count_w', 'lineColor': COLOR_BLUE},
					 {'title': '读+写重复', 'valueField': 'io_overlayed_count_wr', 'lineColor': COLOR_CYAN}]);
			break;
		case 'deviceiorwLatencyReadLayout':
			generateChart2(chartDivId, chartData, 
					{'title': 'IO读延时分布(%)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick,
					 'yTipFormat': yTipFormat,'yValueFormat':latencyReadValueFormat}, 
					[
					 {'title': 'r0_1', 'valueField': 'r0_1'},
					 {'title': 'r1_2', 'valueField': 'r1_2'},
					 {'title': 'r2_5', 'valueField': 'r2_5'},
					 {'title': 'r5_10', 'valueField': 'r5_10'},
					 {'title': 'r10_15', 'valueField': 'r10_15'},
					 {'title': 'r15_20', 'valueField': 'r15_20'},
					 {'title': 'r20_30', 'valueField': 'r20_30'},
					 {'title': 'r30_40', 'valueField': 'r30_40'},
					 {'title': 'r40_50', 'valueField': 'r40_50'},
					 {'title': 'r50_100', 'valueField': 'r50_100'},
					 {'title': 'r100_200', 'valueField': 'r100_200'},
					 {'title': 'r200_500', 'valueField': 'r200_500'},
					 {'title': 'r500_1000', 'valueField': 'r500_1000'},
					 {'title': 'r1000_2000', 'valueField': 'r1000_2000'},
					 {'title': 'r200_500', 'valueField': 'r200_500'},
					 {'title': 'r50000_10000', 'valueField': 'r50000_10000'},
					 {'title': 'r10000_', 'valueField': 'r10000_'},
					 ]);
			break;
		case 'deviceiorwLatencyWriteLayout':
			generateChart2(chartDivId, chartData, 
					{'title': 'IO写延时分布(%)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick, 
					'yTipFormat': yTipFormat,'yValueFormat':latencyWriteValueFormat},
					[
					 {'title': 'w0_1', 'valueField': 'w0_1'},
					 {'title': 'w1_2', 'valueField': 'w1_2'},
					 {'title': 'w2_5', 'valueField': 'w2_5'},
					 {'title': 'w5_10', 'valueField': 'w5_10'},
					 {'title': 'w10_15', 'valueField': 'w10_15'},
					 {'title': 'w15_20', 'valueField': 'w15_20'},
					 {'title': 'w20_30', 'valueField': 'w20_30'},
					 {'title': 'w30_40', 'valueField': 'w30_40'},
					 {'title': 'w40_50', 'valueField': 'w40_50'},
					 {'title': 'w50_100', 'valueField': 'w50_100'},
					 {'title': 'w100_200', 'valueField': 'w100_200'},
					 {'title': 'w200_500', 'valueField': 'w200_500'},
					 {'title': 'w500_1000', 'valueField': 'w500_1000'},
					 {'title': 'w1000_2000', 'valueField': 'w1000_2000'},
					 {'title': 'w200_500', 'valueField': 'w200_500'},
					 {'title': 'w50000_10000', 'valueField': 'w50000_10000'},
					 {'title': 'w10000_', 'valueField': 'w10000_'},
					 ]);
			break;
		case 'deviceiorwBlockReadLayout':
			generateChart2(chartDivId, chartData, 
					{'title': 'IO读块大小分布(%) ', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick,
				    'yTipFormat': yTipFormat,'yValueFormat':ioblockReadValueFormat}, 
					[
					 {'title': 'r0_4', 'valueField': 'r0_4'},
					 {'title': 'r4_8', 'valueField': 'r4_8'},
					 {'title': 'r8_16', 'valueField': 'r8_16'},
					 {'title': 'r16_32', 'valueField': 'r16_32'},
					 {'title': 'r32_64', 'valueField': 'r32_64'},
					 {'title': 'r64_128', 'valueField': 'r64_128'},
					 {'title': 'r128_256', 'valueField': 'r128_256'},
					 {'title': 'r256_512', 'valueField': 'r256_512'},
					 {'title': 'r512_1024', 'valueField': 'r512_1024'},
					 {'title': 'r1024_2048', 'valueField': 'r1024_2048'},
					 {'title': 'r2048_', 'valueField': 'r2048_'}
					 ]);
			break;
		case 'deviceiorwBlockWriteLayout':
			generateChart2(chartDivId, chartData, 
					{'title': 'IO写块大小分布(%) ', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick,
				    'yTipFormat': yTipFormat,'yValueFormat':ioblockWriteValueFormat}, 
					[
					 {'title': 'w0_4', 'valueField': 'w0_4'},
					 {'title': 'w4_8', 'valueField': 'w4_8'},
					 {'title': 'w8_16', 'valueField': 'w8_16'},
					 {'title': 'w16_32', 'valueField': 'w16_32'},
					 {'title': 'w32_64', 'valueField': 'w32_64'},
					 {'title': 'w64_128', 'valueField': 'w64_128'},
					 {'title': 'w128_256', 'valueField': 'w128_256'},
					 {'title': 'w256_512', 'valueField': 'w256_512'},
					 {'title': 'w512_1024', 'valueField': 'w512_1024'},
					 {'title': 'w1024_2048', 'valueField': 'w1024_2048'},
					 {'title': 'w2048_', 'valueField': 'w2048_'}
					 ]);
			break;
//		case 'deviceioutil':
//		    generateChart2(chartDivId, chartData, 
//		  		{'title': 'io util (%)', 'indicator': indicator, 'type': 'DEVICE', 'content': content, 'allowClick': allowClick}, 
//		  		[{'title': 'ioutil', 'valueField': 'util', 'lineColor': COLOR_GREEN}]);
//			break;
		case 'devicelatency':
		    generateChart2(chartDivId, chartData, 
		  		{'title': 'latency (ms)', 'indicator': indicator, 'type': 'DEVICE', 'content': content}, 
		  		[{'title': 'latency', 'valueField': 'avg', 'lineColor': COLOR_GREEN}]);
			break;	
			
		// nc relevant
		case  'nccpu':
			generateChart2(chartDivId, chartData, 
                		{'title': 'CPU使用率(%)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
                		[{'title':'user', 'valueField': 'usr', 'lineColor': COLOR_GREEN},
                		 {'title':'sys', 'valueField': 'sys', 'lineColor': COLOR_BLUE},
                		 {'title':'idle', 'valueField': 'idle', 'lineColor': COLOR_YELLOW},
                		 {'title':'iowait', 'valueField': 'iowait', 'lineColor': COLOR_CYAN}]);
	    	break;
	    case 'ncload':
			generateChart2(chartDivId, chartData, 
               		{'title': 'Load', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
               		[{'title':'load_1', 'valueField': 'ldavg1', 'lineColor': COLOR_GREEN},
               		 {'title':'load_5', 'valueField': 'ldavg5', 'lineColor': COLOR_BLUE},
               		 {'title':'load_15', 'valueField': 'ldavg15', 'lineColor': COLOR_YELLOW}]);
	    	break;
	    case 'ncflow':
 			generateChart2(chartDivId, chartData, 
                 		{'title': '网络流量 (b/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
                 		[{'title': '进流量', 'valueField': 'rxbit_s', 'lineColor': COLOR_GREEN},
                 		 {'title': '出流量', 'valueField': 'txbit_s', 'lineColor': COLOR_BLUE}]);
	    	break;	
	    case 'ncpackage':
	    	generateChart2(chartDivId, chartData, 
             		{'title': '网络丢包(次数/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
             		[{'title': '进丢包', 'valueField': 'rxdrop', 'lineColor': COLOR_GREEN},
             		 {'title': '出丢包', 'valueField': 'txdrop', 'lineColor': COLOR_BLUE}]);
    	    break;	
	    case 'ncpps':
	    	generateChart2(chartDivId, chartData, 
             		{'title': '网络PPS(包/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
             		[{'title': '进包数', 'valueField': 'rxpck', 'lineColor': COLOR_GREEN},
             		 {'title': '出包数', 'valueField': 'txpck', 'lineColor': COLOR_BLUE}]);
    	    break;	
	    case 'ncconn':
	    	generateChart2(chartDivId, chartData, 
             		{'title': '网络连接数(次/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
             		[{'title': '网络连接数', 'valueField': 'nf_conn', 'lineColor': COLOR_GREEN}]);
    	    break;	
	    case 'nciorwflow':
		    generateChart2(chartDivId, chartData, 
           		{'title': 'io读写流量(kB/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
           		[{'title': '每秒读流量', 'valueField': 'rkBs', 'lineColor': COLOR_GREEN},
           		 {'title': '每秒写流量', 'valueField': 'wkBs', 'lineColor': COLOR_BLUE}]);
		    break;
	    case 'nciorwtimes':
		    generateChart2(chartDivId, chartData, 
           		{'title': 'io读写次数(次数/s)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
           		[{'title': '每秒读次数', 'valueField': 'rs', 'lineColor': COLOR_GREEN},
           		 {'title': '每秒写次数', 'valueField': 'ws', 'lineColor': COLOR_BLUE}]);
		    break;
	    case 'ncioutil':	      
		    generateChart2(chartDivId, chartData, 
          		{'title': 'io util (%)', 'indicator': indicator, 'type': 'NC', 'content': content, 'allowClick': allowClick}, 
          		[{'title': 'ioutil', 'valueField': 'util', 'lineColor': COLOR_GREEN}]);
	    	break;	
	    	
	    // vip relevant
	    case 'conn':
			generateChart2(chartDivId, chartData, 
               		{'title': '活动与非活动连接数(个)', 'indicator': indicator, 'type': 'VIP', 'content': content, 'allowClick': allowClick}, 
               		[{'title':'active_conn', 'valueField': 'active_conn', 'lineColor': COLOR_GREEN},
               		 {'title':'inactive_conn', 'valueField': 'inactive_conn', 'lineColor': COLOR_BLUE}]);
	    	break;
	    case 'cps':
 			generateChart2(chartDivId, chartData, 
                 		{'title': '新建连接数 (个/s)', 'indicator': indicator, 'type': 'VIP', 'content': content, 'allowClick': allowClick}, 
                 		[{'title': 'cps', 'valueField': 'cps', 'lineColor': COLOR_GREEN}]);
	    	break;	
	    case 'bps':
		    generateChart2(chartDivId, chartData, 
           		{'title': '流量(kB/s)', 'indicator': indicator, 'type': 'VIP', 'content': content, 'allowClick': allowClick}, 
           		[{'title': '流量', 'valueField': 'bps', 'lineColor': COLOR_GREEN}]);
		    break;
	    case 'pps':	      
		    generateChart2(chartDivId, chartData, 
          		{'title': '包数量(个/s)', 'indicator': indicator, 'type': 'VIP', 'content': content, 'allowClick': allowClick}, 
          		[{'title': 'pps', 'valueField': 'pps', 'lineColor': COLOR_GREEN}]);
	    	break;		
		
	    default:
			break;
	}
};

/**
 * 检查 chartData 的时间戳是否按时间先后排序
 */
var checkTimeOrder = function(chartData) {
	var i = 0, num = (chartData == null ? 0 : chartData.length);
	var checkFlag = true;
	for (i=0; i<num-1; i++) {
		if (chartData[i].timestamp > chartData[i+1].timestamp) {
			checkFlag = false;
		}
	}
	return checkFlag;
};

var checkParams = function(paramArray) {
	
	if (paramArray == null || paramArray.length == null || paramArray.length == 0) {
		return false;
	}
	
	var i=0, len = paramArray.length;
	for (i=0; i < len; i++) {
		
		if (paramArray[i] == null) {
			return false;
		}
	}
	return true;
};

/**
 * 根据不同实体类型及指标创建相应的 DIV 绘图区域, 并添加到 parentRegionDivId 的 DIV 区域末尾.
 * @param className 所创建的 DIV 绘图区域的公用css类名; 若不指定 className, 则默认为 zoomedUpDivStyle
 * @param parentRegionDivId 所创建的 DIV 绘图区域的父区域
 * @param titleText 可选的绘图标题文本
 */
var createAndAppendChartDiv = function(type, indicator, className, parentRegionDivId, titleText) {
	
	var parentRegion = $('#'+parentRegionDivId);
	
	var divId = '', divClass = 'zoomedUpDivStyle', drawText = '';
	indicator = indicator.toLowerCase();
	divId = obtainDivIdForIndicator(indicator) + '-' + unrepeatedNumber();
	
	if (className != null) {
		divClass = className;
	}
	if (titleText != null) {
	    drawText = '<span class="drawTitleText">' + titleText + '</span>';
	}
	chartdivStr = '<div id="' + divId + '" class="' + divClass + '"></div>';
	singDrawRegionDivStr = '<div class="singleDrawRegion">' + drawText + chartdivStr + '</div>';
	parentRegion.append(singDrawRegionDivStr);
	
	return divId;
};

/**
 * 生成无重复随机数
 */
var unrepeatedNumber = function() {
	return (new Date()).getTime();
};

/**
 * 对多个chart数据的指定字段使用指定函数进行汇总
 * @param chartDataArray chart 数据数组, 结构: [[{'timestamp': 'xxx1', 'field1': 'xxx1', 'field2': 'yyy1'}], [{'timestamp': 'xxx1', 'field1': 'xxx2', 'field2': 'yyy2'}]]
 *        若长度不一致, 则取其中最小的 chart 长度
 * @param indicatorFunc 字段与聚合函数的映射, 结构: {'field1': func1, 'field2':func2 }
 * @return 单个 chart 数据, 结构 [{'timestamp': 'xxx1', 'field1': func1('xxx1', 'xxx2'), 'field2': func2('yyy1', 'yyy2')}]
 */
var summary = function(chartDataArray, fieldFuncMapping) {
	var i=0, k=0;
	var chartNum = (chartDataArray == null ? 0 : chartDataArray.length);
	var chartLength = minChartLengthFunc(chartDataArray);
	var result = [];
	var dataArrayForSinglePoint = [];
	var dataSummaryForSinglePoint = 0;
	var dataSummaryObjForSinglePoint = {};
	var singleData = 0;
	var dataItem = {};
	var dataItemProperty = {};
	var fieldSummaryFunc = null;
	if (chartNum == 0 || chartLength == 0) {
		return [];
	}
	dataItemProperty = chartDataArray[0][0];
	for (k=0; k < chartLength; k++) {
		dataSummaryObjForSinglePoint = {};
		for (var field in dataItemProperty) {
			dataArrayForSinglePoint = [];
			for (i=0; i<chartNum;i++) {
				dataItem = chartDataArray[i][k];
				singleData = dataItem[field];
				dataArrayForSinglePoint.push(singleData);
			}
			fieldSummaryFunc = fieldFuncMapping[field];
			if (fieldSummaryFunc != null) {
				dataSummaryForSinglePoint = fieldSummaryFunc(dataArrayForSinglePoint);
				dataSummaryObjForSinglePoint[field] = dataSummaryForSinglePoint;
			}
		}
		result.push(dataSummaryObjForSinglePoint);
	}
	return result;
};

var minChartLengthFunc = function(chartDataArray) {
	var i = 0, chartNum = (chartDataArray == null ? 0 : chartDataArray.length);
	var minChartLength = (chartDataArray[0] == null ? 0 : chartDataArray[0].length);
	for (i=1; i < chartNum; i++) {
		if (chartDataArray[i].length < minChartLength) {
			minChartLength = chartDataArray[i].length;
		}
	}
	return minChartLength;
};

var firstFunc = function(array) {
	return array[0];
};

var sumFunc = function(array) {
	var i=0, sum = 0;
	var len = (array == null ? 0 : array.length);
	if (len == 0) { return 0; }
	for(i=0; i<len; i++) {
		sum += array[i];
	} 
	// 精确到小数点后2位
	sum = Math.round(sum*100) / 100;
	return sum;
};

var averageFunc = function(array) {
	var sum = 0;
	var len = (array == null ? 0 : array.length);
	if (len == 0) { return 0; }
	sum = sumFunc(array); 
	var avg = sum / len;
	avg = Math.round(avg*100) / 100;
	return avg;
};

/**
 * 将指定 chartData 数据按照指定字段的值分类
 * eg. [{'timestamp': 'time0', cpu: '0', sys: '15', usr: '20'}, {'timestamp': 'time0', cpu: '1', sys: '16', usr: '21'},
 *      {'timestamp': 'time1', cpu: '0', sys: '20', usr: '13'}, {'timestamp': 'time1', cpu: '1', sys: '18', usr: '10'}]
 * 转换为 [{ cpu:'0', data: [{'timestamp': 'time0', cpu: '0', sys: '15', usr: '20'}, {'timestamp': 'time1', cpu: '0', sys: '20', usr: '13'}] } ,
 *       { cpu:'1', data: [{'timestamp': 'time0', cpu: '1', sys: '16', usr: '21'}, {'timestamp': 'time1', cpu: '1', sys: '18', usr: '10'}] }] 
 */
var groupByField = function(chartDataGathered, fieldName) {
	var fieldDataMappingArray = [];
	var fieldData = {};
	var i=0, num = (chartDataGathered == null ? 0 : chartDataGathered.length);
	for (i=0; i<num; i++) {
		var fieldValue = chartDataGathered[i][fieldName];
		fieldData = obtainFieldData(fieldDataMappingArray, fieldName, fieldValue);
		if (fieldData == null) {
			fieldData = {};
			fieldData[fieldName] = fieldValue;
			fieldData['data'] = [];
			fieldDataMappingArray.push(fieldData);
		}
		fieldData['data'].push(chartDataGathered[i]);
	}
	return fieldDataMappingArray;
};


/**
 * 在 fieldDataMappingArray 中检测是否有 fieldName = fieldValue 的对象, 若有则返回; 若没有则返回 null
 * @param fieldDataMappingArray [{'fieldName': 'fieldValue1', 'data':[]}, {'fieldName': 'fieldValue2', data: []}]
 * @param fieldName the name of field
 * @param fieldValue the value of field
 */
var obtainFieldData = function(fieldDataMappingArray, fieldName, fieldValue) {
	var k=0, dataArrayLength = (fieldDataMappingArray == null ? 0 : fieldDataMappingArray.length);
	var existFieldData = {};
	if (dataArrayLength == 0) {
		return null;
	}
	
	for (k=0; k<dataArrayLength; k++) {
		existFieldData = fieldDataMappingArray[k];
		if (existFieldData[fieldName] == fieldValue) {
			return existFieldData;
		}
	}
	return null;
};

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
};
