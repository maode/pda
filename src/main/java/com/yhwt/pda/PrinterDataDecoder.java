package com.yhwt.pda;

import com.yhwt.pda.model.ReceiveData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 打印机数据采集解码器
 * @author ZhengMaoDe
 * @date 2021-11-26
 */
@Slf4j
public class PrinterDataDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        int len = msg.content().readableBytes();
        log.debug("当前数据包首次读取剩余可读长度：{}",len);
        byte[] content=new byte[len];
        msg.content().readBytes(content);
        String hexString = HexUtils.toHexString(content);


        int len2 = msg.content().readableBytes();
        log.debug("当前数据包第二次读取剩余可读长度：{}",len2);
        String strUtf8 = new String(content,StandardCharsets.UTF_8);
        String str8859_1 = new String(content,StandardCharsets.ISO_8859_1);
        String strAscii = new String(content,StandardCharsets.US_ASCII);
        String strUtf16 = new String(content,StandardCharsets.UTF_16);
        String strGbk = new String(content, Charset.forName("GBK"));
        log.debug("解码器收到的消息，解码前：{}；解码后：{}",hexString,strUtf8);
        ReceiveData receiveData = new ReceiveData();
        receiveData.setData(strGbk);
        receiveData.setSender(msg.sender());
        out.add(receiveData);

    }
}
