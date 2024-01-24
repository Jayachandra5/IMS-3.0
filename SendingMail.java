//package projectbms;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@SpringBootApplication
//public class SendingMail {
//
//    @Autowired
//    private SendEmail senderService;
//
//    public static void main(String[] args) {
//        SpringApplication.run(SendingMail.class, args);
//    }
//
//    public void sendMail2() {
//        senderService.sendMail("jayachandra.kothamasu18@gmail.com", "Sending email",
//                "Your Bill, Thanks For shopping", "C:\\IMS3\\Bills\\pp.pdf");
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMailOnReadyEvent() {
//        sendMail2();
//    }
//}
