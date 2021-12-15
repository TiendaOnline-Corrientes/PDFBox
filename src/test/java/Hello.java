import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Hello {

    @Test
    public void testHello()  {
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait()
    }
}
