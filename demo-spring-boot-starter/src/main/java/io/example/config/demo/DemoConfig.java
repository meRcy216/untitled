package io.example.config.demo;

import io.example.properties.demo.DemoProperties;
import io.example.service.demo.DemoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DemoProperties.class)
public class DemoConfig {

	@Bean
	public DemoService demoService() {
		return new DemoService();
	}
}
