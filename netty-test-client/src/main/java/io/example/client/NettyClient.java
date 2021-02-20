package io.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class NettyClient implements Runnable {

	private String content;

	public NettyClient(String content) {
		this.content = content;
	}

	@Override
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		int num = 0;
		boolean boo = true;

		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new NettyClientChannelInitializer());

			ChannelFuture future = bootstrap.connect("127.0.0.1", 8000);
			while(boo) {
				num++;
				future.channel().writeAndFlush("[" + new Date() + "]:" + this.content);

				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				if(num == 10) {
					break;
				}
			}
		} finally {
			group.shutdownGracefully();
		}
	}
}
