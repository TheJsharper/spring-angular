package main;

import models.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repositories.PersonRepository;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootApplication
@ComponentScan({"controllers",  "services"})
@EnableJpaRepositories({"repositories"})
@EntityScan("models")
public class Main {
    private final PersonRepository personRepository;

    public Main(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context){
        return args -> {
            String[] beanNames = context.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

            IntStream.range(1, 100).forEach((next)-> {

            Person web = personRepository.save(new Person(  "CMDLINE FirstNAme "+ next, "CMDLINE LastName "+next));
            });


        };
    }
}
