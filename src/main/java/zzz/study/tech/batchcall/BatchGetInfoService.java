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
 * 批量获取第三方业务信息
 */
@Component("batchGetInfoService")
public class BatchGetInfoService {

  private static Logger logger = LoggerFactory.getLogger(BatchGetInfoService.class);

  @Resource
  private HttpClient httpClient;

  @Resource
  private MultiTaskExecutor multiTaskExecutor;

  public <T> List<Map> batchGetAllInfo(BatchHttpRestParam<T> BatchHttpRestParam, int taskSize) {
    try {
      if (BatchHttpRestParam == null || CollectionUtils.isEmpty(BatchHttpRestParam.getKeys())
          || StringUtils.isBlank(BatchHttpRestParam.getUrl()) || BatchHttpRestParam.getParamBuilderFunc() == null) {
        return new ArrayList();
      }

      Function<WrapperListHandlerParam<T,Map>, List<Map>> handleBizDataFunc =
          (wrapperListHanderParam) -> batchGetInfo(BatchHttpRestParam);

      WrapperListHandlerParam listHandlerParam = new WrapperListHandlerParam(BatchHttpRestParam.getKeys(), handleBizDataFunc);
      List<Map> allInfo = multiTaskExecutor.exec(listHandlerParam, handleBizDataFunc, taskSize);
      if (CollectionUtils.isEmpty(allInfo)) {
        return new ArrayList();
      }
      return allInfo;
    } catch (Exception ex) {
      logger.error("failed to batchgetAllInfo for " + BatchHttpRestParam, ex);
      return new ArrayList();
    }
  }

  public <T,R> List<R> batchGetInfo(BatchHttpRestParam<T> BatchHttpRestParam) {
    try {
      if (CollectionUtils.isEmpty(BatchHttpRestParam.getKeys())) {
        return new ArrayList<>();
      }
      List<T> bizParam = BatchHttpRestParam.getKeys();
      Map callParam = BatchHttpRestParam.getParamBuilderFunc().apply(bizParam);
      String url = BatchHttpRestParam.getUrl();
      String query = JSON.toJSONString(callParam);

      logger.info("begin to getBatchInfo for {} {}", url, callParam);
      JSONObject result = httpClient.query(query, url);
      return parseResult(result);
    } catch (Exception ex) {
      logger.error("failed to getBatchInfo for " + BatchHttpRestParam, ex);
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

