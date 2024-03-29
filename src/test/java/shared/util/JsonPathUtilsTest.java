package shared.util;

import cc.lovesq.CommonForTest;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import shared.utils.JsonPathUtils;

/**
 * jsonpath 用法学习
 *
 * 注意：这个不是单测，只是学习示例，单测必须用断言
 */
public class JsonPathUtilsTest extends CommonForTest {

    String json = "{\"error\":0,\"status\":\"success\",\"date\":\"2014-05-10\",\"extra\":{\"rain\":3,\"sunny\":2},\"recorder\":{\"name\":\"qin\",\"time\":\"2014-05-10 22:00\",\"mood\":\"good\",\"address\":{\"provice\":\"ZJ\",\"city\":\"nanjing\"}},\"results\":[{\"currentCity\":\"南京\",\"weather_data\":[{\"date\":\"周六今天,实时19\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/dayu.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/dayu.png\",\"weather\":\"大雨\",\"wind\":\"东南风5-6级\",\"temperature\":\"18\"},{\"date\":\"周日\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/zhenyu.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/duoyun.png\",\"weather\":\"阵雨转多云\",\"wind\":\"西北风4-5级\",\"temperature\":\"21~14\"}]}]}";

    String json2 = "{\"code\":200,\"msg\":\"ok\",\"list\":[{\"id\":20,\"no\":\"1000020\",\"items\":[{\"name\":\"n1\",\"price\":21,\"infos\":{\"feature\":\"\"}}]}],\"metainfo\":{\"total\":20,\"info\":{\"owner\":\"qinshu\",\"parts\":[{\"count\":13,\"time\":{\"start\":1230002456,\"end\":234001234}}]}}}";

    // 常用的 Json Path 可以缓存起来重用，类似正则里的 Pattern p = Pattern.compile('regexString')
    JsonPath codePath = JsonPath.compile("$.code");
    JsonPath totalPath = JsonPath.compile("$.metainfo.total");

    @Test
    public void testBasic() {
        test("$.error");   // 取 error 的值
        test("$.status");  // 取 status 的值
        test("$.date");    // 取 date 的值
    }

    @Test
    public void testBasicNested() {
        test("$.recorder.name");   // 取 recorder.name 的值
        test("$.recorder.mood");   // 取 recorder.mood 的值
        test("$.recorder.address.city");  // 取 recorder.address.city 的值
    }

    @Test
    public void testList() {
        test("$.results");        // 取 results 数组的值
        test("$.results[0].weather_data");      // 取 results 数组的第一个元素的 weather_data 数组的值
        test("$..weather_data.length()");       // 取 results 数组的所有元素的 weather_data 数组的长度（是数组）
        test("$.results[0].weather_data[1]");   // 取 results 数组的第二个元素的 weather_data[1] 的值
        test("$.results[0].weather_data[:1].weather");  // 取 results 数组的第一个元素的 weather_data[0].weather 的值
        test("$.results[0].weather_data[1].weather");   // 取 results 数组的第一个元素的 weather_data[1].weather 的值
        test("$..weather_data[1].weather");             // 取 results 数组的所有元素的含有 weather_data 子元素的 weather 的值（是数组）
        test("$.results[0].weather_data[1].temperature"); // 取 results 数组的第一个元素的 weather_data[1].temperature 的值
        test("$.results[0].weather_data[*].weather");     // 取 results 数组的第一个元素的 weather_data 数组所有元素的 weather 的值（是数组）
    }

    @Test
    public void testListFilter() {

        test("$.results[*].weather_data[?(@.date == '周日')]"); // 取 results 数组的所有元素的 weather_data 数组中 weather 在指定列表的 weather_data 元素（是数组）
        test("$.results[*].weather_data[?(@.weather in ['大雨'])]"); // 取 results 数组的所有元素的 weather_data 数组中 weather 在指定列表的 weather_data 元素（是数组）

        test("$.results[*].weather_data[?(@.temperature =~ /\\d+/i)]"); // 取 results 数组的所有元素的 weather_data 数组中满足 temperature 值为纯数字的 weather_data 元素（是数组）
        test("$.results[*].weather_data[?(@.temperature =~ /\\d+~\\d+/i)]"); // 取 results 数组的所有元素的 weather_data 数组中满足 temperature 值模式匹配 纯数字~纯数字 的 weather_data 元素（是数组）
        test("$.results[*].weather_data[?(@.temperature =~ /\\d+/i)].dayPictureUrl"); // 取 results 数组的所有元素的 weather_data 数组中满足 temperature 值为纯数字的 weather_data 元素的 dayPictureUrl 的值（是数组）
    }


    public void test(String jsonpath) {
        String value = JsonPathUtils.readValByJsonPath(json, jsonpath);
        System.out.println(jsonpath + " => " + value);
        System.out.println();
    }

    @Test
    public void testReadVal() {

        eq(null, JsonPathUtils.readVal(null, "code"));
        eq(null, JsonPathUtils.readVal(json2, null));
        eq("200", JsonPathUtils.readVal(json2, "code"));
        eq("20", JsonPathUtils.readVal(json2, "metainfo.total"));
        eq("qinshu", JsonPathUtils.readVal(json2, "metainfo.info.owner"));
        eq(null, JsonPathUtils.readVal("invalid json", "code"));
        eq(null, JsonPathUtils.readVal(json2, "metainfo.extra.feature"));

        eq(null, JsonPathUtils.readValUsingJsonPath(null, "code"));
        eq(null, JsonPathUtils.readValUsingJsonPath(json2, null));
        eq("200", JsonPathUtils.readValUsingJsonPath(json2, "code"));
        eq("20", JsonPathUtils.readValUsingJsonPath(json2, "metainfo.total"));
        eq("qinshu", JsonPathUtils.readValUsingJsonPath(json2, "metainfo.info.owner"));
        eq(null, JsonPathUtils.readValUsingJsonPath("invalid json", "code"));
        eq(null, JsonPathUtils.readValUsingJsonPath(json2, "metainfo.extra.feature"));

        eq(200, codePath.read(json2));
        eq(20, totalPath.read(json2));
        eq("qinshu", JsonPath.read(json2, "$.metainfo.info.owner"));
        eq("n1", JsonPath.read(json2, "$.list[0].items[0].name"));
        eq(13, JsonPath.read(json2, "$.metainfo.info.parts[0].count"));

    }
}
