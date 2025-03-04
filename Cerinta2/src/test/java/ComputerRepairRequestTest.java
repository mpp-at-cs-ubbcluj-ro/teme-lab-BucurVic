import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("Test 1")
    public void test1() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        assertEquals("", computerRepairRequest.getOwnerName());
        computerRepairRequest.setOwnerName("test");
        assertEquals("test", computerRepairRequest.getOwnerName());
    }

    @Test
    @DisplayName("Test 2")
    public void test2() {
        assertEquals(100, 100, "Numbers should be equal");
        assertNotEquals(100, 101, "Numbers should be different");
    }
}
