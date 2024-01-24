package projectbms;

// Opnes chorm with number link and redierct to whastsap and send file (Should fix curser should move from chat from sreach)

/*
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class sendFile extends parentform {

//     public static void main(String[] args) throws InterruptedException {
//
//        try {
//            Robot robot = new Robot();
//            openWhatsApp(robot);
//        } catch (AWTException ex) {
//            Logger.getLogger(sendFile.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
     
    public void openWhatsApp(String phnNum, String fileLoction) {
        try {
            Desktop desktop = Desktop.getDesktop();
            String num = phnNum;
            desktop.browse(new java.net.URI("https://api.whatsapp.com/send/?phone=%2B91" + num + "&text&type=phone_number&app_absent=0"
                    + ""
                    + ""));
            Thread.sleep(10000);

            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            Thread.sleep(2000);

            robot.mouseMove(650, 700);

            robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

            // Delay after clicking (in milliseconds)
            robot.delay(1000);

            File file = new File(fileLoction);
            selectFile(file);

            Thread.sleep(5000);

            robot.mouseMove(1330, 20);

            robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

        } catch (Exception e) {
            Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("An error occurred while sending file through whatsapp", e);
        }
    }

    private void selectFile(File file){
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            FileTransferable fileTransferable = new FileTransferable(file);
            clipboard.setContents(fileTransferable, null);
            
            Thread.sleep(5000);
            
            Robot robot = new Robot();
            
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            Thread.sleep(2000);
            
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (InterruptedException ex) {
            Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("An error occurred while sending file through whatsapp", ex);
        } catch (AWTException ex) {
           Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("An error occurred while sending file through whatsapp", ex);
        }
    }

    private static class FileTransferable implements Transferable {

        private final File file;

        public FileTransferable(File file) {
            this.file = file;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.javaFileListFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (isDataFlavorSupported(flavor)) {
                return java.util.Collections.singletonList(file);
            }
            return null;
        }
    }
}
 */
// By Using selinum.
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import javafx.stage.Screen;
import org.apache.logging.log4j.LogManager;

public class sendFile {

    public void openWhatsApp(String phoneNumber, String fileLoction) {

        try {
            Robot robot = new Robot();
            // Set the path to the ChromeDriver executable
            System.setProperty("webdriver.chrome.driver", Constants.chormDriver);

            // Create a new instance of ChromeDriver
            WebDriver driver = new ChromeDriver();

            // Set the maximum time to wait for an element to be present
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            String url = "https://api.whatsapp.com/send/?phone=%2B91" + phoneNumber;
            driver.get(url);

            // Wait for the page to load
            Thread.sleep(5000);

            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            Thread.sleep(3000);

            int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
            int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

            robot.mouseMove(screenWidth-(screenWidth/2),screenHeight-80 );

            robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

            // Delay after clicking (in milliseconds)
            robot.delay(2000);

            File file = new File(fileLoction);
            selectFile(file);

            // Close the browser
            driver.quit();

        } catch (Exception e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("Erorr in Sending File Through Whatsapp ", e);
        }
    }

    private static void selectFile(File file) {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            FileTransferable fileTransferable = new FileTransferable(file);
            clipboard.setContents(fileTransferable, null);

            Thread.sleep(5000);

            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            Thread.sleep(2000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (InterruptedException ex) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("Interrut in Sending File Through Whatsapp ", ex);
        } catch (AWTException ex) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(sendFile.class);
            logger.error("AWT exception in Sending File Through Whatsapp ", ex);
        }
    }

    private static class FileTransferable implements Transferable {

        private final File file;

        public FileTransferable(File file) {
            this.file = file;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.javaFileListFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (isDataFlavorSupported(flavor)) {
                return java.util.Collections.singletonList(file);
            }
            return null;
        }
    }
}
