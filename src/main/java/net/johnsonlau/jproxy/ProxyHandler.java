package net.johnsonlau.jproxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ProxyHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(Thread.currentThread().getName() + ": handlerRemoved");
		// TODO: close the ssh channel
    }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    ByteBuf in = (ByteBuf) msg;
	    try {
	        while (in.isReadable()) { // (1)
	            System.out.print(Thread.currentThread().getName() + ": ");
	            System.out.println((char) in.readByte());
	            System.out.flush();
	        }
	    } finally {
	        ReferenceCountUtil.release(msg); // (2)
	    }
	   //ctx.write(msg); // (1)
	   //ctx.flush(); // (2)
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}