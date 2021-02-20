package io.example.thread;

import io.example.base.BaseTest;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentTest extends BaseTest {

	@Override
	@Test
	public void execute() throws Exception {
		Thread thread01 = new Thread(() -> {
			try {
				Thread.sleep(10000);
				System.out.println("Thread01 finish");
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread thread02 = new Thread() {
			@Override
			public void run() {
				try {
					thread01.join();
					Thread.sleep(5000);
					System.out.println("Thread02 finish");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread03 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thread01.join();
					Thread.sleep(4000);
					System.out.println("Thread03 finish");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Callable task01 = (Callable<String>) () -> {
			try {
				thread01.join();
				Thread.sleep(3000);
				System.out.println("Thread04 finish");
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			return "thread04";
		};
		Callable task02 = new Callable<String>() {
			@Override
			public String call() {
				try {
					thread01.join();
					Thread.sleep(3000);
					System.out.println("Thread05 finish");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				return "thread05";
			}
		};

		thread01.start();
		thread02.start();
		thread03.start();
		thread01.join();
		thread02.join();
		thread03.join();

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		// 使用Future获取线程执行结果
		Future future01 = executorService.submit(task01);
		Future future02 = executorService.submit(task02);
		while(!future01.isDone() || !future02.isDone()) {

		}

	}
}
