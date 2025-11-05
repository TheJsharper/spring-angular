import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;

@SpringBootApplication
@ComponentScan({"controllers", "repositories", "services"})
@EntityScan("entities")
@EnableJpaRepositories({"repositories"})
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        var envs = System.getenv();

        envs.forEach( (k, v)-> System.out.println("Key : "+k + " Values: "+  v ) );

        File file = ResourceUtils.getFile(".env");

        System.out.println(file.getAbsoluteFile());

        var content =Files.readString(file.toPath(), Charset.defaultCharset());

        System.out.println(content);

        SpringApplication.run(Main.class, args);
    }
}
