package com.dust.netty.websocket;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 启动类
 * 
 * @author F
 *
 */
public class WebSocketServer {
    private final Logger logger = LogManager.getLogger(this.getClass());
    
    private void init() {
        logger.info("正在启动websocket服务器...");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, work);
            boot.channel(NioServerSocketChannel.class);
            boot.childHandler(new NioWebSocketChannelInitializer());
            Channel channel = boot.bind(8081).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("运行出错", e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) {
        new WebSocketServer().init();
    }
}
