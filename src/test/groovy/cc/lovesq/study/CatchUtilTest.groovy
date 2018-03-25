package cc.lovesq.study

import org.junit.Assert
import spock.lang.Specification
import zzz.study.function.refactor.CatchUtil

import java.util.function.Consumer

/**
 * Created by shuqin on 18/3/25.
 */
class CatchUtilTest extends Specification {

    def "testTryDo"() {
        expect:
        try {
            CatchUtil.tryDo(1, { throw new IllegalArgumentException(it.toString())} as Consumer)
            Assert.fail("NOT THROW EXCEPTION")
        } catch (Exception ex) {
            ex.class.name == "java.lang.RuntimeException"
            ex.cause.class.name == "java.lang.IllegalArgumentException"
        }
    }

    def "testTryDoWithThrown"() {
        when:
        CatchUtil.tryDo(1, { throw new IllegalArgumentException(it.toString())} as Consumer)

        then:
        def ex = thrown(Exception)
        ex.class.name == "java.lang.RuntimeException"
        ex.cause.class.name == "java.lang.IllegalArgumentException"
    }
}
