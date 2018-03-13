package zzz.study.tech.batchcall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Resource;

import zzz.study.function.refactor.StreamUtil;

/**
 * Created by shuqin on 18/3/12.
 *
 * 批量调用HTTP-Rest接口
 */
@Component("batchGetInfoService")
public class BatchGetInfoService {

  private static Logger logger = LoggerFactory.getLogger(BatchGetInfoService.class);

  @Resource
  private HttpClient httpClient;

  @Resource
  private MultiTaskExecutor multiTaskExecutor;

  public <T> Map<String,Object> batchGetAllInfo(WrapperHttpRestParam<T> wrapperHttpRestParam,
                                           Function<List<Map>, Map<String,Object>> buildResultMapFunc, int taskSize) {
    Map<String,Object> resultMap = new HashMap<>();
    try {
      if (wrapperHttpRestParam == null || CollectionUtils.isEmpty(wrapperHttpRestParam.getKeys())
          || StringUtils.isBlank(wrapperHttpRestParam.getUrl()) || wrapperHttpRestParam.getParamBuilderFunc() == null) {
        return new HashMap();
      }

      Function<WrapperListHandlerParam<T,Map>, List<Map>> handleBizDataFunc =
          (wrapperListHanderParam) -> batchGetInfo(wrapperHttpRestParam);

      WrapperListHandlerParam listHandlerParam = new WrapperListHandlerParam(wrapperHttpRestParam.getKeys(), handleBizDataFunc);
      List allInfo = multiTaskExecutor.exec(listHandlerParam, handleBizDataFunc, taskSize);
      if (CollectionUtils.isEmpty(allInfo)) {
        return new HashMap<>();
      }
      resultMap = buildResultMapFunc.apply(StreamUtil.map(allInfo, x -> (Map)x));
    } catch (Exception ex) {
      logger.error("failed to batchgetAllInfo for " + wrapperHttpRestParam, ex);
    }
    return resultMap;
  }

  public <T,R> List<R> batchGetInfo(WrapperHttpRestParam<T> wrapperCallThirdParam) {
    try {
      if (CollectionUtils.isEmpty(wrapperCallThirdParam.getKeys())) {
        return new ArrayList<>();
      }
      List<T> bizParam = wrapperCallThirdParam.getKeys();
      Map callParam = wrapperCallThirdParam.getParamBuilderFunc().apply(bizParam);
      String url = wrapperCallThirdParam.getUrl();
      String query = JSON.toJSONString(callParam);

      logger.info("begin to getBatchInfo for {} {}", url, callParam);
      JSONObject result = httpClient.query(query, url);
      return parseResult(result);
    } catch (Exception ex) {
      logger.error("failed to getBatchInfo for " + wrapperCallThirdParam, ex);
      return new ArrayList<>();
    }
  }

  private List parseResult(JSONObject result) {
    if (result != null && result.getBoolean("result") && result.getJSONObject("data").getBoolean("success")) {
      List dataList = (List) result.getJSONObject("data").get("data");
      return dataList;
    }
    return new ArrayList();
  }
}

