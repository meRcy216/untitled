package io.example.service.demo;

import io.example.properties.demo.DemoProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoService {

	@Autowired
	private DemoProperties demoProperties;

	public String getDemo() {
		return demoProperties.getName();
	}
}
