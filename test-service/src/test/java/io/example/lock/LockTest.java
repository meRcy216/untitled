package io.example.lock;

import io.example.base.BaseTest;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest extends BaseTest {

	@Override
	@Test
	public void execute() throws Exception {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		lock.unlock();
		Executors.newFixedThreadPool(2);
	}
}
