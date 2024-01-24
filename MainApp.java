//package projectbms;
//
//import java.util.Properties;
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//public class MainApp extends Application {
//
//    private SendEmail emailSender; // Instantiate the SendEmail class here
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Create an instance of JavaMailSender
//        JavaMailSender mailSender = createMailSender();
//
//        // Instantiate the SendEmail class with the JavaMailSender instance
//        emailSender = new SendEmail(mailSender);
//
//        CheckBox sendEmailCheckbox = new CheckBox("Send Email");
//        Button okButton = new Button("OK");
//
//        okButton.setOnAction(event -> {
//            if (sendEmailCheckbox.isSelected()) {
//                // Call the sendMail function
//                String toEmail = "jayachandra.kothamasu18@gmail.com"; // Replace with the customer's email
//                String subject = "Invoice";
//                String body = "Please find the attached invoice.";
//                String filePath = "C:\\IMS3\\Bills\\pp.pdf"; // Replace with the actual file path
//
//                emailSender.sendMail(toEmail, subject, body, filePath);
//            }
//
//            // Close the application after sending the email
//            primaryStage.close();
//        });
//
//        VBox vbox = new VBox(10);
//        vbox.setPadding(new Insets(10));
//        vbox.getChildren().addAll(sendEmailCheckbox, okButton);
//
//        primaryStage.setScene(new Scene(vbox));
//        primaryStage.setTitle("Email Invoice");
//        primaryStage.show();
//    }
//
//    private JavaMailSender createMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com"); // Replace with your SMTP host
//        mailSender.setPort(587); // Replace with your SMTP port
//        mailSender.setUsername("inventorymanagementsystem2023@gmail.com"); // Replace with your SMTP username
//        mailSender.setPassword("qvaqyxpfkhufguas"); // Replace with your SMTP password
//
//        // Enable TLS
//    Properties props = mailSender.getJavaMailProperties();
//    props.put("mail.smtp.starttls.enable", "true");
//
//        return mailSender;
//    }
//}
