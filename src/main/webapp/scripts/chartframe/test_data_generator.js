/**
 * 生成测试数据
 * @param fieldFuncMapping 字段-生成函数映射, 结构:
 *        {field1: func1, field2: func2, ..., fieldN: funcN}
 * @param pointNum 点数
 * @return 返回一个对象数组 [{'timestamp:': '1234055532', 'field1': 'xxx', 'field2': 'xxx', 'fieldN': 'xxx'}]
 */
var generateTestData = function(fieldFuncMapping, pointNum) {
	var i=0;
	var freq_sec = 60;
	var now = new Date();
	var nowTimestamp = Math.floor(now.getTime());
    var timestamp = 0;
    var dataobjArray = [];
    var field = '';
    var func = null;
    var key = null;
    var value = null;	
    var result = [];
	for (i=0; i<pointNum; i++) {
		dataobjArray[i] = {};
		timestamp = nowTimestamp - (pointNum-i)*freq_sec*1000;
		dataobjArray[i].timeStamp = timestamp;
		for (key in fieldFuncMapping) {
			field = key;
			randFunc = fieldFuncMapping[field];
			value = randFunc();
			dataobjArray[i][field] = value;
		}
		result.push(dataobjArray[i]);
	}
	return result;
} 

var randNumFunc = function(number) {
	return Math.floor(Math.random()*number);
}

var numberRandomFunc = function() {
	return randNumFunc(100); 
};

var vmcpu = function(pointNum) {
	var fieldFuncMappingInstance = {'cpu': numberRandomFunc};
	return generateTestData(fieldFuncMappingInstance, pointNum);
}

var vmio = function(pointNum) {
	var fieldFuncMappingInstance = {'rkBs': numberRandomFunc, 'wkBs': numberRandomFunc, 
			       'rs':numberRandomFunc, 'ws': numberRandomFunc, 'util': numberRandomFunc};
	return generateTestData(fieldFuncMappingInstance, pointNum);
}



