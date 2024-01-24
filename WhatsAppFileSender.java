//file sending using selium but not working properly

package projectbms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class WhatsAppFileSender {
    public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\jayac\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // Launch ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open WhatsApp web
            driver.get("https://web.whatsapp.com/");

            // Wait for the user to scan the QR code and log in
            // You can manually add a delay using Thread.sleep() here if needed

            // Simulate pressing the keys to open the file picker dialog
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_O);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_O);

            // Wait for the file picker dialog to open
            // You can manually add a delay using Thread.sleep() here if needed

            // Set the file path and name
            String filePath = "C:\\IMS3\\Bills";
            String fileName = "bill1.pdf";

            // Simulate typing the file path and name into the file picker dialog
            StringSelection stringSelection = new StringSelection(filePath + "\\" + fileName);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);

            // Simulate pressing Enter to select the file
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            // Wait for the file to upload
            // You can manually add a delay using Thread.sleep() here if needed

            // Locate the input field for the contact or group
            // Replace "Contact Name" with the actual name of the contact or group
            driver.findElement(By.xpath("//span[@title='Venni']")).click();

            // Locate the send button
            driver.findElement(By.xpath("//span[@data-testid='send']")).click();

            // Wait for the file to be sent
            // You can manually add a delay using Thread.sleep() here if needed

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the driver
            driver.quit();
        }
    }
}
