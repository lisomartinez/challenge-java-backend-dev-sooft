package ar.edu.teclab.prueba;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DegreeServiceTest.class,
        DegreeTest.class,
        DirectorInvalidEmailTest.class,
        DirectorTest.class,
        DirectorValidEmailTest.class,
        TicketServiceTest.class
})
public class UnitTestSuite {
}
