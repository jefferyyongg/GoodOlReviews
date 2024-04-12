import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageTest {

    @Test
    public void TestEncodeSpace(){
        Page page = new Page();
        assertEquals("test_review",page.encodeReview("test review"));
    }

    @Test
    public void TestEncodeUnderscore(){
        Page page = new Page();
        assertEquals("test-review",page.encodeReview("test_review"));
    }

    @Test
    public void TestEncodeDash(){
        Page page = new Page();
        assertEquals("test@review",page.encodeReview("test-review"));
    }
}
