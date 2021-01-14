package ar.edu.teclab.prueba;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DegreeControllerTest.class,
        DegreeSystemTest.class,
        TicketControllerTest.class,
        DegreeControllerTest.class,
        TicketServiceTest.class,
        DirectorServiceTest.class
})
public class IntegrationTestSuite {

}
