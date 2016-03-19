package cc.lovesq.invoker.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Described：API常量
 * @author YHJ create at 2014年5月16日 下午3:41:56
 * @ClassNmae com.aliyun.houyi.compass.v2.constant.api.APIConstant
 */
public class APIConstant {
	
	/**
	 * 签名参数
	 */
	public final static String PARAM_SIGN = "sign";
	/**
	 * 分隔符参数
	 */
	public final static String PARAM_SPLIT = "|";
	/**
	 * 编码参数
	 */
	public final static String ENCODE = "UTF-8";
	/**
	 * 弹外生产云
	 */
	public final static int OUT_PRO = 1;
	/**
	 * 弹内生产云
	 */
	public final static int IN_PRO = 0;
	/**
	 * 弹外开发测试云
	 */
	public final static int IN_DEV = 2;
	
	public final static List<Integer> ENV_LIST = new ArrayList<Integer>();
	
	static{
		ENV_LIST.add(OUT_PRO);
		ENV_LIST.add(IN_PRO);
		ENV_LIST.add(IN_DEV);
	}

}
