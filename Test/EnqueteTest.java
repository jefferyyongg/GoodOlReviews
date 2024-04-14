import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class EnqueteTest {

    private Enquete enquete;

    @BeforeEach
    public void setUp(){
        enquete = new Enquete();

    }

    @Test
    public void testLoadVragen(){
        setUp();
        enquete.loadVragen();
        assertNotNull(enquete.getVragen());
        assertEquals(7, enquete.getVragen().size());
    }

    @Test
    public void testCheckForDependencyZonderDependency(){
        setUp();
        enquete.loadVragen();
        EnqueteVraag vraag = enquete.getVragen().get(0);
        assertTrue(enquete.checkForDependency(vraag));
    }

    @Test
    public void testCheckForDependencyMetDependency(){
        setUp();
        enquete.loadVragen();
        EnqueteVraag vraagMetDep = enquete.getVragen().get(1);
        enquete.getVragen().get(0).setAntwoordGegeven("Dagelijks");
        assertTrue(enquete.checkForDependency(vraagMetDep));
    }

}