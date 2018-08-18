package cc.lovesq.controller;


import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cc.lovesq.model.NcLoadData;
import cc.lovesq.result.BaseResult;

import static cc.lovesq.util.DataUtil.retainNpoints;

@RestController
@RequestMapping("/api/perf")
public class NcPerfController {

  private Random random = new Random();

  /**
   * 获取 NC Load 性能数据
   */
  @RequestMapping(value = "/ncload")
  @ResponseBody
  public BaseResult save(Long beginTime, Long endTime, String ncIp) {
    Assert.notNull(ncIp, "NC IP 不能为空");

    List<NcLoadData> loadDatas = new ArrayList<NcLoadData>();
    long nowtimestamp = new Date().getTime();

    for (int i = 0; i < 100; i++) {
      NcLoadData ncloadData = new NcLoadData();
      ncloadData.setLdavg_1(retainNpoints(random.nextDouble() * 6));
      ncloadData.setLdavg_5(retainNpoints(random.nextDouble() * 6));
      ncloadData.setLdavg_15(retainNpoints(random.nextDouble() * 6));
      ncloadData.setTimestamp(nowtimestamp + 60 * 1000 * (i + 1));
      loadDatas.add(ncloadData);

    }

    Map<String, Object> result = new HashMap<>();
    result.put("list", loadDatas);
    result.put("total", loadDatas == null ? 0 : loadDatas.size());
    return BaseResult.succ(result);

  }

}