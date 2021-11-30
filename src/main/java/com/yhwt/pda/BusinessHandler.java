package com.yhwt.pda;

import com.yhwt.pda.model.ReceiveData;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;


/**
 * @author ZhengMaoDe
 * @Description 打印机数据业务处理器
 * @date 2021-11-29 11:58
 */
@Slf4j
@ChannelHandler.Sharable
public class BusinessHandler extends SimpleChannelInboundHandler<ReceiveData> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveData receiveData) throws Exception {
        log.debug("业务处理器收到的数据为：{}", receiveData);

        //发送消息
        ctx.channel().writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer(receiveData.getData() + "_result", Charset.forName("GBK")),
                        receiveData.getSender()
                )
        );

    }

}
