package cn.henuer.netty.line.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class LineBasedFramerDecoderTimerClient {    public void connect(){
    EventLoopGroup group=new NioEventLoopGroup();

    try {
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new LineBasedFramerDecoderTimerClientHandler());
                    }
                });

        ChannelFuture f=bootstrap.connect("127.0.0.1",9001).sync();
        f.channel().closeFuture().sync();
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        group.shutdownGracefully();
    }
}

    public static void main(String[] args) {
        new SimpleTimerClient().connect();
    }

    public class LineBasedFramerDecoderTimerClientHandler extends ChannelInboundHandlerAdapter {

        private  final Logger logger = Logger.getLogger(SimpleTimerClient.class.getName());

        private AtomicInteger counter=new AtomicInteger(0);

        private byte[] req;


        public LineBasedFramerDecoderTimerClientHandler(){
            // 客户端每次发送的消息后面都跟了一个换行符
            req = ("Query time order"+System.getProperty("line.separator")).getBytes();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf message=null;
            for (int i=0;i<100;i++){
                message= Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body=(String) msg;
            System.out.println("Now is :"+body+";counter is :"+ counter.addAndGet(1));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.warning("Unexpected exception from downstream:"+cause.getMessage());
            ctx.close();
        }
    }
}
