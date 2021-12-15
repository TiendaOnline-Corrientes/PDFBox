import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.jws.Oneway;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PDFReader {


    private final SoftAssert softAssert = new SoftAssert();

    @Test
    public void readPDFInLocalMachine() throws Exception {
        File file = new File("C:\\Users\\Usuario\\Desktop\\response2.pdf");
        FileInputStream fis= new FileInputStream(file);
       // si queremos crear un pdf archivo tipo pdf
       // PDDocument document = new PDDocument();
        PDDocument document = PDDocument.load(fis);
        // para saber la cantidad de hojas que tiene
        System.out.println("amount of pages is " + document.getPages().getCount());

        //Para leer dentro de los archivos creamos una clase llamada PDFTextStripper
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        // si queremos el texto de una pagina en particular, se utiliza como un rago desde que pagina a que pagina
        // aqui solo vamos a leer la priemra pagina
        pdfTextStripper.setStartPage(1);
        pdfTextStripper.setEndPage(1);
        String pdfContent= pdfTextStripper.getText(document);
        System.out.println(pdfContent);
        
        // siempre tenemos que cerrar la cl OBjeto PDocument y el ImputStream
        document.close();
        fis.close();
    }

    @Test
    public void readPDFFromWebPage() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Usuario\\Desktop\\ProjectFravega\\creditos-automation\\src\\main\\resources\\drivers\\chrome\\win\\chromedriver.exe");
        WebDriver webDriver= new ChromeDriver();
        webDriver.get("https://file-examples.com/");
        webDriver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS );
        By documentsButton = By.xpath("(//*/div[@class='feature-item']/a) [3]");
        webDriver.findElement(documentsButton).click();
        By selectPDF = By.xpath("//td[text()='PDF']/following-sibling::td/a");
        webDriver.findElement(selectPDF).click();
        By showPDF= By.xpath("(//td[text()='150 kB']/following-sibling::td/a) [1]");
        webDriver.findElement(showPDF).click();
        // primero lo que hacemos es traenos la url con un metodo de selenium
        String stringURL = webDriver.getCurrentUrl();

        // creamo una jinstacia de un objeto URL
         URL url = new URL(stringURL);
         //ahora lo que hacemos es cargar el pdf, le pasamos la url y leera el archivo
        PDDocument pdfDocument= PDDocument.load(url.openStream());
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(1);
        pdfTextStripper.setEndPage(1);
        String pdfContent= pdfTextStripper.getText(pdfDocument);
        String word = "Lorem";
        softAssert.assertTrue(pdfContent.contains(word),String.format("the value do not contain the word %s",word));
        pdfDocument.close();
        webDriver.quit();
        softAssert.assertAll();
    }
}
