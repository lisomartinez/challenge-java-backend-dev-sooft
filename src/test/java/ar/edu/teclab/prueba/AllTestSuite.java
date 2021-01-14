package ar.edu.teclab.prueba;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UnitTestSuite.class,
        IntegrationTestSuite.class,
})
public class AllTestSuite {
}
