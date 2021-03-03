package com.dust.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NioWebSocketChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        sc.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
        sc.pipeline().addLast("http-codec", new HttpServerCodec());
        sc.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        sc.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        sc.pipeline().addLast("handler", new NioWebSocketHandler());
        
    }
}
