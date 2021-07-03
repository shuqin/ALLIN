package shared.script;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ScriptPoolFactory extends BasePooledObjectFactory<Script> {

    private String script;

    public ScriptPoolFactory(String script) {
        this.script = script;
    }

    @Override
    public Script create() {
        return new GroovyShell().parse(script);
    }

    @Override
    public PooledObject<Script> wrap(Script obj) {
        return new DefaultPooledObject<Script>(obj);
    }
}
