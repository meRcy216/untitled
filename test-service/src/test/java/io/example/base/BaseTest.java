package io.example.base;

import org.junit.After;
import org.junit.Before;

public abstract class BaseTest implements IBaseTest {

	@Override
	@Before
	public void init() throws Exception {
		System.out.println("#######Test Start#######");
	}

	@Override
	@After
	public void finish() throws Exception {
		System.out.println("#######Test Success#######");
	}
}
