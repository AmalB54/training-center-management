package com.iit.trainingcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EntityScan n'est pas nécessaire car @SpringBootApplication scanne automatiquement
// le package com.iit.trainingcenter et tous ses sous-packages
public class TrainingCenterManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingCenterManagementApplication.class, args);
	}

}
