package zzz.study.utils;

import cc.lovesq.service.CreativeService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static zzz.study.utils.BaseTool.*;

/**
 * 移除指定类的 Javadoc 注释 Created by shuqin on 16/5/4.
 */
public class RemoveJavadocComments {

    private static final String javadocRegexStr = "(\\/\\*.*?\\*\\/)";
    private static final Pattern javadocPattern =
            Pattern.compile(javadocRegexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    public static void main(String[] args) {

        // 移除指定包下面的类 Javadoc 注释
        String tradeDALPackage = ALLIN_PROJ_PATH_SRC + "/cc/lovesq/controller";
        List<Class> classes = getClasses(tradeDALPackage);
        for (Class c : classes) {
            if (c.getSimpleName().endsWith("Controller")) {
                removeJavadoc(c);
            }
        }

        // 移除单个类的 Javadoc 注释
        removeJavadoc(CreativeService.class);
    }

    public static void removeJavadoc(Class<?> coreServiceCls) {

        String coreServiceName = coreServiceCls.getSimpleName();
        String packageName = coreServiceCls.getPackage().getName();
        String packagePath = "/" + packageName.replaceAll("\\.", "/");

        String coreServiceClsRelativePath = packagePath + "/" + coreServiceName + ".java";
        String coreServiceClsPath = ALLIN_PROJ_PATH_SRC + coreServiceClsRelativePath;
        String coreServiceContent = readFile(coreServiceClsPath);

        Matcher m = javadocPattern.matcher(coreServiceContent);
        String newContent = coreServiceContent;
        while (m.find()) {
            String matchedJavadoc = coreServiceContent.substring(m.start(), m.end());
            newContent = newContent.replace(matchedJavadoc, "");
        }
        newContent = newContent.replaceAll("\n\\s*\n", "\n\n");
        writeFile(coreServiceClsPath, newContent);
    }

}
