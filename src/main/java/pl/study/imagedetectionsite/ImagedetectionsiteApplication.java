package pl.study.imagedetectionsite;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.study.imagedetectionsite.config.PythonProperties;
import pl.study.imagedetectionsite.config.StorageProperties;
import pl.study.imagedetectionsite.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, PythonProperties.class})
public class ImagedetectionsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagedetectionsiteApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }
}
