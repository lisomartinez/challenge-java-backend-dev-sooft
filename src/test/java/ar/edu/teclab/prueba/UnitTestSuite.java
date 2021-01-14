package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.model.DegreeTest;
import ar.edu.teclab.prueba.model.DirectorInvalidEmailTest;
import ar.edu.teclab.prueba.model.DirectorTest;
import ar.edu.teclab.prueba.model.DirectorValidEmailTest;
import ar.edu.teclab.prueba.services.DegreeServiceTest;
import ar.edu.teclab.prueba.services.TicketServiceTest;
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
