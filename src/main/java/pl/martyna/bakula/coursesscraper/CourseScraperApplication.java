package pl.martyna.bakula.coursesscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseScraperApplication.class, args);
	}

}