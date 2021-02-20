package io.example.test;

import io.example.demo_service.DemoApp;
import io.example.service.demo.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = DemoApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DemoTest {

	@Autowired
	private DemoService demoService;

	@Test
	public void test01() {
		System.out.println(demoService.getDemo());
	}
}
