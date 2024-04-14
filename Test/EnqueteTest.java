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
        assertTrue(enquete.checkForDependency(vraag), "Moet true teruggeven als het vraag zonder dependency is.");
    }

    @Test
    public void testCheckForDependencyMetDependency(){
        setUp();
        enquete.loadVragen();
        EnqueteVraag vraagMetDep = enquete.getVragen().get(1);
        enquete.getVragen().get(0).setAntwoordGegeven(String.valueOf(1));
        assertTrue(enquete.checkForDependency(vraagMetDep),"Moet True teruggeven als het antwoord Dagelijks is, dus voldoet aan de dependency");
    }

}