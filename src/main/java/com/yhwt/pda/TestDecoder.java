package com.yhwt.pda;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 打印机数据采集解码器
 * @author ZhengMaoDe
 * @date 2021-11-26
 */
@Slf4j
public class TestDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        int len = msg.content().readableBytes();
        String hexString = ByteBufUtil.hexDump(msg.content());
        String prettyHexDump = ByteBufUtil.prettyHexDump(msg.content());
        log.debug("\n解码器收到数据包。\n当前数据包字节长度：{}。\n数据包内容：{}。\n格式化输出：\n{}",len,hexString,prettyHexDump);
        //回包
        byte[] result = ByteBufUtil.decodeHexDump(hexString + "F4");
        ByteBuf resultByteBuf = Unpooled.wrappedBuffer(result);
        String resultHexDump = ByteBufUtil.hexDump(resultByteBuf);
        String resultPrettyHexDump = ByteBufUtil.prettyHexDump(resultByteBuf);
        ctx.writeAndFlush(new DatagramPacket(resultByteBuf, msg.sender()));
        log.debug("\n回包数据已发送，在源数据尾部追加 0xF4 后进行返回。\n回包内容如下：{}。\n格式化输出：\n{}",resultHexDump,resultPrettyHexDump);

    }
}
