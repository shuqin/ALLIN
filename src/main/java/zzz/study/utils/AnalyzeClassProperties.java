package zzz.study.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by lovesqcc on 16-1-20.
 */
public class AnalyzeClassProperties {

    private List<Class> classList;

    public AnalyzeClassProperties(List<Class> classList) {
        this.classList = classList;
    }

    public AnalyzeClassProperties(Class[] classes) {
        this.classList = Arrays.asList(classes);
    }

    public static void main(String[] args) {
        AnalyzeClassProperties ncp = new AnalyzeClassProperties(new Class[]{Thread.class});
        ncp.printClassPropertiesInfo(ncp.analyze());

    }

    public Map<String, Set<String>> analyze() {
        Map<String, Set<String>> classProperties = new HashMap<String, Set<String>>();
        for (Class cls : classList) {
            classProperties.put(cls.getName(), analyze(cls));
        }
        return classProperties;
    }

    public Set<String> analyze(Class cls) {
        Set<String> fieldNames = new HashSet<String>();
        for (Field f : cls.getDeclaredFields()) {
            fieldNames.add(getFieldInfo(f));
        }
        return fieldNames;
    }

    public void printClassPropertiesInfo(Map<String, Set<String>> classProperties) {
        Set<Map.Entry<String, Set<String>>> cpEntries = classProperties.entrySet();
        for (Map.Entry e : cpEntries) {
            System.out.println(e.getKey());
            System.out.println(e.getValue());
        }
    }

    private String getFieldInfo(Field f) {
        return "<" + f.getName() + "," + f.getType().getSimpleName() + ">";
    }

}
