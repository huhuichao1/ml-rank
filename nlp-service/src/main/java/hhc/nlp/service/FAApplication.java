package hhc.nlp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huhuichao
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = FAApplication.class)
public class FAApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FAApplication.class, args);
	}


	
}
