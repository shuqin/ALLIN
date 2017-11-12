package cc.lovesq.study.test.datastructure;

import com.jayway.jsonpath.JsonPath;

import org.junit.Test;

import cc.lovesq.study.test.CommonForTest;
import zzz.study.utils.JsonUtil;

/**
 * Created by shuqin on 17/11/12.
 */
public class JsonUtilTest extends CommonForTest {

  String json = "{\"code\":200,\"msg\":\"ok\",\"list\":[{\"id\":20,\"no\":\"1000020\",\"items\":[{\"name\":\"n1\",\"price\":21,\"infos\":{\"feature\":\"\"}}]}],\"metainfo\":{\"total\":20,\"info\":{\"owner\":\"qinshu\",\"parts\":[{\"count\":13,\"time\":{\"start\":1230002456,\"end\":234001234}}]}}}";

  // 常用的 Json Path 可以缓存起来重用，类似正则里的 Pattern p = Pattern.compile('regexString')
  JsonPath codePath = JsonPath.compile("$.code");
  JsonPath totalPath = JsonPath.compile("$.metainfo.total");

  @Test
  public void testReadVal() {

    eq(null, JsonUtil.readVal(null, "code"));
    eq(null, JsonUtil.readVal(json, null));
    eq("200", JsonUtil.readVal(json, "code"));
    eq("20", JsonUtil.readVal(json, "metainfo.total"));
    eq("qinshu", JsonUtil.readVal(json, "metainfo.info.owner"));
    eq(null, JsonUtil.readVal("invalid json", "code"));
    eq(null,JsonUtil.readVal(json, "metainfo.extra.feature"));

    eq(null, JsonUtil.readValUsingJsonPath(null, "$.code"));
    eq(null, JsonUtil.readValUsingJsonPath(json, null));
    eq(200, codePath.read(json));
    eq(20, totalPath.read(json));
    eq("qinshu", JsonUtil.readValUsingJsonPath(json, "$.metainfo.info.owner"));
    eq("n1", JsonUtil.readValUsingJsonPath(json, "$.list[0].items[0].name"));
    eq(13, JsonUtil.readValUsingJsonPath(json, "$.metainfo.info.parts[0].count"));

  }

}
