package io.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	private static final ConcurrentMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 获取通道id
		ChannelId channelId = ctx.channel().id();

		if(CHANNEL_MAP.containsKey(channelId)) {
			log.info("客户端[" + channelId + "]已是连接状态,连接通道数[" + CHANNEL_MAP.size() + "]");
		} else {
			log.info("客户端[" + channelId + "]正在连接,连接通道数[" + CHANNEL_MAP.size() + "]");
			CHANNEL_MAP.put(channelId, ctx);
			log.info("客户端[" + channelId + "]已连接,连接通道数[" + CHANNEL_MAP.size() + "]");
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx){
		// 获取通道id
		ChannelId channelId = ctx.channel().id();

		if(CHANNEL_MAP.containsKey(channelId)) {
			log.info("客户端[" + channelId + "]正在断开连接,连接通道数[" + CHANNEL_MAP.size() + "]");
			CHANNEL_MAP.remove(channelId);
			log.info("客户端[" + channelId + "]已断开连接,连接通道数[" + CHANNEL_MAP.size() + "]");
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		log.info("通道报文[" + ctx.channel().id() + "] :" + msg.toString());

		//TODO: 数据处理

		// 将服务端响应消息写入消息通道
		channelWrite(ctx.channel().id(), msg);
	}

	private void channelWrite(ChannelId channelId, Object msg) {
		// 获取context对象
		ChannelHandlerContext ctx = CHANNEL_MAP.get(channelId);

		if(ctx == null) {
			log.info("通道[" + channelId + "]不存在");
			return;
		}
		if(msg == null || "".equals(msg)) {
			log.info("服务端响应消息为空");
			return;
		}
		ctx.write(msg);
		ctx.flush();
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
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		log.info("通道[" + ctx.channel().id() + "]发生异常,此连接已关闭,连接通道数[" + CHANNEL_MAP.size() + "]");
	}
}
