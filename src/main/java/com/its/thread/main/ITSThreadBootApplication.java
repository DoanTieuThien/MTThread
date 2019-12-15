package com.its.thread.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author its - itshare
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.its.thread.*")
public class ITSThreadBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(ITSThreadBootApplication.class, "");
	}
}
