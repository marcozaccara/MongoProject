package mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringBootMongoDBApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongoDBApplication.class, args);
    }
}