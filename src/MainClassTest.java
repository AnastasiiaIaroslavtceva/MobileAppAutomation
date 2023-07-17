import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    MainClass mainClassObj = new MainClass();

    @Test
    public void testGetLocalNumber() {
        int actualNumber = mainClassObj.getLocalNumber();
        Assert.assertTrue("Number returned by getLocalNumber() != 14", actualNumber == 14);
    }

    @Test
    public void testGetClassNumber() {
        int actualNumber = mainClassObj.getClassNumber();
        Assert.assertTrue("Number returned by getClassNumber() = " + actualNumber + " < 45", actualNumber > 45);
    }
}
