package io.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyServer {

	public void start(InetSocketAddress address) {
		// 主线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// 工作线程组
		EventLoopGroup workerGroup = new NioEventLoopGroup(10);

		ServerBootstrap bootstrap = new ServerBootstrap()
				.group(bossGroup, workerGroup)// 绑定线程池
				.channel(NioServerSocketChannel.class)
				.localAddress(address)
				.childHandler(new NettyServerChannelInitializer())// 编码解码
				.option(ChannelOption.SO_BACKLOG, 1024)// 服务端队列长度
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			ChannelFuture future = bootstrap.bind(address).sync();
			log.info("Netty Server Started in port " + address.getPort());
			future.channel().closeFuture().sync();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
