package tgg.securityblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecurityblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityblogApplication.class, args);
	}

}
