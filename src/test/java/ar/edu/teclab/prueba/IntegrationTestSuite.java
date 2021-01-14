package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.controllers.DegreeControllerTest;
import ar.edu.teclab.prueba.controllers.DirectorControllerTest;
import ar.edu.teclab.prueba.controllers.TicketControllerTest;
import ar.edu.teclab.prueba.functional.DegreeSystemTest;
import ar.edu.teclab.prueba.services.DirectorServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DegreeControllerTest.class,
        DirectorControllerTest.class,
        DegreeSystemTest.class,
        TicketControllerTest.class,
        DegreeControllerTest.class,
        DirectorServiceTest.class
})
public class IntegrationTestSuite {

}
