package zzz.study.utils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuqin on 16/5/4.
 */
public class BaseTool {

    public static final String ALLIN_PROJ_PATH = System.getProperty("user.dir");
    public static final String ALLIN_PROJ_PATH_SRC = ALLIN_PROJ_PATH + "/src/main/java";
    public static final String methodNameRegexStr = "\\s*(?:\\w+\\s+)?\\w+<?\\w+>?\\s+(\\w+)";
    public static final String singleParamRegexStr = "[^,]*\\w+<?\\w+>?\\s+(\\w+)\\s*";
    public static final String simpleMethodSignRexStr = methodNameRegexStr + "\\(" + singleParamRegexStr + "\\)\\s*;\\s*";
    public static final String twoParamMethodSignRegStr = methodNameRegexStr + "\\(" + singleParamRegexStr + "," + singleParamRegexStr + "\\);\\s*";
    //val generalParamMethodSignRegStr = methodNameRegexStr + "\\((" + singleParamRegexStr + "(?:," + singleParamRegexStr + ")*)\\);\\s*";
    public static final String generalParamMethodSignRegStr = methodNameRegexStr + "\\((.*)\\);\\s*";
    public static final Pattern singleParamPattern = Pattern.compile(singleParamRegexStr);
    public static final Pattern generalParamMethodSignPattern = Pattern.compile(generalParamMethodSignRegStr);

    public static String strip(String origin, String toStrip) {
        int index = origin.indexOf(toStrip);
        if (index == -1) {
            return origin;
        } else {
            return origin.substring(0, index);
        }
    }

    public static String firstToLower(String doClassName) {
        return "" + String.valueOf(doClassName.charAt(0)).toLowerCase() + doClassName.substring(1);
    }

    public static String firstToUpper(String fieldName) {
        return "" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }

    public static String getBizType(String packageName) {
        int preIndex = packageName.indexOf("common.");
        if (preIndex == -1) {
            return "";
        }
        String bizPart = packageName.substring(preIndex + "common.".length());
        int bizIndex = bizPart.indexOf(".");
        if (bizIndex == -1) {
            return "";
        }
        return bizPart.substring(0, bizIndex);
    }

    public static String indent(int n) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < n; i++) {
            spaces.append(' ');
        }
        return spaces.toString();
    }

    public static String readFile(String filePath) {
        String fileContent = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            StringBuilder doClsContentBuilder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                doClsContentBuilder.append(line + "\n");
            }
            fileContent = doClsContentBuilder.toString();
        } catch (IOException ioe) {
            System.err.println("Failed to Read File " + filePath + " : " + ioe.getMessage());
        }
        return fileContent;
    }

    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ioe) {
            System.err.println("Failed to Read File " + filePath + " : " + ioe.getMessage());
        }
        return lines;
    }

    public static void writeFile(String filePath, String fileContent) {
        try {
            BufferedWriter modelFileW = new BufferedWriter(new FileWriter(new File(filePath)));
            modelFileW.write(fileContent);
            modelFileW.close();
        } catch (IOException ioe) {
            System.err.println("Failed to write Java File " + filePath + " : " + ioe.getMessage());
        }
    }

    public static List<String> fetchAllFiles(String path) {
        List<String> fetchedFiles = new ArrayList<String>();
        fetchFiles(path, fetchedFiles);
        return fetchedFiles;
    }

    public static void fetchFiles(String path, List<String> fetchedFiles) {
        File[] dirAndfiles = (new File(path)).listFiles();
        if (dirAndfiles != null && dirAndfiles.length > 0) {
            for (File file : dirAndfiles) {
                if (file.isFile()) {
                    fetchedFiles.add(file.getAbsolutePath());
                }
            }

            for (File file : dirAndfiles) {
                if (file.isDirectory()) {
                    fetchFiles(file.getAbsolutePath(), fetchedFiles);
                }
            }
        }
    }

    public static List<Class> getClasses(String path) {
        List<String> files = fetchAllFiles(path);
        List<Class> result = new ArrayList<Class>();
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        for (String fname : files) {
            String fn = fname.replace(ALLIN_PROJ_PATH_SRC + "/", "").replace(".java", "");
            String qualifiedClassName = fn.replaceAll("/", ".");
            try {
                Class<?> cls = cld.loadClass(qualifiedClassName);
                result.add(cls);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Failed to load class " + qualifiedClassName + " : " + cnfe.getMessage());
            }

        }
        return result;
    }

    /**
     * 从方法签名中解析出方法名称\参数列表
     *
     * @param methodSign 方法签名
     * @return ["方法名称", "参数1, 参数2, ..., 参数N"]
     */
    public static List<String> parseMethod(String methodSign) {

        Matcher m = generalParamMethodSignPattern.matcher(methodSign);
        String methodName = "";
        String args = "";
        List<String> parsed = new ArrayList<String>();

        if (m.find()) {
            methodName = m.group(1);
            args = m.group(2);
        } else {
            return Arrays.asList(new String[]{"", ""});
        }
        parsed.add(methodName);
        String[] params = args.split(",");
        for (String param : params) {
            String arg = extractArgName(param);
            parsed.add(arg);
        }
        return parsed;
    }

    public static String extractArgName(String singleParam) {
        Matcher m = singleParamPattern.matcher(singleParam);
        return m.find() ? m.group(1) : "";
    }

    public static List<String> transform(List<String> parsed) {
        if (parsed == null || parsed.isEmpty()) {
            return parsed;
        }
        List<String> result = new ArrayList<String>();
        result.add(parsed.get(0));
        if (parsed.size() == 2) {
            result.add(parsed.get(1));
        } else {
            int size = parsed.size();
            StringBuilder argBuilder = new StringBuilder();
            for (int i = 1; i < size - 1; i++) {
                argBuilder.append(parsed.get(i) + ", ");
            }
            argBuilder.append(parsed.get(size - 1));
            result.add(argBuilder.toString());
        }
        return result;
    }

    public static void testParseMethod() {
        Map<String, List<String>> testMethods = new HashMap<String, List<String>>();
        testMethods.put(" List<OrderDO> queryOrder(int kdtId); ", Arrays.asList(new String[]{"queryOrder", "kdtId"}));
        testMethods.put(" List<OrderDO> queryOrder( int kdtId ); ", Arrays.asList(new String[]{"queryOrder", "kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"kdtId\") int kdtId); ", Arrays.asList(new String[]{"queryOrder", "kdtId"}));
        testMethods.put(" List<OrderDO> queryOrder(List<String> orderNos); ", Arrays.asList(new String[]{"queryOrder", "orderNos"}));
        testMethods.put(" List<OrderDO> queryOrder(@Param(\"orderNos\") List<String> orderNos); ", Arrays.asList(new String[]{"queryOrder", "orderNos"}));
        testMethods.put(" OrderDO queryOrder(String orderNo, Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNo, kdtId"}));
        testMethods.put(" OrderDO queryOrder(String orderNo, @Param(\"kdtId\") Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNo, kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"orderNo\") String orderNo, Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNo, kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"orderNo\") String orderNo, @Param(\"kdtId\") Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNo, kdtId"}));
        testMethods.put(" OrderDO queryOrder(List<String> orderNos, Integer kdtId); \n", Arrays.asList(new String[]{"queryOrder", "orderNos, kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNos, kdtId"}));
        testMethods.put(" OrderDO queryOrder(List<String> orderNos, @Param(\"kdtId\") Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNos, kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, @Param(\"kdtId\") Integer kdtId); ", Arrays.asList(new String[]{"queryOrder", "orderNos, kdtId"}));
        testMethods.put(" OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, @Param(\"page\") Integer page, @Param(\"pageSize\") Integer pageSize); ", Arrays.asList(new String[]{"queryOrder", "orderNos, page, pageSize"}));

        Set<Map.Entry<String, List<String>>> entries = testMethods.entrySet();
        for (Map.Entry entry : entries) {
            String methodSign = (String) entry.getKey();
            List<String> expected = (List<String>) entry.getValue();
            List<String> actual = transform(parseMethod(methodSign));
            if (!assertListEqual(actual, expected)) {
                System.err.println("failed: " + methodSign);
                System.err.println("expected: " + expected);
                System.err.println("actual: " + actual);
            }
        }

        System.out.println("Test ParseMethod passed");

    }

    public static boolean assertListEqual(List<String> list1, List<String> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if ((list1 == null && list2 != null) || (list1 != null && list2 == null)) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

}
