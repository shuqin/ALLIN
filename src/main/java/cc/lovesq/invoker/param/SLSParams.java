package cc.lovesq.invoker.param;

public class SLSParams {
	
	// sls API 
    /** sls的UserId **/
    public final static String KEY_SLS_USER_ID              = "UserId";
    public final static String KEY_SLS_PROJECT              = "Project";
    public final static String KEY_SLS_VERSION              = "APIVersion";
    public final static String KEY_SLS_ACCESS_KEY_ID        = "AccessKeyId";
    /** sls的Category **/
    public final static String KEY_SLS_CATEGORY             = "Category";
    /** sls的Topic **/
    public final static String KEY_SLS_TOPIC                = "Topic";
    /** sls的BeginTime **/
    public final static String KEY_SLS_BEGIN_TIME           = "BeginTime";
    /** sls的EndTime **/
    public final static String KEY_SLS_END_TIME             = "EndTime";
    /** sls的Query **/
    public final static String KEY_SLS_QUERY                = "Query";
    /** sls 的 Line **/
    public final static String KEY_SLS_LINES                 = "Lines";
    /** sls 的 Offset **/
    public final static String KEY_SLS_OFFSET               = "Offset";
    public final static String KEY_DATE              		 = "Date";
    /** sls 的 getData Servlet **/
    public final static String SLS_GETDATA_SIGN_KEY         = "GetData";
    public final static String SLS_GETDATA_SIGN_PARAM_NAME       = "Signature";
    public final static String SLS_GETDATA_SERVLETPATH       = "/GetData";
    /** sls 的 getMetaData Servlet **/
    public final static String SLS_GETDATAMETA_SERVLETPATH   = "/GetDataMeta";
    
    
    // SLS调用要提供给前端的参数名
    /** nc ip */
    public final static String KEY_NC_HOST                       = "host";
    /** cluster_name eg. AY41E */
    public final static String KEY_CLUSTER_NAME                  = "cluster_name";
    /** begin time */
    public final static String KEY_BEGIN_TIME                    = "begin_time";
    /** end time */
    public final static String KEY_END_TIME                      = "end_time";
    
    /** device_id */
    public final static String KEY_DEVICE_ID                   = "device_id";
    /** vm_name */
    public final static String KEY_VM_NAME                     = "vm_name";
    /** iface */
    public final static String KEY_IFACE                       = "iface";
    
}

