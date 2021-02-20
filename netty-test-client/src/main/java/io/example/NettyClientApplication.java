package io.example;

import io.example.client.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class NettyClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);

		for(int i = 0; i < 1; i++) {
			new Thread(new NettyClient(new Date().toString())).start();
		}

	}
}
