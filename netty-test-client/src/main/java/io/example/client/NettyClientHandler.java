package io.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	private static final ConcurrentMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		CHANNEL_MAP.put(ctx.channel().id(), ctx);
		log.info("ClientHandler Active");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx){
		ctx.close();
		log.info("Server Disconnected");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		log.info("收到服务端数据:" + msg);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch(event.state()) {
				case READER_IDLE: {
					log.info("[READER_IDLE]:读超时");
					break;
				}
				case WRITER_IDLE: {
					log.info("[WRITER_IDLE]:写超时");
					break;
				}
				case ALL_IDLE: {
					log.info("[ALL_IDLE]:超时");
					break;
				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		log.info("通道[" + ctx.channel().id() + "]发生异常,此连接已关闭,连接通道数[" + CHANNEL_MAP.size() + "]");
	}
}
