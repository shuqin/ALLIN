package cc.lovesq.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * struts2 logging intercepter
 * 
 * @author kongyu
 * 
 */
//###
public class LoggingInterceptor extends HandlerInterceptorAdapter {/*

	protected final static Logger log = Logger.getLogger(LoggingInterceptor.class);
	protected final static Logger localLog = Logger.getLogger("AppLog");
	public static final String LOG_MAP_PARAMS = "params";
	private AppLogService logger;
	private Resource configLocation;
	private Map<String, LogConfig> config;
	private AppLogConverter converter = new DefaultAppLogConverter();

	public void setLogger(AppLogService logger) {
		this.logger = logger;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setConverter(AppLogConverter converter) {
		this.converter = converter;
	}

	public void init() {
		try {
			this.config = LogUtils.initConfig(configLocation.getInputStream());
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public String intercept(ActionInvocation invocation) throws Exception {

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		try {
			HttpSession session = request.getSession();
			List<Map<String, Object>> list = LocalLogBuffer.get();
			if (list != null && !list.isEmpty()) {
				
				List<AppLog> logs = new ArrayList<AppLog>(list.size());
				*//**
				for (Map<String, Object> map : list) {
					LoginUser user = ThreadLocalManager.getLoginUser();
					AppLog log = new AppLog();
					log.setOperId(user.getNetworkMid());
					log.setOperName(user.getNetworkMname());
					log.setOpSuberId(user.getOpId());
					log.setOpSuberName(user.getOpName());
					if(user.getOperType() == OperType.ADMIN ){
						log.setOperType("2");
					}
					else{
						log.setOperType("1");
					}
					log.setOperIP(IPRequestUtil.getIpAddress(request));
					log.setOpTime(System.currentTimeMillis());
					log.setOpType((String) map.get(AppLog.LOGS_COLUMN_NAME_OP_TYPE));
					log.setOpName(config.get(log.getOpType()).getName());
					log.setObjectId((String) map.get(AppLog.LOGS_COLUMN_NAME_OBJECTID));
					log.setObjParentId((String) map.get(AppLog.LOGS_COLUMN_NAME_OBJECT_PARENTID));
					log.setOpDetail(LogUtils.getDescByRult(config.get(log.getOpType()).getDescription(),
							(Map) map.get(LOG_MAP_PARAMS)));
					log.setObjectName((String) map.get(AppLog.LOGS_COLUMN_NAME_OBJECT_NAME));
					log.setObjType(config.get(log.getOpType()).getObjtype());
					log.setObjTypeName(config.get(log.getOpType()).getObjtypeName());
					log.setExtValue((Map<String, String>) map.get(AppLog.LOGS_COLUMN_NAME_EXTEND_VALUE));
					// log.setLogType(arr[4]);// 添加日志类型
					log = extendLog(session, log, map);
					logs.add(log);
				}
				**//*
				try {
					if (logger != null) {
						writeRemoteLog(logs);
					} else {
						writeLog(logs);
					}
				} catch (Exception e) {
					log.error(e, e);
					writeLog(logs);
				} finally {
					LocalLogBuffer.clear();
				}
			}
		} catch (RuntimeException e) {
			LocalLogBuffer.clear();
			throw e;
		} finally {
			LocalLogBuffer.clear();
		}
	}

	*//**
	 * 可以覆盖该方法获取到
	 * @param session
	 * @param log
	 * @param map
	 * @return
	 *//*
	protected AppLog extendLog(HttpSession session, AppLog log, Map<String, Object> map) {
		return log;
	}

	private void writeLog(List<AppLog> list) {
		for (AppLog log : list) {
			localLog.info(converter.fromTypedAppLog(log));
		}
	}

	private void writeRemoteLog(List<AppLog> list) throws AppLogException {
		for (AppLog log : list) {
			logger.createLog(log);
		}
	}
	

*/}
