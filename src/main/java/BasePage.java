import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    private final WebDriver webDriver;

    public BasePage( WebDriver webDriver) {
        this.webDriver= webDriver;
    }

    public void click(By locator){
        webDriver.findElement(locator).click();
    }
}
