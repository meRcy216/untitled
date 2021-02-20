package io.example;

import io.example.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyServerApplication.class, args);
		NettyServer server = new NettyServer();
		server.start(new InetSocketAddress("127.0.0.1", 8000));
	}
}
