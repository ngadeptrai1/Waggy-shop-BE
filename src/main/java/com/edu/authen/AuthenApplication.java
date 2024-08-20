package com.edu.authen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.edu.authen")
@EnableAsync
public class AuthenApplication {
	public static void main(String[] args) { SpringApplication.run(AuthenApplication.class, args);
	}

}
