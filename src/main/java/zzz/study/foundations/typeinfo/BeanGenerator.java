package zzz.study.foundations.typeinfo;

import zzz.study.foundations.string.TextUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BeanGenerator {

    // bean 的包名
    private String packageName;

    // bean 的名称
    private String beanName;

    // bean 的属性集［名称及类型］
    private Map<String, Class<?>> beanAttrs;

    public BeanGenerator(String packageName, String beanName, Map<String, Class<?>> beanAttrs) {

        this.packageName = packageName;
        this.beanName = beanName;

        if (beanAttrs != null) {
            this.beanAttrs = beanAttrs;
        } else {
            this.beanAttrs = new LinkedHashMap<String, Class<?>>();
        }
    }

    public static void main(String[] args) {
        String path = "./src/foundations/string/";
        Map<String, Class<?>> beanAttrs = new LinkedHashMap<String, Class<?>>();
        beanAttrs.put("id", Integer.class);
        beanAttrs.put("name", String.class);
        BeanGenerator bg = new BeanGenerator("foundations.string", "AutoBean", beanAttrs);
        bg.generateBean(path);
        beanAttrs.put("bigNum", BigDecimal.class);
        beanAttrs.put("printStream", PrintStream.class);
        bg.generateBean(path);

        try {
            System.out.println(bg.instantiateBean());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addAttr(String propertyName, Class<?> type) {
        beanAttrs.put(propertyName, type);
    }

    public void removeAttr(String propertyName) {
        if (beanAttrs.get(propertyName) != null) {
            beanAttrs.remove(propertyName);
        }
    }

    private void appendImport(StringBuilder sb, Class<?> type, int n) {
        String importString = "import " + type.getCanonicalName() + "; \n";
        sb.insert(n, importString);
    }

    private void appendAttr(StringBuilder sb, Class<?> type, String propertyName) {
        String attr = "\tprivate " + type.getSimpleName() + " " + propertyName + ";\n\n";
        sb.append(attr);
    }

    private void appendSetter(StringBuilder sb, Class<?> type, String propertyName) {
        String setter = "\tpublic void set" + TextUtil.capitalFirst(propertyName) +
                "(" + type.getSimpleName() + " " + propertyName + ") { " +
                "this." + propertyName + " = " + propertyName + "; } \n\n";
        sb.append(setter);
    }

    private void appendGetter(StringBuilder sb, Class<?> type, String propertyName) {
        String getter = "\tpublic " + type.getSimpleName() + " " + "get" + TextUtil.capitalFirst(propertyName) +
                "() { " + " return " + propertyName + "; } \n\n";
        sb.append(getter);
    }

    /**
     * 生成 JavaBean 实例，并返回该实例
     *
     * @throws Exception
     */
    public Object instantiateBean() throws Exception {
        Class<?> typeClass = Class.forName(packageName + "." + beanName);
        return typeClass.newInstance();
    }

    /**
     * 生成 JavaBean 文件，并写入指定路径
     */
    public void generateBean(String path) {
        StringBuilder sb = new StringBuilder();
        String packageString = "package " + packageName + ";\n\n";
        sb.append(packageString);
        sb.append("\npublic class ").append(beanName).append(" { \n\n");
        try {
            Set beanEntry = beanAttrs.entrySet();
            Iterator iter = beanEntry.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Class<?>> beanProperty = (Map.Entry<String, Class<?>>) iter.next();
                String propertyName = beanProperty.getKey();
                Class<?> propertyType = beanProperty.getValue();
                appendAttr(sb, propertyType, propertyName);
                appendSetter(sb, propertyType, propertyName);
                appendGetter(sb, propertyType, propertyName);
                try {
                    if (!propertyType.getPackage().getName().equals("java.lang")) {
                        appendImport(sb, propertyType, packageString.length());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            sb.append("\n }");

            TextUtil.writeText(sb.toString(), new File(path + beanName + ".java"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
