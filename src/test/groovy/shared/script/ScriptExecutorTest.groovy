package shared.script

import org.junit.Test
import shared.config.GlobalConfig
import spock.lang.Specification

class ScriptExecutorTest extends Specification {

    ScriptExecutor scriptExecutor = new ScriptExecutor()
    GlobalConfig globalConfig = new GlobalConfig()


    def setup() {
        scriptExecutor.globalConfig = globalConfig
        scriptExecutor.init()
    }

    @Test
    def "testExec"() {
        expect:
        Binding binding = new Binding()
        binding.setVariable("x", x)
        def script = " 2 * x "
        def result = scriptExecutor.exec(script, binding)
        result == actual

        where:
        x | actual
        1 | 2
        2 | 4
    }


    @Test
    def "testExecConcurrent"() {
        expect:
        (1..100).each {
            new CalcThread(it, scriptExecutor).start()
        }
    }

}

class CalcThread extends Thread {
    int x
    ScriptExecutor scriptExecutor

    CalcThread(int x, ScriptExecutor scriptExecutor) {
        this.x = x
        this.scriptExecutor = scriptExecutor
    }

    @Override
    void run() {
        Binding binding = new Binding()
        binding.setVariable("x", x)
        def script = " Math.sqrt(x) "
        def result = scriptExecutor.exec(script, binding)
        result == Math.sqrt(x)
    }
}
