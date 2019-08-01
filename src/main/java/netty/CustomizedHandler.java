package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class CustomizedHandler extends ChannelInboundHandlerAdapter {
    Scanner scanner = new Scanner(System.in);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("聊天开始");
            new Thread(()->{
                while (true){
                    ByteBuf buffer = getByteBuf(ctx);
                    ctx.channel().writeAndFlush(buffer);
                }
            }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": Gin -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        String s = scanner.nextLine();
        byte[] bytes = s.getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(bytes);
        return buffer;
    }
}
