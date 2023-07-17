import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    MainClass mainClassObj = new MainClass();

    @Test
    public void testGetLocalNumber() {
        int actualNumber = mainClassObj.getLocalNumber();
        Assert.assertTrue("Number returned by getLocalNumber() != 14", actualNumber == 14);
    }
}
