package famous.sequence;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import FibonacciStructureTest;

@Suite
@SelectClasses({
    FibonacciStructureTest.class,
    FibonacciTest.class
})
public class FibonacciTestSuite {}
