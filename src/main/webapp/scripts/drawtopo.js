/**
 * 使用 jsPlumb 根据指定的拓扑数据结构绘制拓扑图
 * 使用 drawTopo(topoData, nodeTypeArray) 方法
 * 
 */

/**
 * 初始化拓扑图实例及外观设置
 */
(function() {
	
	jsPlumb.importDefaults({
		
		DragOptions : { cursor: 'pointer', zIndex:2000 },
	
		EndpointStyles : [{ fillStyle:'#225588' }, { fillStyle:'#558822' }],
	
		Endpoints : [ [ "Dot", { radius:2 } ], [ "Dot", { radius: 2 } ]],
	
		ConnectionOverlays : [
			[ "Arrow", { location:1 } ],
			[ "Label", { 
				location:0.1,
				id:"label",
				cssClass:"aLabel"
			}]
		]
	});
	
	var connectorPaintStyle = {
		lineWidth: 1,
		strokeStyle: "#096EBB",
		joinstyle:"round",
		outlineColor: "#096EBB",
		outlineWidth: 1
	};
	
	var connectorHoverStyle = {
		lineWidth: 2,
		strokeStyle: "#5C96BC",
		outlineWidth: 2,
		outlineColor:"white"
	};
	
	var endpointHoverStyle = {
		fillStyle:"#5C96BC"
	};
	
	window.topoDrawUtil = {
			
		sourceEndpoint: {
			endpoint:"Dot",
			paintStyle:{ 
				strokeStyle:"#1e8151",
				fillStyle:"transparent",
				radius: 2,
				lineWidth:2 
			},				
			isSource:true,
			maxConnections:-1,
			connector:[ "Flowchart", { stub:[40, 60], gap:10, cornerRadius:5, alwaysRespectStubs:true } ],								                
			connectorStyle: connectorPaintStyle,
			hoverPaintStyle: endpointHoverStyle,
			connectorHoverStyle: connectorHoverStyle,
	        dragOptions:{},
	        overlays:[
	        	[ "Label", { 
	            	location:[0.5, 1.5], 
	            	label:"",
	            	cssClass:"endpointSourceLabel" 
	            } ]
	        ]
		},
		
		targetEndpoint: {
			endpoint: "Dot",					
			paintStyle: { fillStyle:"#1e8151",radius: 2 },
			hoverPaintStyle: endpointHoverStyle,
			maxConnections:-1,
			dropOptions:{ hoverClass:"hover", activeClass:"active" },
			isTarget:true,			
	        overlays:[
	        	[ "Label", { location:[0.5, -0.5], label:"", cssClass:"endpointTargetLabel" } ]
	        ]
		},
	    
	    initConnection: function(connection) {
			connection.getOverlay("label").setLabel(connection.sourceId + "-" + connection.targetId);
			connection.bind("editCompleted", function(o) {
				if (typeof console != "undefined")
					console.log("connection edited. path is now ", o.path);
			});
		},			

		addEndpoints: function(toId, sourceAnchors, targetAnchors) {
			for (var i = 0; i < sourceAnchors.length; i++) {
				var sourceUUID = toId + sourceAnchors[i];
				jsPlumb.addEndpoint(toId, this.sourceEndpoint, { anchor:sourceAnchors[i], uuid:sourceUUID });						
			}
			for (var j = 0; j < targetAnchors.length; j++) {
				var targetUUID = toId + targetAnchors[j];
				jsPlumb.addEndpoint(toId, this.targetEndpoint, { anchor:targetAnchors[j], uuid:targetUUID });						
			}
		}
    };


})();


/**
 * drawTopo 根据给定拓扑数据绘制拓扑图
 * @param topoData 拓扑数据
 * @param rootPosition 拓扑图根节点的位置
 * @param nodeTypeArray 节点类型数组
 * 
 * 拓扑图的所有节点是自动生成的, DIV class = "node" , id= nodeType.toUpperCase + "-" + key 
 * 拓扑图的所有节点连接也是自动生成的, 可以进行算法改善与优化, 但使用者不需要关心此问题
 * 需要定义节点类型数组 nodeTypeArray
 * 
 * 拓扑数据结构:
 * 1. 节点数据结构: node = { type: 'typeName', key: 'key', rel: [], data: {'More Info'}} 
 *    rel, data 可选 , type-key 唯一标识该节点
 * 2. 关联关系: rel: [node1, node2, ..., nodeN]
 * 3. 更多详情: 关于节点的更多信息可放置于此属性中
 * 4. 示例:
 *   var topoData = {
 *			type: 'VM', key: '110.75.188.35', 
 *			rel: [
 *			     {   type: 'DEVICE', key: '3-120343' },
 *			     { 	 type: 'DEVICE', key: '3-120344' },
 *			     { 	 type: 'VIP',  	 key: '223.6.250.2',
 *			    	 rel: [
 *			    	     { type: 'VM', key: '110.75.189.12' },
 *			    	     { type: 'VM', key: '110.75.189.12' }
 *			    	 ]
 *			     },
 *			     { 	 type: 'NC',  key: '10.242.192.2',
 *			    	 rel: [
 *                       { type: 'VM', key: '110.75.188.132' },
 *                       { type: 'VM', key: '110.75.188.135' },
 *                       { type: 'VM', key: '110.75.188.140' }
 *			    	 ]
 *			     
 *			     }
 *			]
 *		};
 * 
 */
function drawTopo(topoData, rootPosition, nodeTypeArray) {
	
	// 创建所有拓扑节点及连接并确定其位置
	createNodes(topoData, rootPosition, nodeTypeArray);
	
	// 调整重合节点的位置, 添加节点的附着点, 即连接线的端点
	adjust(topoData, nodeTypeArray);
	
	// 使所有拓扑节点均为可拉拽的					
	jsPlumb.draggable(jsPlumb.getSelector(".node"), { grid: [5, 5] });
	
	// 创建所有节点连接
	createConnections(topoData, nodeTypeArray);
	
}

/**
 * 根据给定拓扑数据绘制拓扑节点并确定其位置, 使用深度优先遍历
 * @param topoData 拓扑数据
 * @param rootPosition 根节点的位置设定
 * @param nodeTypeArray 拓扑节点类型
 */
function createNodes(rootData, rootPosition, nodeTypeArray) {
	
	if (rootData == null) {
		return ;
	}
	
	var topoRegion = $('#topoRegion');
	var relData = rootData.rel;
	var i=0, relLen = relLength(relData);;
	var VM_TYPE = nodeTypeArray[0];
	var DEVICE_TYPE = nodeTypeArray[1];
	var NC_TYPE = nodeTypeArray[2];
	var VIP_TYPE = nodeTypeArray[3];
	
	// 根节点的位置, 单位: px
	var rootTop = rootPosition[0];
	var rootLeft = rootPosition[1];
	
	var nextRootData = {};
	var nextRootPosition = [];
	
	// 自动生成并插入根节点的 DIV 
	var divStr = createDiv(rootData);
	var nodeDivId = obtainNodeDivId(rootData);
	topoRegion.append(divStr);
	//console.log(divStr);
	
	// 设置节点位置
	$('#'+nodeDivId).css('top', rootTop + 'px');
	$('#'+nodeDivId).css('left', rootLeft + 'px');
	
	for (i=0; i < relLen; i++) {
		nextRootData = relData[i];
		nextRootPosition = obtainNextRootPosition(rootData, nextRootData, rootPosition, nodeTypeArray);
		createNodes(nextRootData, nextRootPosition, nodeTypeArray);
	}

}

/**
 * 调整重合节点的位置, 并添加节点的附着点, 即连接线的端点
 */
function adjust(topoData, nodeTypeArray) {
	
	var vm_deviceOffset = 0;   // 起始节点为 vm , 终止节点为 device, device div 的偏移量
	var vm_vipOffset = 0;      // 起始节点为 vm , 终止节点为 vip, vip div 的偏移量
	var vm_ncOffset = 0;       // 起始节点为 vm , 终止节点为 nc, nc div 的偏移量 
	var vip_vmOffset = 0;      // 起始节点为 vip , 终止节点为 vm, vm div 的偏移量     
	var nc_vmOffset = 0;      // 起始节点为nc , 终止节点为 vm, vm div 的偏移量 
	var verticalDistance = 120;
	var horizontalDistance = 150;
	
	var VM_TYPE = nodeTypeArray[0];
	var DEVICE_TYPE = nodeTypeArray[1];
	var NC_TYPE = nodeTypeArray[2];
	var VIP_TYPE = nodeTypeArray[3];
	
	$('.node').each(function(index, element) {
		var nodeDivId = $(element).attr('id');
		var nodeType = nodeDivId.split('-')[0];
		var offset = $(element).offset();
		var originalTop = offset.top;
		var originalLeft = offset.left;
		var parentNode = $(element).parent();
		var parentNodeType = parentNode.attr('id').split('-')[0];
		switch (nodeType) {
		    case VM_TYPE:
		    	// VM 位置水平偏移
	    		$(element).css('left', (originalLeft + vip_vmOffset*horizontalDistance) + 'px');
	    		vip_vmOffset++;
		    	topoDrawUtil.addEndpoints(nodeDivId, ['Top', 'Bottom', 'Right'], []);
		    	break;
		    case DEVICE_TYPE:
		    	// DEVICE 位置垂直偏移
	    		$(element).css('top', (originalTop + (vm_deviceOffset-1)*verticalDistance) + 'px');
		    	vm_deviceOffset++;
		    	topoDrawUtil.addEndpoints(nodeDivId, [], ['Left']);
		    	break;
		    case VIP_TYPE:
		    	// VIP 位置水平偏移
		    	$(element).css('left', (originalLeft + vm_vipOffset*horizontalDistance) + 'px');
		    	vm_vipOffset++;
		    	topoDrawUtil.addEndpoints(nodeDivId, ['Top'], ['Bottom']);
		    	break;
		    case NC_TYPE:
		    	// NC 位置水平偏移
		    	$(element).css('left', (originalLeft + vm_ncOffset*verticalDistance) + 'px');
		    	vm_ncOffset++;
		    	topoDrawUtil.addEndpoints(nodeDivId, ['Bottom'], ['Top']);
		    	break;
		    default:
		    	break;
		}
	});
}

/**
 * 获取下一个根节点的位置, 若节点类型相同, 则位置会重合, 需要后续调整一次
 * @root            当前根节点
 * @nextRoot        下一个根节点
 * @rootPosition    当前根节点的位置
 * @nodeTypeArray   节点类型数组
 */
function obtainNextRootPosition(root, nextRoot, rootPosition, nodeTypeArray) {
	
	var VM_TYPE = nodeTypeArray[0];
	var DEVICE_TYPE = nodeTypeArray[1];
	var NC_TYPE = nodeTypeArray[2];
	var VIP_TYPE = nodeTypeArray[3];
	
	var startNodeType = root.type;
	var endNodeType = nextRoot.type;
	var nextRootPosition = [];
	var rootTop = rootPosition[0];
	var rootLeft = rootPosition[1];
	
	var verticalDistance = 120;
	var horizontalDistance = 250;
	var shortVerticalDistance = 80;
	
	switch (startNodeType) {
	    case VM_TYPE:
	    	if (endNodeType == VIP_TYPE) {
	    		nextRootPosition = [rootTop-verticalDistance, rootLeft];
	    	}
	    	else if (endNodeType == DEVICE_TYPE) {
	    		nextRootPosition = [rootTop, rootLeft+horizontalDistance];
	    	}
	    	else if (endNodeType == NC_TYPE) {
	    		nextRootPosition = [rootTop+verticalDistance, rootLeft];
	    	}
	    	break;
	    case VIP_TYPE:
	    	if (endNodeType == VM_TYPE) {
	    		nextRootPosition = [rootTop-shortVerticalDistance, rootLeft];
	    	}
	    	break;
	    case NC_TYPE:
	    	if (endNodeType == VM_TYPE) {
	    		nextRootPosition = [rootTop+shortVerticalDistance, rootLeft];
	    	}
	    	break;
	    default: 
	    	break;
	}
	return nextRootPosition;
}

/**
 * 根据给定拓扑数据, 绘制节点之间的连接关系, 使用深度优先遍历
 * @param topoData 拓扑数据
 * @param nodeTypeArray 节点类型数组
 */
function createConnections(topoData, nodeTypeArray) {
	
	if (topoData == null) {
		return ;
	}
	var rootData = topoData;
	var relData = topoData.rel;
	var i=0, len = relLength(relData);;
	for (i=0; i < len; i++) {
		connectionNodes(rootData, relData[i], nodeTypeArray);
		createConnections(relData[i], nodeTypeArray);
	}		
}

/**
 * 连接起始节点和终止节点
 * @beginNode 起始节点
 * @endNode 终止节点
 * NOTE: 根据是起始节点与终止节点的类型
 */
function connectionNodes(beginNode, endNode, nodeTypeArray) 
{
	var startNodeType = beginNode.type;
	var endNodeType = endNode.type;
	var startDirection = '';
	var endDirection = '';
	
	var VM_TYPE = nodeTypeArray[0];
	var DEVICE_TYPE = nodeTypeArray[1];
	var NC_TYPE = nodeTypeArray[2];
	var VIP_TYPE = nodeTypeArray[3];
	
	switch (startNodeType) {
	    case VM_TYPE:
	    	if (endNodeType == VIP_TYPE) {
	    		// VIP 绘制于 VM 上方
	    		startDirection = 'Top';
	    		endDirection = 'Bottom';
	    	}
	    	else if (endNodeType == DEVICE_TYPE) {
	    		// DEVICE 绘制于 VM 右方
	    		startDirection = 'Right';
	    		endDirection = 'Left';
	    	}
	    	else if (endNodeType == NC_TYPE) {
	    		// NC 绘制于 VM 下方
	    		startDirection = 'Bottom';
	    		endDirection = 'Top';
	    	}
	    	break;
	    case VIP_TYPE:
	    	if (endNodeType == VM_TYPE) {
	    		// VM 绘制于 VIP 上方
	    		startDirection = 'Top';
	    		endDirection = 'Top';
	    	}
	    	break;
	    case NC_TYPE:
	    	if (endNodeType == VM_TYPE) {
	    		// VM 绘制于 NC 下方
	    		startDirection = 'Bottom';
	    		endDirection = 'Bottom';
	    	}
	    	break;
	    default: 
	    	break;
	}
	var startPoint = obtainNodeDivId(beginNode) + startDirection;
	var endPoint = obtainNodeDivId(endNode) + endDirection;
	jsPlumb.connect({uuids:[startPoint, endPoint], editable: false}); 
}

function createDiv(metaNode) {
	return '<div class="node" id="' + obtainNodeDivId(metaNode) + '"><strong>' 
	        + metaNode.type + '<br/><a href="http://aliyun.com">' + metaNode.key + '</a><br/></strong></div>'
}

/**
 * 生成节点的 DIV id
 * divId = nodeType.toUpperCase + "-" + key
 * key 可能为 IP , 其中的 . 将被替换成 ZZZ , 因为 jquery id 选择器中 . 属于转义字符.
 * eg. {type: 'VM', key: '1.1.1.1' }, divId = 'VM-1ZZZ1ZZZ1ZZZ1'
 */
function obtainNodeDivId(metaNode) {
	return metaNode.type.toUpperCase() + '-' + transferKey(metaNode.key);
}

function transferKey(key) {
	return key.replace(/\./g, 'ZZZ');
}

function revTransferKey(value) {
	return value.replace(/ZZZ/g, '.');
}


/**
 * 合并新的拓扑结构到原来的拓扑结构中, 新的拓扑结构中有节点与原拓扑结构中的某个节点相匹配: type-key 相等
 * @param srcTopoData 原来的拓扑结构
 * @param newTopoData 要添加的的拓扑结构
 */
function mergeNewTopo(srcTopoData, newTopoData) {
	
	var srcTopoData = shallowCopyTopo(srcTopoData);
	
	if (srcTopoData == null || newTopoData == null) {
		return srcTopoData || newTopoData;
	}
	
	var srcRoot = srcTopoData;
	var newRoot = newTopoData;

	var newRelData = newTopoData.rel;
	var i=0, newRelLen = relLength(newRelData);
	
	var matched = findMatched(srcRoot, newRoot);
	if (matched == null) {
		// 没有找到匹配的节点, 直接返回原有的拓扑结构
		return srcTopoData;
	}
	matched.rel = matched.rel.concat(newRelData);
	return srcTopoData;
}

/**
 * 在原拓扑结构中查找与新拓扑结构根节点 newRootData 匹配的节点
 * @param srcRootData 原拓扑结构
 * @param newRootData 新拓扑结构的根节点
 * @returns 原拓扑结构中与新拓扑结构根节点匹配的节点 or null if not found
 */
function findMatched(srcRootData, newRootData) {
	var srcRelData = srcRootData.rel;
	var i=0, srcRelLen = relLength(srcRelData);
	var matched = null;
	if ((srcRootData.type == newRootData.type) && (srcRootData.key == newRootData.key)) {
		return srcRootData;
	}
	for (i=0; i<srcRelLen; i++) {
		matched = findMatched(srcRelData[i], newRootData);
		if (matched != null) {
			return matched;
		}
	}
	return matched;
}

function relLength(relData) {
	if (isArray(relData)) {
		return relData.length;
	} 
	return 0;
}

function isArray(value) {
	return value && (typeof value === 'object') && (typeof value.length === 'number');
}

/**
 * 浅复制拓扑结构
 */
function shallowCopyTopo(srcTopoData) {
	return srcTopoData;
}

/**
 * 深复制拓扑结构
 */
function deepCopyTopo(srcTopoData) {
	//TODO identical to deep copy of js json
}