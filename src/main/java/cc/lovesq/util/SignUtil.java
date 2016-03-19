package cc.lovesq.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import cc.lovesq.constants.LogConstant;
import cc.lovesq.invoker.constants.APIConstant;
/**
 * @Described：签名工具类
 * @author YHJ create at 2014年5月14日 下午5:07:16
 * @ClassNmae com.aliyun.houyi.compass.v2.util.SignUtil
 */
public class SignUtil {

	public static String sign(Map<String, String> paramMap,String md5_key) {
		// 根据KEY排序
		Map<String, String> sortedParamMap = null;
		if(paramMap instanceof TreeMap){
			sortedParamMap = paramMap;
		}else{
			sortedParamMap = new TreeMap<String, String>();
			// 参数集合
			for (Entry<String, String> entry : paramMap.entrySet()) {
				if (entry.getKey().equals(APIConstant.PARAM_SIGN)) {
					//略过sign和以t_开头的参数
					continue;
				}
				sortedParamMap.put(entry.getKey(),entry.getValue());
			}
		}
		// 最终结果
		List<String> allList = new LinkedList<String>();
		for (String value : sortedParamMap.values()) {
			allList.add(value);
		}
		// 添加签名key
		allList.add(md5_key);
		String signSource = StringUtils.join(allList.iterator(), APIConstant.PARAM_SPLIT);
		String signStr = Md5Util.encrypt(signSource , APIConstant.ENCODE);
		LogConstant.info("签名前："+signSource+",签名后:"+signStr);
		return signStr;
	}
}
