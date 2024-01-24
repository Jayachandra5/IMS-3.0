package projectbms;

import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private Properties properties = new Properties();

    // getters and setters

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}