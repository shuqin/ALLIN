package zzz.study.utils;

import java.util.List;
import static zzz.study.utils.BaseTool.*;

public class AutoGenerateCoreService {

    public static void main(String[] args) {
        testParseMethod();

    }

    public static void generateCoreServiceFile(Class<?> daocls) {

        String daoClassName = daocls.getSimpleName();
        String packageName = daocls.getPackage().getName();
        String daoRelativePath = "/" + packageName.replaceAll("\\.", "/");
        String daoFileName = ALLIN_PROJ_PATH_SRC + "/" + daoRelativePath +
                                                     "/" + daocls.getSimpleName() + ".java";
        String bizType = getBizType(packageName);

        String writeFilename = ALLIN_PROJ_PATH_SRC + "/cc/lovesq/service/" + daoClassName.replace("DAO", "CoreService") + ".java";
        String serviceClassName = daoClassName.replace("DAO", "CoreService");
        String daoRefName = firstToLower(daoClassName);

        List<String> lines = readLines(daoFileName);
        String fileContents = "";
        boolean daoFlag = false;
        for (String line: lines) {

            if (daoFlag) {
                fileContents += "\n\t@Resource\n";
                fileContents += "\tprivate " + daoClassName + " " + daoRefName + ";\n\n";
                daoFlag = false;
            }
            else if (line.contains("interface")) {
                fileContents += "@Component\npublic class " + serviceClassName + " { \n";
                daoFlag = true;
            }
            else if (line.contains(";")) {
                if (!line.contains("import") && !line.contains("package")) {
                    System.out.println(line);
                    System.out.println("parsed: " + parseMethod(line));
                    List<String> parsed = transform(parseMethod(line));
                    String replaceStr = " {\n\t\treturn " + daoRefName + "." + parsed.get(0) + "(" + parsed.get(1) + ");\n\t}\n";
                    String accessQualifier = "";
                    if (!line.contains("public")) {
                        accessQualifier = "public ";
                    }
                    fileContents += "\t" + accessQualifier + " " + line.trim().replace(";", replaceStr);
                }
                else if (line.contains("package")) {
                    System.out.println(line);
                    fileContents += line.replace("dao", "service") + "\n\n";
                    fileContents += "import " + daocls.getPackage().getName() + "." + daoClassName + ";\n";
                    fileContents += "import javax.annotation.Resource;\n" +
                                    "import org.springframework.stereotype.Component;\n" +
                                    "import java.util.List;";
                }
                else {
                    fileContents += line + "\n";
                }
            }
            else {
                fileContents += line + "\n";
            }
        }

        writeFile(writeFilename, fileContents);
    }

}
