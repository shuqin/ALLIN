/**
 * 使用 amcharts 绘制时间趋势曲线图
 * 
 * generateChart:
 *     chartDiv  绘图所需要的 DIV 区域名称;
 *     chartData 绘图所需要的数据
 *     chartConfig 绘图的全局配置对象
 *     lineConfigArray  每个曲线图的配置对象(配置Y轴)
 * 
 */
var globalChart = null, globalChartData = null;
function generateChart(chartDiv, chartData, chartConfig, lineConfigArray) {
	    console.log('begin draw chart: ' + getNow());
    	// SERIAL CHART
        var chart = new AmCharts.AmSerialChart();
        globalChart = chart;
        globalChartData = chartData;
        chart.pathToImages = "../resources/images/amcharts2/";
        chart.zoomOutButton = {
            backgroundColor: '#000000',
            backgroundAlpha: 0.15
        };
        chart.dataProvider = chartData;
        chart.categoryField = "timestamp";

        // data updated event will be fired when chart is first displayed,
        // also when data will be updated. We'll use it to set some
        // initial zoom
        chart.addListener("dataUpdated", zoomChart);
       
        // AXES
        // Category
        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = true; // in order char to understand dates, we should set parseDates to true
        categoryAxis.minPeriod = "mm";  // as we have data with minute interval, we have to set "mm" here.			 
        categoryAxis.gridAlpha = 0.07;
        categoryAxis.axisColor = "#DADADA";
        categoryAxis.labelFunction = function(valueText, date, categoryAxis) {
			var MM = date.getMonth()+1;
			var dd = date.getDate();
			var hh = date.getHours();
			if(hh<10) hh = '0' + hh;
			var mm = date.getMinutes();
			if(mm<10) mm = '0' + mm;
			var ss = date.getSeconds();
        	return MM+'-'+dd+' '+hh+':'+mm;
        }
        
        // Value
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.gridAlpha = 0.07;
        valueAxis.title = chartConfig.title;
        chart.addValueAxis(valueAxis);

        // GRAPH
        for (var i=0; i<lineConfigArray.length;i++) {
        	var graph = new AmCharts.AmGraph();
            graph.type = "line"; 
            graph.title = lineConfigArray[i].title;
            graph.valueField = lineConfigArray[i].valueField;
            graph.lineAlpha = 1;
            graph.lineColor = lineConfigArray[i].lineColor;
            chart.addGraph(graph);
        }

        // CURSOR
        var chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorPosition = "mouse";
        chartCursor.categoryBalloonDateFormat = "MM DD JJ:NN";
        chart.addChartCursor(chartCursor);

        // SCROLLBAR
        var chartScrollbar = new AmCharts.ChartScrollbar();
        chart.addChartScrollbar(chartScrollbar);
        
        // LEGEND
        var legend = new AmCharts.AmLegend();
        legend.marginLeft = 110;
        chart.addLegend(legend);
        
        // WRITE
        chart.write(chartDiv);
        console.log('end draw chart: ' + getNow());
    }

    function convertDate(chartData, timeStampFieldName, dateConvertFunc) {
		for (var i=0; i<chartData.length;i++) {
			var timestamp_i = chartData[i][timeStampFieldName == null ? "timestamp": timeStampFieldName];
			if (typeof dateConvertFunc === 'function') {
				chartData[i].timestamp = dateConvertFunc(timestamp_i);
			}
			else {
				chartData[i].timestamp = new Date(timestamp_i);
			}
		}
        return chartData;
    }

    function zoomChart() {
        globalChart.zoomToIndexes(0, globalChartData.length - 1);
    }