package units;


import configs.DataSourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = DataSourceConfig.class)
@ActiveProfiles("test")
public class DevActiveProfileUnitTest {
    @Value("${app.info}")
    private String propertyString;

    @Autowired
    private Environment env;

    @Test
    void whenDevIsActive_thenValueShouldBeKeptFromDedicatedApplicationFile() {
        var currentProfile = env.getActiveProfiles()[0];

        var expected = String.format("This is the TEST Environment property file %s", currentProfile);

        var actual = String.format(propertyString + " %s", currentProfile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenDevIsActive_thenValueShouldBeKeptFromDedicatedApplicationGetProperty() {
        String currentProfile = env.getActiveProfiles()[0];

        var message = env.getProperty("app.info");

        var expected = String.format("This is the TEST Environment property file %s", currentProfile);

        var actual = String.format(message + " %s", currentProfile);

        Assertions.assertEquals(expected, actual);
    }
}
