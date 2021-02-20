package io.example.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public interface IBaseTest {

	public void execute() throws Exception;

	public void init() throws Exception;

	public void finish() throws Exception;
}
