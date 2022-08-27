package cc.lovesq.study.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTester extends TestCase {

    public AllTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(IntArraySortingTester.class);
        suite.addTestSuite(DoubleArraySortingTester.class);
        suite.addTestSuite(IntegerArraySortingTester.class);
        suite.addTestSuite(WrappedDoubleArraySortingTester.class);
        suite.addTestSuite(ObjectArraySortingTester.class);
        return suite;
    }

}
