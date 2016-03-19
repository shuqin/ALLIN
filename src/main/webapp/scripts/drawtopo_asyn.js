/**
 * 使用 jsPlumb 根据指定的拓扑数据结构绘制拓扑图
 * 使用 drawTopo_asyn(vmName, regionNo, parentDivId) 方法
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
			[ "Label", { location:1 } ],
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
				radius: 4,
				lineWidth:2 
			},				
			isSource:true,
			maxConnections:-1,
			connector:[ "Flowchart", { stub:[40, 60], gap:1, cornerRadius:5, alwaysRespectStubs:true } ],								                
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
		
		removeAllEndPoints: function(nodeDivId) {
			jsPlumb.removeAllEndpoints($('#'+nodeDivId)); 
		},

		addEndpoints: function(toId, sourceAnchors, targetAnchors) {
			for (var i = 0; i < sourceAnchors.length; i++) {
				var sourceUUID = toId + sourceAnchors[i];
				var endPoint = jsPlumb.addEndpoint(toId, this.sourceEndpoint, { anchor:sourceAnchors[i], uuid:sourceUUID });	
				endPoint.bind("click", function(endpoint) {
					if ($('#error') != null) {
						$('#error').html('');
					}
				    var anchorType = endpoint.anchor.type;
				    var nodeType = toId.split('_')[0];
				    var content = toId.split('_')[1];
				    if (nodeType == VM_TYPE) {
				    	switch (anchorType) {
				    	    case 'Right':
				    	    	cacheKey = 'VM-DEVICE-'+ vmNodeData.key;
					    		cacheConnectionData[cacheKey] = null;
					    		linkDevices(vmNodeData, vmNodeData.key);
					    		break;
				    	    case 'Top':
				    	    	cacheKey = 'VM-ACCOUNT-'+ vmNodeData.key;
				    	    	cacheConnectionData[cacheKey] = null;
				    	    	vmName = vmNodeData.key;
				    	    	regionNo = vmNodeData.data.region_no;
				    	    	linkAccount(vmNodeData, vmName, regionNo);
					    		break;
				    	    case 'Bottom':
				    	    	cacheKey = 'VM-NC-'+ vmNodeData.key;
					    		cacheConnectionData[cacheKey] = null;
				    	    	ncId = vmNodeData.data.nc_id;
				    	    	regionNo = vmNodeData.data.region_no;
				    			linkNc(vmNodeData, ncId, regionNo);
				    			break;
				    	    case 'Left':
				    	    	cacheKey = 'VM-VIP-'+ vmNodeData.key;
					    		cacheConnectionData[cacheKey] = null;
					    		vmInnerIp = vmNodeData.data.vm_inner_ip;
					    		linkVips(vmNodeData, vmInnerIp);
					    		break;
				    		default:
				    			break;
				    	}
				    }
				    else if (nodeType == DEVICE_TYPE) {
				    	if (anchorType == 'Bottom') {
				    		cacheKey = 'DEVICE-SNAPSHOT-'+ content;
				    		cacheConnectionData[cacheKey] = null;
				    		deviceNodeData = deviceNodeDataMapping[content];
				    		linkSnapshot(deviceNodeData.data.aliUid, content, deviceNodeData);
				    	}
				    }
				});
			}
			for (var j = 0; j < targetAnchors.length; j++) {
				var targetUUID = toId + targetAnchors[j];
				jsPlumb.addEndpoint(toId, this.targetEndpoint, { anchor:targetAnchors[j], uuid:targetUUID });						
			}
		}
    };


})();


//////////////////////////////////////////////////////////////////////////////
// 这里将所有用到的数据结构汇聚在这里, 避免修改时需要在代码中穿行, 浪费时间

/**
 * 重新刷新VM关联实体时需要使用到VM的信息,这里进行全局缓存,避免重复查询
 * 重新刷新VM关联实体时VM必定存在, vmNodeData 也必定是最近一次查询的结果
 */
var vmNodeData = {};

/**
 * 重新刷新磁盘关联快照实体时需要使用到磁盘的信息,这里进行全局缓存,避免重复查询
 * 重新刷新磁盘关联快照实体时磁盘必定存在,且必定是最近一次查询的结果
 * eg. {'instanceId': { "ecsInstanceId": "vmName", "houyiDiskId": "102-80012003",
                        "aliUid": aliUidNum,  "instanceId": "d-28ilj8rsf", ... }}
 */
var deviceNodeDataMapping = {};

/**
 * 拓扑图中的节点类型
 */
var nodeTypeArray = ['VM', 'DEVICE', 'NC', 'VIP', 'SNAPSHOT', 'CLUSTER', 'AVZ', 'ACCOUNT'];

var VM_TYPE = nodeTypeArray[0];
var DEVICE_TYPE = nodeTypeArray[1];
var NC_TYPE = nodeTypeArray[2];
var VIP_TYPE = nodeTypeArray[3];
var SNAPSHOT_TYPE= nodeTypeArray[4];
var CLUSTER_TYPE= nodeTypeArray[5];
var AVZ_TYPE= nodeTypeArray[6];
var ACCOUNT_TYPE= nodeTypeArray[7];

/**
 * cacheConnectionData 节点之间的已有连接数目缓存, 在计算节点位置及布局方法 computeLayout 中用到
 * eg. {
 *    'VM-DEVICE-vmkey': 2,  'VM-NC-vmkey':1,  'VM-VIP-vmkey':2 , 'VM-ACCOUNT-vmkey': 1,
 * } 
 * 表示已经有2磁盘/1NC/2VIP/1ACCOUNT 与VM(key 为 vmkey)连接, 这些信息用于计算与VM相连接的同类型的下一个实体的相对位置
 */
var cacheConnectionData = {};

/**
 * 连接关系的存储, 在 cacheConnections , reconnectAll 方法中用到
 * 由于重复节点会移动到新的位置,原有连接会出现断连现象, 因此采用"每次刷新拉取新实体时重连所有连线" 的策略, 可以保证实时性, 只要连接数不多重复连接的开销是可以接受的.
 * connections = [[startPoint1, endPoint1], [startPoint2, endPoint2], ..., [startPointN, endPointN]];
 */
var connections = [];

/**
 * 实体与实体上附着点方向的设置
 * DEVICE_TYPE: [['Right', 'Bottom'], ['Left']] 的含义是：
 * 对于DEVICE实体: 作为起始节点时, 附着点可以在右方中点(连接CLUSTER), 下方中点(连接快照); 作为终止节点时, 附着点仅在左方中点(连接VM) 
 */
var entityEndPointsMapping = {
	"VM": [['Top', 'Bottom', 'Right', 'Left'], []],
	"DEVICE": [['Right', 'Bottom'], ['Left']],
	"NC": [['Bottom'], ['Top']],
	"VIP": [[], ['Right']],
	"SNAPSHOT": [[], ['Top']],
	"CLUSTER": [[], ['Left', 'Top']],
    "AVZ": [['Bottom'], ['Top']],
	"ACCOUNT": [[], ['Bottom']]
};

/**
 * 连接线附着点方向设置
 * "VM-ACCOUNT": ['Top', 'Bottom'] 的含义是：
 * VM 的上方附着点 与 ACCOUNT 的下方附着点的连接
 */
var connectionDirectionMapping = {
	"VM-ACCOUNT": ['Top', 'Bottom'],
	"VM-NC": ['Bottom', 'Top'],
	"NC-CLUSTER": ['Bottom', 'Top'],
	"VM-DEVICE": ['Right', 'Left'],
	"DEVICE-CLUSTER": ['Right', 'Left'],
	"VM-VIP": ['Left', 'Right'],
	"DEVICE-SNAPSHOT": ['Bottom', 'Top']
}

/**
 * 节点之间的水平与垂直相对位置
 */
var largeVerticalDistance = 270;
var verticalDistance = 220;
var horizontalDistance = 300;
var shortVerticalDistance = 50;
var shortHorizontalDistance = 220;

/**
 * 节点之间的水平或垂直相对位置和距离的设置
 * "VM-DEVICE": [largeVerticalDistance, horizontalDistance] 
 */
var connectionDistanceMapping = {
	"VM-ACCOUNT": [-verticalDistance, 0],
	"VM-NC": [shortVerticalDistance, 0],
	"NC-CLUSTER": [shortVerticalDistance, 0],
	"VM-DEVICE": [largeVerticalDistance, horizontalDistance],
	"DEVICE-CLUSTER": [-108, horizontalDistance],
	"VM-VIP": [verticalDistance, -horizontalDistance],
	"DEVICE-SNAPSHOT": [shortVerticalDistance, shortHorizontalDistance]
}

/**
 * 根节点位置
 */
rootPosition = [220, 360];
rootTop = rootPosition[0];
rootLeft = rootPosition[1];

var parentDiv = null;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////



function drawtopo_asyn(vmName, regionNo, parentDivId) {
	
	parentDiv = $('#'+parentDivId);
	
	var vmInfoReq = {
		'url': 	httpPrefix + '/controllers/vm/obtainVmData',
		'params' : {
			'vm_name' : vmName,
			'region_no': regionNo
		}
	};
	var vmjq = doAjaxRequest(vmInfoReq);
	var vmInfoLoadedAfter = function(resultJson) {
		
		vmNodeData = resultJson.result.data;
		if (vmNodeData == null) {
			alert('没有找到VM的相关信息!');
			return ;
		}
		vmNode = createDiv(vmNodeData);
		
		vmDivId = obtainNodeDivId(vmNodeData);
		$('#'+vmDivId).css('top', rootTop + 'px');
		$('#'+vmDivId).css('left', rootLeft + 'px');
		
		linkAccount(vmNodeData, vmName, regionNo);
		
		ncId = vmNodeData.data.nc_id;
		linkNc(vmNodeData, ncId, regionNo);
		
		// vmName = 'ATX-28n2dhdq8';
		linkDevices(vmNodeData, vmName);
		
		vmInnerIp = vmNodeData.data.vm_inner_ip;
		linkVips(vmNodeData, vmInnerIp);
		
	};
	vmjq.done(vmInfoLoadedAfter);
}

function linkAccount(vmNodeData, vmName, regionNo) {
	var accountInfoReq = {
		'url': httpPrefix + '/controllers/vm/obtainAliyunAccountInfo',
		'params': {
			'vm_name' : vmName,
			'region_no': regionNo
		}
	};
	var accountjq = doAjaxRequest(accountInfoReq);
	accountjq.done(function(resultJson) {
		
		// for test
		// resultJson = {"result":{"msg":"successful","code":200,"data":{"errorCode":null,"errorMsg":null,"aliyunID":"it-cloudpc@alibaba-inc.com","kp":null},"success":true}};
		
		if (resultJson.result.success) {
			accountData = resultJson.result.data;
			accountNodeData = createAccountData(accountData);
			accountNode = createDiv(accountNodeData);
			cacheConnections(vmNodeData, accountNodeData, obtainConnectionDirections(vmNodeData, accountNodeData));
			reconnectAll(connections);
		}
		else {
			$('#error').append('获取关联云账号信息失败！');
		}
	}).fail(function() {
		$('#error').append('获取关联云账号信息失败！ ');
	});
	
}


function linkNc(vmNodeData, ncId, regionNo) {
	var ncInfoReq = {
		'url': 	httpPrefix + '/controllers/nc/listNc',
		'params': {
			'region_no': regionNo,
			'nc_id': ncId,
			'start': 0,
			'page': 1,
			'limit': 1
		}
	};
	var ncjq = doAjaxRequest(ncInfoReq);
	ncjq.done(function(resultJson) {
		ncDataList = resultJson.data;
		if (ncDataList.length > 0) {
			ncData = ncDataList[0];
			ncNodeData = createNcData(ncData);
			ncNode = createDiv(ncNodeData);
			cacheConnections(vmNodeData, ncNodeData, obtainConnectionDirections(vmNodeData, ncNodeData));
			
			ncClusterNodeData = createNcClusterData(ncData);
			ncClusterNode = createDiv(ncClusterNodeData);
			cacheConnections(ncNodeData, ncClusterNodeData, obtainConnectionDirections(ncNodeData, ncClusterNodeData));
			reconnectAll(connections);
		}
		else {
			$('#error').append('获取关联NC实体失败！');
		}
	}).fail(function() {
		$('#error').append('获取关联NC实体失败！');
	});
}


function linkDevices(vmNodeData, vmName) {
	var deviceInfoReq = {
		'url' :  httpPrefix + '/controllers/disk/search',
		'params': {
			'vmName': vmName
		}
	}
	var regionPeNickName = vmNodeData.data.region_pe_nickname;
	var devicejq = doAjaxRequest(deviceInfoReq);
	devicejq.done(function(resultJson) {
		
		total = resultJson.data.total;
		if (total > 0) {
			devices = resultJson.data.list;
			
			for (var i=0; i<total; i++) {
				
				deviceData = devices[i];
				deviceData['regionPeNickName'] = regionPeNickName;
				deviceNodeData = createDeviceData(deviceData);
				deviceNodeDataMapping[deviceData.instanceId] = deviceNodeData;
				deviceNode = createDiv(deviceNodeData);
				cacheConnections(vmNodeData, deviceNodeData, obtainConnectionDirections(vmNodeData, deviceNodeData));
				
				deviceClusterNodeData = createDeviceClusterData(deviceData);
				if (deviceClusterNodeData != null) {
					deviceClusterNode = createDiv(deviceClusterNodeData);
					cacheConnections(deviceNodeData, deviceClusterNodeData, obtainConnectionDirections(deviceNodeData,deviceClusterNodeData));
				}
				
				linkSnapshot(devices[i].aliUid, devices[i].instanceId, deviceNodeData);
			}
			reconnectAll(connections);
		}
		else {
			$('#error').append('该VM没有关联的磁盘实体！');
		}
	}).fail(function() {
		$('#error').append('获取关联磁盘实体失败！');
	});
}


function linkVips(vmNodeData, vmInnerIp) {
	
	var vipInfoReq = {
		'url': httpPrefix + '/controllers/slbvip/listVip',
		'params': {
			'realserver_param': vmInnerIp,
			'start': 0,
			'page': 1,
			'limit': 100
		}
	};
	var vipjq = doAjaxRequest(vipInfoReq);
	vipjq.done(function(resultJson) {
		
		total = resultJson.total;
		vips = resultJson.data;
		if (total > 0) {
			for (var j=0; j<total; j++) {
				var vipInfo = vips[j];
				vipNodeData = createVipData(vipInfo);
				vipNode = createDiv(vipNodeData);
				cacheConnections(vmNodeData, vipNodeData, obtainConnectionDirections(vmNodeData,vipNodeData));
			}
			reconnectAll(connections);
		}
		else {
			$('#error').append('该VM没有关联的VIP实体！');
		}
	}).fail(function() {
		$('#error').append('获取关联VIP实体失败！');
	});
	
}

function linkSnapshot(aliUid, diskId, deviceNodeData) {
	
	var snapshotInfoReq = {
		'url': httpPrefix + '/controllers/snapshot/search',
		'params': {
			'aliUid': aliUid,
			'diskId': diskId
		}
	};
	var snapshotjq = doAjaxRequest(snapshotInfoReq);
	snapshotjq.done(function(resultJson) {
		
		total = resultJson.total;
		if (total > 0) {
			snapshotDataList = resultJson.list;
			snapshotData = {
				'total': total,
				'aliUid': aliUid,
				'diskId': diskId
			};
			snapshotNodeData = createSnapshotData(snapshotData);
			snapshotNode = createDiv(snapshotNodeData);
			cacheConnections(deviceNodeData, snapshotNodeData, obtainConnectionDirections(deviceNodeData, snapshotNodeData));
			reconnectAll(connections);
		}
		else {
			$('#error').append('磁盘 ' + diskId + ' 没有关联的快照实体！');
		}
	}).fail(function() {
		$('#error').append('磁盘' + diskId + ' 获取关联快照实体失败！');
	});
}


/**
 * createXXXData
 * 创建拓扑图所使用的节点数据  
 */
function createVmData(vmData) {
	return {
		'type': 'VM',
		'key': vmData.vm_name,
		'data': vmData
	}
}

function createNcData(ncData) {
	return {
		'type': 'NC',
		'key': ncData.ip,
		'data': ncData
	}
}

function createNcClusterData(ncData) {
	return {
		'type': 'CLUSTER',
		'key': ncData.regionPeNickName,
		'data': {
			'regionNo': ncData.regionNo,
			'regionNickName': ncData.regionNickName,
			'regionPeNickName': ncData.regionPeNickName
		}
	}
}

function createDeviceData(deviceData) {
	return {
		'type': 'DEVICE',
		'key': deviceData.instanceId,
		'data': deviceData
	}
}


function createDeviceClusterData(deviceData) {
	if (deviceData.regionNo == null) {
		return null;
	}
	return {
		'type': 'CLUSTER',
		'key': deviceData.regionNo + '_' + deviceData.izNo + '_' + deviceData.zoneNo,
		'data': {
			'regionNo': deviceData.regionNo,
			'izNo': deviceData.izNo,
			'zoneNo': deviceData.zoneNo
		}
	}
}

function createSnapshotData(snapshotData) {
	return {
		'type': 'SNAPSHOT',
		'key': snapshotData.diskId + '_' + snapshotData.aliUid,
		'data': snapshotData 
	}
}

function createSnapshotClusterData(snapshotData) {
	if (snapshotData.regionNo == null) {
		return null;
	}
	return {
		'type': 'CLUSTER',
		'key': snapshotData.regionNo,
		'data': {
			'regionNo': snapshotData.regionNo
		}
	}
}

function createVipData(vipData) {
	return {
		'type': 'VIP',
		'key': vipData.vipAddress,
		'data': vipData
	}
}

function createAccountData(accountData) {
	if (accountData.aliyunID == null) {
		return null;
	}
	return {
		'type': 'ACCOUNT',
		'key': accountData.aliyunID,
		'data': accountData
	}
}

/**
 * 缓存起始节点 beginNode 和终止节点 endNode 的连接关系 
 */
function cacheConnections(beginNode, endNode, directions) {
	
	computeLayout(beginNode, endNode);
	
	var startPoint = obtainNodeDivId(beginNode) + directions[0];
	var endPoint = obtainNodeDivId(endNode) + directions[1];
	connections.push([startPoint, endPoint]);
}


/**
 * 计算节点位置及布局
 */
function computeLayout(beginNode, endNode) {
	
	var beginDivId = obtainNodeDivId(beginNode);
	var endDivId = obtainNodeDivId(endNode);
	var beginNodeType = beginNode.type;
	var endNodeType = endNode.type;
	
	beginNodeTop = $('#'+beginDivId).offset().top;
	beginNodeLeft = $('#'+beginDivId).offset().left;
	
	var key = beginNodeType + '-' + endNodeType + '-' + beginNode.key;
	if (cacheConnectionData[key] == null) {
		cacheConnectionData[key] = -1;
	}
	else {
		cacheConnectionData[key] = cacheConnectionData[key]+1;
	}
	connNum = cacheConnectionData[key];
	
	var typeKey = beginNodeType + '-' + endNodeType;
	var relDistance = connectionDistanceMapping[typeKey];
	var relVertiDistance = relDistance[0];
	var relHoriDistance = relDistance[1];
	
	switch (beginNodeType) {
	    case VM_TYPE:
	    	if (endNodeType == VIP_TYPE) {
	    		endNodePosition = [beginNodeTop+connNum*relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	else if (endNodeType == DEVICE_TYPE) {
	    		endNodePosition = [beginNodeTop+connNum*relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	else if (endNodeType == NC_TYPE) {
	    		endNodePosition = [beginNodeTop+relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	else if (endNodeType == ACCOUNT_TYPE) {
	    		endNodePosition = [beginNodeTop+relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	break;
	    case DEVICE_TYPE:
	    	if (endNodeType == CLUSTER_TYPE) {
	    		endNodePosition = [beginNodeTop+relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	else if (endNodeType == SNAPSHOT_TYPE) {
	    		endNodePosition = [beginNodeTop+relVertiDistance, beginNodeLeft+(connNum+1)*relHoriDistance];
	    	}
	    	break;
	    case VIP_TYPE:
	    	break;
	    case NC_TYPE:
	    	if (endNodeType == CLUSTER_TYPE) {
	    		endNodePosition = [beginNodeTop+relVertiDistance, beginNodeLeft+relHoriDistance];
	    	}
	    	break;
	    case SNAPSHOT_TYPE:
	    default: 
	    	break;
	}
	
	$('#'+endDivId).css('top', endNodePosition[0] + 'px');
	$('#'+endDivId).css('left', endNodePosition[1] + 'px');
	
	addEndPoints(beginDivId, beginNodeType);
	addEndPoints(endDivId, endNodeType);
}

/**
 * 为节点添加用于连线的附着点
 * @param nodeDivId  节点的 DIV ID
 * @param type  节点类型
 */
function addEndPoints(nodeDivId, type) {
	var startAttachedPoints = entityEndPointsMapping[type][0];
	var endAttachedPoints = entityEndPointsMapping[type][1];
	topoDrawUtil.addEndpoints(nodeDivId, startAttachedPoints, endAttachedPoints);
}


/**
 * 连接所有连线
 */
function reconnectAll(connections) {
	
	var i=0;
	for (i=0; i<connections.length; i++) {
		jsPlumb.connect({uuids:connections[i], editable: false}); 
	}
	// 使所有拓扑节点均为可拉拽的					
	jsPlumb.draggable(jsPlumb.getSelector(".node"), { grid: [5, 5] });
}


/**
 * div id cache , avoid duplicated div.
 * {'divId': 'divStr'}
 */
divIdCache = {};

/**
 * 为节点数据创建节点\附着点并返回节点的DIV
 */
function createDiv(metaNode) {
	var clickUrl = '';
	var display = '';
	var type = metaNode.type;
	var regionPeNickname = '';
	if (metaNode.data != null) {
		regionPeNickname = metaNode.data.regionPeNickName;
	}
	
	nodeDivId = obtainNodeDivId(metaNode);
	
	if (divIdCache[nodeDivId] != null) {
		// 该节点要移动到新的位置, 因此原来的附着点要去掉
		topoDrawUtil.removeAllEndPoints(nodeDivId);
		return divIdCache[nodeDivId];
	}
	
	switch(type.toUpperCase()) {
	    case VM_TYPE:
	    	clickUrl = httpPrefix + '/framehtml/vm_monitor.html?vm_name=' + metaNode.key + '&data='+JSON.stringify(metaNode.data).replace(/\"/g,"'");
	    	display = metaNode.key;
	    	break;
	    case DEVICE_TYPE:
	    	displayDevice1 = metaNode.data.instanceId;
	    	is_system = (metaNode.data.mountPoint == "/dev/xvda");
	    	clickDeviceUrl2 = httpPrefix + '/framehtml/device_monitor.html?disk_id=' + metaNode.data.houyiDiskId + '&yaochi_disk_id='+metaNode.data.instanceId+'&region_pe_nickname='+regionPeNickname;
	    	displayDevice2 = metaNode.data.houyiDiskId;
	    	break;
	    case NC_TYPE:
	    	var regionNo = metaNode.data.regionNo;
	    	clickUrl = httpPrefix + '/framehtml/nc_monitor.html?nc_ip=' + metaNode.key + '&region_pe_nickname='+regionPeNickname + '&region_no='+regionNo;
	    	display = metaNode.key;
	    	break;
	    case VIP_TYPE:
	    	display = metaNode.key + ':' + metaNode.data.port;
	    	clickUrl = httpPrefix + '/framehtml/vip_monitor.html?vip=' + display + '&instanceId='+metaNode.data.instanceId;
	    	break;
	    case SNAPSHOT_TYPE:
	    	snapshotDisplay1 = '快照总数: ' + metaNode.data.total;
	    	clickUrl = httpPrefix + '/ecs_module/ecs.html?type=snapshot&diskId='+metaNode.data.diskId+'&aliUid='+metaNode.data.aliUid; 
	    	break;
	    case CLUSTER_TYPE:
	    	display = metaNode.key.split('_')[0]; 
	    	if (metaNode.data.izNo != null) {
	    		display += '<br/>可用区: ' + metaNode.data.izNo;
	    	}
	    	if (metaNode.data.zoneNo != null) {
	    		display += '<br/>ZoneNo: ' + metaNode.data.zoneNo;
	    	}
	    	break;
	    case AVZ_TYPE:
	    case ACCOUNT_TYPE:
	    	display = metaNode.key;
	    	break;
	    default:
	    	break;
	} 
	
	if (type == VM_TYPE || type == NC_TYPE || type == VIP_TYPE) {
		divStr =  '<div class="node biggerNode" id="' + nodeDivId + '"><strong>' 
                + metaNode.type + '<br/><a href="' + clickUrl + '" target="_blank">' + display + '</a><br/></strong></div>';
	}
	else if (type == DEVICE_TYPE){
		divStr =  '<div class="node biggerNode" id="' + nodeDivId 
		+ (is_system?('" style="color:red;"'):('"'))
		+ '><strong>' + metaNode.type + '<br/>' + displayDevice1
		+ (is_system?'(系统盘)':'(数据盘)')
		+'<br/><a href="' + clickDeviceUrl2 + '" target="_blank">'
		+ displayDevice2 + '</a><br/></strong></div>';
	}
	else if (type == SNAPSHOT_TYPE) {
		divStr =  '<div class="node biggerNode" id="' + nodeDivId + '"><strong>' 
        + metaNode.type + '<br/>' + snapshotDisplay1 + '<br/><a href="' + clickUrl + '" target="_blank">详情</a><br/></strong></div>';
	}
	else {
		divStr = '<div class="node biggerNode" id="' + nodeDivId + '"><strong>' 
                + metaNode.type + '<br/>' + display + '<br/></strong></div>';
	}
	parentDiv.append(divStr);
	
	divIdCache[nodeDivId] = divStr;
	return divStr;
}

function obtainConnectionDirections(srcNodeData, destNodeData) {
	var key = srcNodeData.type + '-' + destNodeData.type;
	var startDirection = connectionDirectionMapping[key][0];
	var endDirection = connectionDirectionMapping[key][1];
	return [startDirection, endDirection];
}

/**
 * 生成节点的 DIV id
 * divId = nodeType.toUpperCase + "_" + key
 * key 可能为 IP , 其中的 . 将被替换成 ZZZ , 因为 jquery id 选择器中 . 属于转义字符.
 * eg. {type: 'VM', key: '1.1.1.1' }, divId = 'VM_1ZZZ1ZZZ1ZZZ1'
 */
function obtainNodeDivId(metaNode) {
	if (metaNode.type == VIP_TYPE) {
		return metaNode.type.toUpperCase() + '_' + transferKey(metaNode.key) + '_' + metaNode.data.port;
	}
	return metaNode.type.toUpperCase() + '_' + transferKey(metaNode.key);
}

function transferKey(key) {
	return key.replace(/\./g, 'ZZZ').replace(/\@/g,'YYY');
}

function revTransferKey(value) {
	return value.replace(/ZZZ/g, '.').replace('/YYY/g','@');
}