package cc.lovesq.parser;

import cc.lovesq.model.Student;
import cc.lovesq.result.BaseResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BaseParser {

    static final ObjectMapper mapper = new ObjectMapper();

    static final Gson gson = new Gson();

    static {
        try {
            // 为保持对象版本兼容性,忽略未知的属性
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 序列化的时候，跳过null值
            mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//      mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mapper.setDateFormat(fmt);
        } catch (Exception e) {
            //TODO
        }
    }

    public static <T> BaseResult<T> parse(String responseStr, Class<T> type) {
        System.out.println(responseStr);
        BaseResult<T> result = new BaseResult<T>();
        try {
            BaseResult br = mapper.readValue(responseStr, new TypeReference<BaseResult<T>>() {});
            if (br.isSuccess()) {
                result.setData(mapToObject(br.getData(), type));
            }
            else {
                result.setCode(br.getCode());
                result.setMsg(br.getMsg());
            }
            return result;
        } catch (Exception ex) {
            System.err.println("Failed to Parse " + responseStr + " : " + ex.getMessage());
            result.setCode(-1);
            result.setMsg("response parse error");
            result.setData(null);
        }
        return result;
    }

    public static <T> BaseResult<List<T>> parseComplex(String responseStr, Class<T> cls) {
        System.out.println(responseStr);
        BaseResult<List<T>> result = new BaseResult<List<T>>();
        try {
            Type type = new TypeToken<BaseResult<List<T>>>(){}.getType();
            Object obj = gson.fromJson(responseStr, type);
            BaseResult<List<T>> br = (BaseResult<List<T>>) obj;
            List<T> data = br.getData();
            if (br.isSuccess()) {
                List<T> datas = new ArrayList<T>();
                for (T t: data) {
                    datas.add(mapToObject(t, cls));
                }
                result.setData(datas);
            }
            return result;
        } catch (Exception ex) {
            System.err.println("Failed to Parse " + responseStr + " : " + ex.getMessage());
            result.setCode(-1);
            result.setMsg("response parse error");
            result.setData(null);
        }
        return result;
    }



    private static <T> T mapToObject(Object map, Class<T> cls) {
        try {
            return mapper.readValue(mapper.writeValueAsString(map), cls);
        } catch (Exception ex) {
            System.out.println("Error Map to Object: " + ex.getMessage());
            return null;
        }
    }

    public static class BaseParserTester {
        public static void main(String[] args) {
            String respStr = "{\"code\": 200, \"msg\": \"success\", \"data\": {\"age\": 32, \"name\": \"shuqin\", \"address\": {\"detail\": \"中国湖北天门\"}}}";
            BaseResult<Student> result = BaseParser.parse(respStr, Student.class);
            System.out.println(result);

            String respStr2 = "{\"code\": 200, \"msg\": \"success\", \"data\": [{\"age\": 32, \"name\": \"shuqin\", \"address\": {\"detail\": \"中国湖北天门\"}}, "
                    + "{\"age\": 28, \"name\": \"ni\", \"address\": {\"detail\": \"中国湖北宜昌\"}}]}";
            BaseResult<List<Student>> listResult = BaseParser.parseComplex(respStr2, Student.class);
            if (result.isSuccess()) {
                System.out.println(listResult.getData());
            }

        }
    }
}
