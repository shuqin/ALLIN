package cc.lovesq.constants;

public class Constants {
	
    /** 上传图片格式时的最大字节大小  1M*/
    public static final long MAX_PIC_BYTESIZE = 1000*1000*1000L;
    /** 上传视频格式时的最大字节大小  1M*/
    public static final long MAX_VIDEO_BYTESIZE = 300*1000*1000*1000L;
    
    public static final String SPLIT_STRING_X = "x";
    
    public static final String SPLIT_STRING_DOT = ".";
    
    /** 逗号分隔符. */
	public static final String SPLIT_COMMA = ",";
	
	/**
	 * 推广位上dm定向能设置的最大人群数
	 */
	public static final Integer ADGROUP_DMP_TARGET_CROWD_MAX_COUNT = 5;
	
	public static final String DSP_CONF_KEY_CAMPAIGN_MAX_COUNT="campaign_max_count";
	/**
	 * 查询分页最大
	 */
	public static final Integer BATCH_MAX_SIZE = 200;
	/**
	 * 计划下能关联的最多广告组
	 */
	public static final Integer MAX_CAMPAGIN_ADGROUP = 100;
	
	public static final Integer AMOUNT_OF_UNIT = 100;
	
}
