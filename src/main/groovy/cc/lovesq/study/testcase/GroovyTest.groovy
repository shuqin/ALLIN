package cc.lovesq.study.testcase

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GroovyTest {

    static Logger log = LoggerFactory.getLogger(GroovyTest.class)

    def kdtId = 55

    def invokeAllTests() {

        Map invokeResultMap = [:]
        Object obj = this
        obj.getClass().getDeclaredMethods().findAll {

            // 识别测试方法： 带有 @TestMethod 或者 以test开头
            it.getAnnotation(TestMethod.class) != null || it.name.startsWith("test")

        }.each {

            try {
                log.info("Enter Test Method : {}", it.name)
                invokeResultMap[it.name] = it.invoke(obj, null)
            } catch (Throwable e) {
                invokeResultMap[it.name] = "failed"
            } finally {
                log.info("Exit Test Method : {}", it.name)
            }

        }
        return invokeResultMap
    }


}
