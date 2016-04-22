package scalastudy.basic

import java.io.PrintWriter

import scalastudy.utils.DefaultFileUtil._
import scalastudy.utils.PathConstants

/**
 * Created by shuqin on 16/4/22.
 */
object AutoGenerateJavaCodes extends App {

    val methodNameRegexStr = "\\s*(?:\\w+\\s+)?\\w+<?\\w+>?\\s+(\\w+)"
    val singleParamRegexStr = "[^,]*\\w+<?\\w+>?\\s+(\\w+)\\s*"
    val simpleMethodSignRexStr = methodNameRegexStr + "\\(" + singleParamRegexStr + "\\)\\s*;\\s*"
    val twoParamMethodSignRegStr = methodNameRegexStr + "\\(" + singleParamRegexStr + "," + singleParamRegexStr + "\\);\\s*"
    //val generalParamMethodSignRegStr = methodNameRegexStr + "\\((" + singleParamRegexStr + "(?:," + singleParamRegexStr + ")*)\\);\\s*"
    val generalParamMethodSignRegStr = methodNameRegexStr + "\\((.*)\\);\\s*"

    val apiPackageName = "cc.lovesqcc"

    launch()


    object Method {

        def unapplySeq(methodSign:String): Option[(String, Seq[String])] = {
            try {
                val generalParamMethodSignReg = generalParamMethodSignRegStr.r
                val generalParamMethodSignReg(methodName, args) = methodSign
                val params = args.split(',').toList
                val argNames = params.map(extractArgName(_))
                println("parsed: " + Some(methodName, argNames))
                Some(methodName, argNames)
            } catch {
                case _ => Some("", List())
            }

        }

        def extractArgName(singleParam: String): String = {
            val singleParamRegex = singleParamRegexStr.r
            val singleParamRegex(argName) = singleParam
            return argName
        }

    }

    def generateJavaFile(filepath: String): List[Any] = {

        val basePath = "/tmp/"
        val biz = List("order", "payment")

        var writePath = basePath
        var bizType = ""
        biz.foreach { e =>
            if (filepath.contains(e)) {
                writePath = writePath + "/" + e + "/"
                bizType = e
            }
        }
        val daoFileName = extraFilename(filepath)
        val daoClassName = daoFileName.substring(0, daoFileName.indexOf('.'))
        val writeFilename = writePath + daoFileName.replaceAll("DAO", "ServiceImpl")
        val serviceClassName = daoClassName.replace("DAO", "ServiceImpl")
        val daoRefName = firstLower(daoClassName)
        val lines = readFileLines(filepath)
        var fileContents = ""
        var daoFlag = false
        lines.foreach { line =>
            if (daoFlag) {
                fileContents += "\n\t@Resource\n"
                fileContents += "\tprivate " + daoClassName + " " + daoRefName + ";\n\n"
                daoFlag = false
            }
            else if (line.contains("interface")) {
                fileContents += "@Service\npublic class " + serviceClassName + " implements " + daoClassName.replace("DAO", "Service") + " { \n"
                daoFlag = true
            }
            else if (line.contains(";")) {
                if (!line.contains("import") && !line.contains("package")) {
                    val parsed = parseMethod(line)
                    val replaceStr = " {\n\t\treturn " + daoRefName + "." + parsed(0) + "(" + parsed(1) + ");\n\t}\n"
                    var assertQualifier = ""
                    if (!line.contains("public")) {
                        assertQualifier = "public "
                    }
                    fileContents += "\t" + assertQualifier + " " + line.trim.replace(";", replaceStr)
                }
                else if (line.contains("package")) {
                    fileContents += line.replace("dao", "service") + "\n\n"
                    var bizTypeStr = "."
                    if (bizType.length > 0) {
                        bizTypeStr = "." + bizType + "."
                    }
                    fileContents += "import " + apiPackageName + bizTypeStr + "service." + daoClassName.replace("DAO", "Service") + ";\n"
                    fileContents += "import javax.annotation.Resource;\n"
                    fileContents += "import org.springframework.stereotype.Service;\n"
                }
                else {
                    fileContents += line + "\n"
                }
            }
            else {
                fileContents += line + "\n"
            }
        }

        mkdir(writePath)
        val writeFile = new PrintWriter(writeFilename)
        writeFile.println(fileContents)
        writeFile.close

        return List[Any]();
    }

    def daoRefName(daoClassName: String): String = {
        return firstLower(daoClassName)
    }

    def firstLower(str: String): String = {
        return str.charAt(0).toLower + str.substring(1)
    }

    def parseMethod(methodSign: String): List[String] = {

        val simpleMethodSignRex = simpleMethodSignRexStr.r
        try {
            val Method(methodName, firstArg, restArgs @ _*) = methodSign
            if (restArgs.size == 0) {
                return List(methodName, firstArg)
            }
            else {
                return List(methodName, firstArg + ", " + restArgs.mkString(", "))
            }

        } catch {
            case _ => return List("", "")
        }


    }


    def debug(): Unit = {

        // simple catch regex groups
        val methodSign = "  int insert(@Param(\"kdtId\") BuyerAddressDO buyerAddressDO); "
        val simpleMethodSignRex = simpleMethodSignRexStr.r
        val simpleMethodSignRex(methodName, arg) = methodSign
        println(methodName + " " + arg)

        val twoParamMethodSign = "OrderExpressDO getById(@Param(\"id\") int id, @Param(\"kdtId\") int kdtId); "
        val twoParamMethodSignRex = twoParamMethodSignRegStr.r
        val twoParamMethodSignRex(methodName2, arg1, arg2) = twoParamMethodSign
        println(List(methodName2, arg1 + ", " + arg2))

        val text = "Good query(@Param(\"goodId\") goodId, int kdtId)"
        val regex = "\\s*(?:\\w+\\s+)?\\w+<?\\w+>?\\s+(\\w+)\\((.*)\\)".r
        val regex(methodName3, wholearg1) = text
        println(methodName3 + " " + wholearg1);

        val generalParamMethodSign = " OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, @Param(\"page\") Integer page, @Param(\"pageSize\") Integer pageSize, boolean flag); "
        val regex2 = generalParamMethodSignRegStr.r
        val regex2(methodName4, wholearg2) = generalParamMethodSign
        println(methodName4 + " : " + wholearg2);

        generalParamMethodSign match {
            case Method(methodName, firstArg, restArgs @ _*) =>
                println("Case Match Way: " + methodName + "(" + firstArg + ", " + restArgs.mkString(", ") + ")")
            case _ => println("Not matched")
        }

        val Method(methodName5, firstArg, restArgs @ _*) = generalParamMethodSign
        println("Extractor Way: " + methodName5 + "(" + firstArg + ", " + restArgs.mkString(", ") + ")")
    }

    def testParseMethod(): Unit = {
        val testMethods = Map(
            " List<OrderDO> queryOrder(int kdtId); " -> List("queryOrder", "kdtId"),
            " List<OrderDO> queryOrder( int kdtId ); " -> List("queryOrder", "kdtId"),
            " OrderDO queryOrder(@Param(\"kdtId\") int kdtId); " -> List("queryOrder", "kdtId"),
            " List<OrderDO> queryOrder(List<String> orderNos); " -> List("queryOrder", "orderNos"),
            " List<OrderDO> queryOrder(@Param(\"orderNos\") List<String> orderNos); " -> List("queryOrder", "orderNos"),
            " OrderDO queryOrder(String orderNo, Integer kdtId); " -> List("queryOrder", "orderNo, kdtId"),
            " OrderDO queryOrder(String orderNo, @Param(\"kdtId\") Integer kdtId); " -> List("queryOrder", "orderNo, kdtId"),
            " OrderDO queryOrder(@Param(\"orderNo\") String orderNo, Integer kdtId); " -> List("queryOrder", "orderNo, kdtId"),
            " OrderDO queryOrder(@Param(\"orderNo\") String orderNo, @Param(\"kdtId\") Integer kdtId); " -> List("queryOrder", "orderNo, kdtId"),
            " OrderDO queryOrder(List<String> orderNos, Integer kdtId); \n" -> List("queryOrder", "orderNos, kdtId"),
            " OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, Integer kdtId); " -> List("queryOrder", "orderNos, kdtId"),
            " OrderDO queryOrder(List<String> orderNos, @Param(\"kdtId\") Integer kdtId); " -> List("queryOrder", "orderNos, kdtId"),
            " OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, @Param(\"kdtId\") Integer kdtId); " -> List("queryOrder", "orderNos, kdtId"),
            " OrderDO queryOrder(@Param(\"orderNos\") List<String> orderNos, @Param(\"page\") Integer page, @Param(\"pageSize\") Integer pageSize); " -> List("queryOrder", "orderNos, page, pageSize"))
        testMethods.foreach { testMethod =>
            println("test method: " + testMethod._1)
            val parsed = parseMethod(testMethod._1)
            println(parsed)
            assert(parseMethod(testMethod._1) == testMethod._2)
        }
        println("test ParseMethod passed.")
    }

    def launch(): Unit = {

        debug
        testParseMethod

        val dirpath = "/tmp/";
        handleFiles(fetchAllFiles)((file: String) => file.endsWith("DAO.java"))(List(generateJavaFile(_)))((liststr: List[Any]) => "")(dirpath);
    }

}
