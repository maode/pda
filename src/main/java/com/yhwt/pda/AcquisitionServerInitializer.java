package com.yhwt.pda;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;

/**
 * @author ZhengMaoDe
 * @Description: 通信server初始化
 * @date 2021-11-29 13:43
 */
public class AcquisitionServerInitializer extends ChannelInitializer<DatagramChannel> {

    @Override
    public void initChannel(DatagramChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // pipeline.addLast("decoder",new PrinterDataDecoder());
        pipeline.addLast("decoder",new TestDecoder());
       // pipeline.addLast("business", new BusinessHandler());

    }
}
