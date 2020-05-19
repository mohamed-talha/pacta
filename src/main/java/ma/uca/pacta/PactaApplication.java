package ma.uca.pacta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ma.uca.pacta"})
public class PactaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactaApplication.class, args);
	}

}
