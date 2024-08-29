package com.dobe.locmns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
@ComponentScan(basePackages = "com.dobe.locmns")
public class LocmnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocmnsApplication.class, args);
	}


}
