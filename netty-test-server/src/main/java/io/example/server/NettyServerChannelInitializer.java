package io.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		channel.pipeline().addLast("encoder", new StringDecoder(CharsetUtil.UTF_8));
		channel.pipeline().addLast(new NettyServerHandler());
	}
}
