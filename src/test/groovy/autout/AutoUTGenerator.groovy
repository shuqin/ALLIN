package autout

import groovy.text.SimpleTemplateEngine

import java.lang.reflect.Method

/**
 * Created by shuqin on 18/6/22.
 */
class AutoUTGenerator {

    def static projectRoot = System.getProperty("user.dir")

    static void main(String[] args) {
        // ut("com.youzan.ebiz.trade.biz")
    }

    static void ut(String packageName) {
        List<String> className = ClassUtils.getClassName(packageName, true)
        className.collect {
            ut Class.forName(it)
        }
    }

    /**
     * 生成指定类的单测模板文件
     */
    static void ut(Class testClass) {
        def packageName = testClass.package.name
        def className = testClass.simpleName
        def methods = testClass.declaredMethods.findAll { ! it.name.contains("lambda") }
        def methodInfos = methods.collect { parse(it) }

        def allInfo = new AllInfoForAutoGeneratingUT(
                packageName: packageName,
                className: className,
                methodInfos: methodInfos
        )
        def content = buildUTContent allInfo

        def path = getTestFileParentPath(testClass)
        def dir = new File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        def testFilePath = "${path}/${className}AutoTest.groovy"
        writeUTFile(content, testFilePath)
        println("Success Generate UT for $testClass.name in $testFilePath")
    }

    /**
     * 解析拿到待测试方法的方法信息便于生成测试方法的内容
     */
    static MethodInfo parse(Method m) {
        def methodName = m.name
        def paramTypes = m.parameterTypes.collect { it.simpleName }
        def classNamesToImported = m.parameterTypes.collect { it.name }
        def returnType = m.returnType.simpleName

        new MethodInfo(methodName: methodName,
                       paramTypes: paramTypes,
                       classNamesToImported: classNamesToImported,
                       returnType: returnType
        )

    }

    /**
     * 根据单测模板文件生成待测试类的单测类模板
     */
    static buildUTContent(AllInfoForAutoGeneratingUT allInfo) {
        def spockTestFile = new File("${projectRoot}/templates/spocktest.tpl")
        def methodContents = allInfo.methodInfos.collect { generateTestMethod(it, allInfo.className) }
                                                .join("\n\n")

        def engine = new SimpleTemplateEngine()
        def imports = allInfo.methodInfos.collect { it.classNamesToImported }
                .flatten().toSet()
                .findAll { isNeedImport(it) }
                .collect { "import " + it } .join("\n")
        def binding = [
                "packageName": allInfo.packageName,
                "ClassName": allInfo.className,
                "inst": allInfo.className.toLowerCase(),
                "BizClassNameImports": imports,
                "AllTestMethods": methodContents
        ]
        def spockTestContent = engine.createTemplate(spockTestFile).make(binding) as String
        return spockTestContent
    }

    static Set<String> basicTypes = new HashSet<>(["int", "long", "char", "byte", "float", "double", "short"])

    static boolean isNeedImport(String importStr) {
        def notToImport = importStr.startsWith('[') || importStr.contains("java") || (importStr in basicTypes)
        return !notToImport
    }

    /**
     * 根据测试方法模板文件 method.tpl 生成测试方法的内容
     */
    static generateTestMethod(MethodInfo m, String className) {
        def engine = new SimpleTemplateEngine()
        def methodTplFile = new File("${projectRoot}/templates/method.tpl")
        def paramValues = m.paramTypes.collect { getDefaultValueOfType(firstLowerCase(it)) }.join(" | ")
        def returnValue = getDefaultValueOfType(firstLowerCase(m.returnType))

        def paramTypeList = []
        m.paramTypes.eachWithIndex {
            def entry, int ind -> paramTypeList.add(mapType(firstLowerCase(entry), false) + ind)
        }

        def binding = [
                "method": m.methodName,
                "Method": firstUpperCase(m.methodName) + "(" + m.paramTypes.join(",") + ")",
                "inst": className.toLowerCase(),
                "paramListInMethodCall": paramTypeList.join(","),
                "paramListInDataProvider": paramTypeList.join(" | "),
                "result": mapType(firstLowerCase(m.returnType), true),
                "paramValues": paramValues,
                "resultValue": returnValue
        ]

        return engine.createTemplate(methodTplFile).make(binding).toString() as String

    }

    /**
     * 写UT文件
     */
    static void writeUTFile(String content, String testFilePath) {
        def file = new File(testFilePath)
        if (!file.exists()) {
            file.createNewFile()
        }
        def printWriter = file.newPrintWriter()

        printWriter.write(content)
        printWriter.flush()
        printWriter.close()
    }

    /**
     * 根据待测试类生成单测类文件的路径(同样的包路径)
     */
    static getTestFileParentPath(Class testClass) {
        println(testClass.getResource("").toString())
        testClass.getResource("").toString() // GET: file:$HOME/Workspace/java/project/submodule/target/classes/packagePath/
                .replace('/target/test-classes', '/src/test/groovy')
                .replace('/target/classes', '/src/test/groovy')
                .replace('file:', '')
    }

    /** 首字母小写 */
    static String firstLowerCase(String s) {
        s.getAt(0).toLowerCase() + s.substring(1)
    }

    /** 首字母大写 */
    static String firstUpperCase(String s) {
        s.getAt(0).toUpperCase() + s.substring(1)
    }

    /**
     * 生成参数列表中默认类型的映射, 避免参数使用关键字导致跑不起来
     */
    static String mapType(String type, boolean isResultType) {
        def finalType = typeMap[type] == null ? type : typeMap[type]
        (isResultType ? "result" : "") + finalType
    }

    static String getDefaultValueOfType(String type) {
        def customerType = firstUpperCase(type)
        typeDefaultValues[type] == null ? "new ${customerType}([:])" : typeDefaultValues[type]
    }

    def static typeMap = [
            "string": "str", "boolean": "bval", "long": "longval", "Integer": "intval",
            "float": "fval", "double": "dval", "int": "intval", "object[]": "objectlist",
            "int[]": "intlist", "long[]": "longlist", "char[]": "chars",
            "byte[]": "bytes", "short[]": "shortlist", "double[]": "dlist", "float[]": "flist"
    ]

    def static typeDefaultValues = [
            "string": "\"\"", "boolean": true, "long": 0L, "integer": 0, "int": 0,
            "float": 0, "double": 0.0, "list": "[]", "map": "[:]", "date": "new Date()",
            "int[]": "[]", "long[]": "[]", "string[]": "[]", "char[]": "[]", "short[]": "[]", "byte[]": "[]", "booloean[]": "[]",
            "integer[]": "[]", "object[]": "[]"
    ]
}

class AllInfoForAutoGeneratingUT {

    String packageName
    String className
    List<MethodInfo> methodInfos
}

class MethodInfo {
    String methodName
    List<String> paramTypes
    List<String> classNamesToImported
    String returnType
}


