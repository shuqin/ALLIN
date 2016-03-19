/**
 * 使用 highcharts 绘制时间趋势曲线图
 * 
 */
Highcharts.setOptions({
	global: {
		useUTC: false
	},
	lang: {
		resetZoom: '还原视图'
	}
	
});

/**
 * generateChart:
 *     chartDiv  绘图所需要的 DIV 区域名称;
 *     chartData 绘图所需要的数据
 *     chartConfig 绘图的全局配置对象
 *          title: 此图表的标题
 *          xFormat: 此图表的X轴格式指定
 *     lineConfigArray  每个曲线图的配置对象(配置Y轴)
 *          title: 每条曲线所对应的文字说明
 *          valueField: 每条曲线所对应的对象数组数据中的字段名称
 *          lineColor: 每条曲线的颜色设定
 *     clickEventHandler 点击图表后的事件设定, 如果没有设定, 则在指定区域 <div id="zoomUpDiv"> 放大显示; 
 *                                      如果没有 zoomUpDiv 区域, 则什么都不做
 */
function generateChart(chartDivName, chartData, chartConfig, lineConfigArray, clickEventHandler) {
	var xDateFormat = chartConfig.xFormat;
	var yTipFormat = chartConfig.yTipFormat;
	var yValueFormat = chartConfig.yValueFormat;
	if (xDateFormat == null) {
		xDateFormat = { hour: '%m-%d %H:%M' };
	}
	
	var displayFormatter = function() {  // 当鼠标悬停图表上时, 格式化提示信息
    	var xDisplayFormat = xDateFormat['hour'] || xDateFormat['day'] || xDateFormat['week']
                                                 || xDateFormat['month'] || xDateFormat['year']
                                                 || xDateFormat['minute'] || xDateFormat['second'];
        var tipText = '<b>' + Highcharts.dateFormat(xDisplayFormat, this.x) + '</b>';
        if (yTipFormat != null) {
        	tipText = yTipFormat(this.points, tipText);
        }
        else {       	
        	$.each(this.points, function(i, point) {
        		tipText += '<br/>'+ point.series.name +': '+ point.y;
        	});
        }     
        return tipText;
	};
    
	var chartObj = obtainCommonChartObj(displayFormatter);
	chartObj.title.text = chartConfig.title;
	chartObj.xAxis = {
		type: 'datetime',
		dateTimeLabelFormats: xDateFormat
	};
	// 在同一坐标中绘制多个曲线图
	for (var i=0; i<lineConfigArray.length;i++) {
		var valueData;
		if(yValueFormat != null)
			valueData = yValueFormat(chartData, lineConfigArray[i].valueField);
		else
			valueData = extract(chartData, lineConfigArray[i].valueField);
		
		
    	var subseries = {
    		name: lineConfigArray[i].title,
    		data: valueData,
    		color: lineConfigArray[i].lineColor
    	};
    	chartObj.series.push(subseries);
    }
	var chartdiv = $('#'+chartDivName);
	chartObj.yAxis.tickPixelInterval = chartdiv.height() / 4;
	
	/*
	 * 点击图表的事件设定
	 */
	if (clickEventHandler != null && typeof clickEventHandler === 'function') {
		chartObj.chart.events.click = clickEventHandler;
		chartObj.plotOptions.series.events.click = clickEventHandler;
		
	}
	chartdiv.highcharts(chartObj);
}

/**
 * 点击图表后在新窗口中放大显示
 */
function generateChart2(chartDivName, chartData, chartConfig, lineConfigArray) {
	var windowSetting = 'width=1000,height=580, toolbar=no, location=no, directories=no, status=no, menubar=no,resizable=no,scrollbars=no, top=100, left=200';
	var indicator = chartConfig.indicator;
	var type = chartConfig.type;
	var content = chartConfig.content;
	var allowClick = chartConfig.allowClick;
	if (allowClick == null || allowClick == true) {
		var clickEventHandler =  function (event) {
			var src = httpPrefix+'/framehtml/zoomedUpChart.html?indicator=' + indicator + '&type=' + type + '&content=' + JSON.stringify(content);
			popWin.showWin('880', '610', '性能曲线放大图', src, {'parent': $(window.parent.document.body)});
		};
	}
	else {
		clickEventHandler= null;
	}
	generateChart(chartDivName, chartData, chartConfig, lineConfigArray, clickEventHandler);
}

/**
 * 创建分类曲线图
 * @param chartDivName
 * @param chartData
 * @param chartConfig
 * @param lineConfigArray
 * @param clickEventHandler
 * @returns
 */
function generateCategoryChart(chartDivName, chartData, chartConfig, fieldArray, clickEventHandler) {
	var categoryFunc = chartConfig.categoryFunc;
	var displayFormatter = function() {  // 当鼠标悬停图表上时, 格式化提示信息
    	var tipText = '<b>' + this.x + '</b>';
        $.each(this.points, function(i, point) {
        	tipText += '<br/>'+ point.series.name +': '+ point.y;
        });
        return tipText;
    };
    var chartObj = obtainCommonChartObj(displayFormatter);
	chartObj.title.text = chartConfig.title;
	var categories = categoryFunc(chartData);
	chartObj.xAxis.categories = categories;
	
	// 在同一坐标中绘制多个曲线图
	
	for (var i=0; i<fieldArray.length;i++) {
		var fieldData = [];
		for (var j=0; j<chartData.length; j++) {
			fieldData.push(chartData[j][fieldArray[i].valueField])
		}
		
    	var subseries = {
    		name: fieldArray[i].title,
    		data: fieldData,
    		color: fieldArray[i].lineColor,
    		type: fieldArray[i].type || 'line'
    	};
    	chartObj.series.push(subseries);
    }
	var chartdiv = $('#'+chartDivName);
	chartObj.yAxis.tickPixelInterval = chartdiv.height() / 4;
	
	/*
	 * 点击图表的事件设定
	 */
	if (clickEventHandler != null && typeof clickEventHandler === 'function') {
		chartObj.chart.events.click = clickEventHandler;
		chartObj.plotOptions.series.events.click = clickEventHandler;
		
	}
	chartdiv.highcharts(chartObj);
}

/**
 * 创建柱状图(针对对象数组的高层接口)
 * @param chartDivName  用来绘制柱状图的 DIV-ID 值
 * @param chartData     对象数组
 *                      categories: ['c1', 'c2', ..., 'Cn']
 *                      data:
 *                      [{'field1': 'v11', 'field2': 'v12', ..., 'fieldN': 'v1N'},
 *                       {'field1': 'v21', 'field2': 'v22', ..., 'fieldN': 'v2N'},
 *                       ..., 
 *                       {'field1': 'vN1', 'field2': 'vN2', ..., 'fieldN': 'vNN'}]
 * @param chartConfig   柱状图全局配置
 *                       title: 图表标题
 *                       categoryFunc: 将单行数据项转化为某个 category 值的方法
 *                       categoryField: 分组字段
 *                       groupField: 用于创建 legend 的分组字段
 *                       valueField: 用于显示 Y 轴的字段
 * @returns
 */
function generateColumnChartHighLevel(chartDivName, chartData, chartConfig) {
    
	var groupField = chartConfig.groupField;
	var valueField = chartConfig.valueField;
	var categoryField = chartConfig.categoryField;
	var categoryFunc = chartConfig.categoryFunc;
	
	var categories = chartData.categories;
	
    var groupedChartData = groupByField(chartData.data, groupField);
    
    var series = [];
    
	// 在同一坐标中绘制多个曲线图
	for (var i=0; i< groupedChartData.length; i++) {
		var groupName = groupedChartData[i][groupField];
		var groupData = groupedChartData[i]['data'];
		
		var fieldData = [];
		for (var k=0; k < groupData.length; k++) {
			// 每个分类的值必须与相应的分类对应, 应对这样的情况
			// 对于每个 groupField, 并不是所有 categories 都有值, 可以通过测试例子看出来
			// 苹果在 Q3 对应的值是缺失的, 香蕉在 Q2 对应的值是缺失的 
			var categoryPosition =  getCategoryPosition(categories, groupData[k], categoryFunc);
			if (categoryPosition != -1) {
				fieldData[categoryPosition] = groupData[k][valueField];
			}
		}
		for (var j=0; j < categories.length; j++) {
			// 缺失值填充
			if (fieldData[j] == null) {
				fieldData[j] = 0;
			}
		}
		var subseries = {
    		name: groupName,
    		data: fieldData,
    	};
		series.push(subseries);
	}
	
	var data = {};
    data.categories = categories;
    data.series = series;
	generateColumnChart(chartDivName, data, chartConfig);
	
}


/**
 * 检测 value 在 categories 中的位置
 * @param categories 分类值
 * @param dataItem   数据项
 * @param categoryFunc 将数据项转化为分类值的函数
 */
function getCategoryPosition(categories, dataItem, categoryFunc) {
	var value = categoryFunc(dataItem);
	for (var index=0; index < categories.length; index++) {
		if (categories[index] == value) {
			return index;
		}
	}
	return -1;
}

/**
 * 测试绘制柱状图高层接口的测试例子
 * @param chartDivId
 */
function testGenerateColChartHighLevel(chartDivId) {
	var data = [
	    { time: 'Q1' , fruit: '苹果', sale: 1500 },             
	    { time: 'Q1' , fruit: '桔子', sale: 1300 },
	    { time: 'Q1' , fruit: '香蕉', sale: 1400 },
	    { time: 'Q2' , fruit: '苹果', sale: 1500 }, 
	    { time: 'Q2' , fruit: '桔子', sale: 1900 },
	    { time: 'Q3' , fruit: '桔子', sale: 1700 },
	    { time: 'Q3' , fruit: '香蕉', sale: 1800 }
	];
	
	var categories = ['Q1', 'Q2', 'Q3'];
	
	var chartData = {
		categories: categories,
		data: data
	};
	
	var chartConfig = {
		title: '季度水果销量',
		categoryField: 'time',
		groupField: 'fruit',
		valueField: 'sale'
	}
	generateColumnChartHighLevel(chartDivId, chartData, chartConfig);
}


/**
 * 创建柱状图(基本接口)
 * @param chartDivName  用来绘制柱状图的 DIV-ID 值
 * @param chartData     柱状图数据结构
 *                      categories: ['c1', 'c2', ..., 'Cn']
 *                      series: [
 *                          { name: 'var1', data: [d11, d12, ..., d1n]},  
 *                          { name: 'var2', data: [d21, d22, ..., d2n]},
 *                          ...,
 *                          { name: 'varN', data: [dn1, dn2, ..., dnn]}
 *                      ]
 * @param chartConfig  柱状图全局配置
 *                        title:  图表标题
 * @returns
 */
function generateColumnChart(chartDivName, chartData, chartConfig) {
	var displayFormatter = function() {  // 当鼠标悬停图表上时, 格式化提示信息
    	var tipText = '<b>' + this.x + '</b>';
    	var total = 0;
    	$.each(this.points, function(i, point) {
        	total += point.y;
        });
        $.each(this.points, function(i, point) {
        	tipText += '<br/>'+ point.series.name +': '+ Highcharts.numberFormat((point.y*100 / total), 2) + '%' + '(' + point.y + ')';
        });
        return tipText;
    };
    
    var chartObj = obtainCommonChartObj(displayFormatter);
	chartObj.title.text = chartConfig.title;
	chartObj.xAxis.categories = chartData.categories;
	chartObj.series = chartData.series;
	var seriesNum = (chartData.series == null ? 0 : chartData.series.length);
	for (var k=0; k < seriesNum; k++) {
		chartObj.series[k].type = 'column';
	}
	
	var chartdiv = $('#'+chartDivName);
	chartdiv.highcharts(chartObj);
}

/**
 * 一个用于测试基本接口的示例
 * @param chartDivId
 */
function testGenerateColumnChart(chartDivId) {
	var categories = ['2013-11', '2013-12', '2014-01'];
	var series = [
	     { name: '苹果', data: [1500, 1300, 1200] },
	     { name: '桔子', data: [3500, 5000, 2500] },
	     { name: '香蕉', data: [2000, 1800, 1600] }
	];
	var data = {
		categories: categories, series: series	
	};
	var chartConfig = { title: '第四季度水果销量' };
	generateColumnChart(chartDivId, data, chartConfig);
}

function obtainCommonChartObj(displayFormatterFunc) {
	
	   var commonChartObj = {
			chart: {
				zoomType: 'x',
				events: {
					click: null
				},
				resetZoomButton: {
					position: {
	                    x: -10,
	                    y: 10
	                },
	                relativeTo: 'chart'
				}
			},
			
			// 去掉 highcharts.com 链接
			credits: {
				enabled: false,
				text: ''
			},
			
			exporting: {
				buttons: {
					contextButton: {
						menuItems: null,
	                    onclick: function() {
	                        this.exportChart();
	                    }
					}
				},
				enabled: true,
				type: 'image/jpeg'
			},
			
			plotOptions: {
				series: {
					// 去掉点的marker, 使图形更美观
	                marker: {
	                    enabled: false,
	                    states: {
	                        hover: {
	                            enabled: true
	                        }
	                    }
	                },
	                turboThreshold: 0,
	                events: {
	                	click: null
	                }
	            },
	            line: {
	            	lineWidth: 1.5
	            }
			},
			
			series: [],
			xAxis: {
			},
			yAxis: {
				title: {
					text: ''
				},
			    min: 0
			},
			
			tooltip: {
				crosshairs: true,
				shared: true,
	            formatter: displayFormatterFunc
	        },
	        
		    title: {
		    	// 动态显示图表标题
		    	text: '',
		    	align: 'center',
		    	style: {
		    		fontSize: '12px',
	                margin: '3px'
		    	}
		    }
	   };
	   return commonChartObj ;
}


/**
 * 绘制饼图
 * @param chartDivName 
 * @param chartData  [['category1', data1], ... ['categoryN', dataN]]
 * @param clickHandler
 */
function generatePieChart(chartDivName, chartData, clickHandler) {
	
	var chartObj = {
		chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        // 去掉 highcharts.com 链接
		credits: {
			enabled: false,
			text: ''
		},
		exporting: {
			buttons: {
				contextButton: {
					menuItems: null,
                    onclick: function() {
                        this.exportChart();
                    }
				}
			},
			enabled: true,
			type: 'image/jpeg'
		},
        title: {
            text: ''
        },
        tooltip: {
        	formatter: function() {
                return '<b>'+ this.point.name +'</b>:<br/>'+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                             Highcharts.numberFormat(this.y, 0, ',') +')';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                },
                size: 128
            }
        },
        series: [{
            type: 'pie',
            name: '宕机原因分布',
            data: chartData,
            events: {
            	click : clickHandler
            }
        }]
	};
	var chartdiv = $('#'+chartDivName);
	chartdiv.highcharts(chartObj);
}

/**
 * 数据格式转换, 便于 highchart 绘制饼图
 * eg. [{downReason: "crash-xen", downAmount: 1}, {downReason: "disk_error", downAmount:3}] =>
 *     [["crash-xen", 1], ["disk_error", 3]]
 */
function transferDataFormat(objArray, categoryField, valueField) {
	var i=0, len = (objArray == null) ? 0 : objArray.length;
	var resultData = [];
	for (i=0; i<len; i++) {
		
		var category = objArray[i][categoryField];
		var value = objArray[i][valueField];
		resultData.push([category, value]);
	}
	return resultData;
}


/**
 * 指定后台返回的时间字段为 timeStampFieldName , 若不指定则默认为 timestamp , 
 * 并利用自定义回调函数 dateConvertFunc 统一为时间戳进行处理
 */
function convertDate(chartData, timeStampFieldName, dateConvertFunc) {
	for (var i=0; i<chartData.length;i++) {
		var timestamp_i = chartData[i][timeStampFieldName == null ? "timestamp": timeStampFieldName];
		if (typeof dateConvertFunc === 'function') {
			// 如果给定了自定义的时间转换回调函数, 则使用该函数
			chartData[i].timestamp = dateConvertFunc(timestamp_i);
		}
		else if (typeof timestamp_i === 'number') {
			// 适用于时间戳, 精确到毫秒
			chartData[i].timestamp = timestamp_i;
			
		}
		else {
			// 适用于格式 "Sun Sep 29 17:24:07 2013", "2013-09-29 17:23:09"
			chartData[i].timestamp = new Date(timestamp_i).getTime();
		}
	}
    return chartData;
}

/**
 * 从 chartData 中抽取出 valueField 数据
 * 满足 warnFunc 指定条件的点突出红色显示
 */
function extract(chartData, valueField) {
	var valueData = [], value = 0;
	var i=0, num = (chartData == null ? 0 : chartData.length);
    var point = {};
    var tagArray = [];
    var markerSetting = { enabled: true, radius: 2};
	
	for (i=0; i<num; i++) {
		point = chartData[i];
		tagArray = point.tag;
		value = Math.floor(parseFloat(point[valueField])*100000)/100000;
		if (isInArray(valueField, tagArray)) {
			valueData.push({x: chartData[i].timestamp, y: value, color: '#ff0000', marker: markerSetting});
		}
		else {
			valueData.push({x: chartData[i].timestamp, y: value});
		}
	}
	return valueData;
}

var isInArray = function(value, array) {
	var i=0, len = (array == null ? 0 : array.length);
	for (i=0; i<len; i++) {
		if (array[i] == value) {
			return true;
		}
	}
	return false;
}
