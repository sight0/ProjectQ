package ae.s1ght.projectq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectqApplication.class, args);
	}

}
