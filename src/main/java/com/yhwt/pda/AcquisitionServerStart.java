package com.yhwt.pda;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;

/**
 * @author ZhengMaoDe
 * @Description 通信server入口类
 * @date 2021-11-29 13:42
 */
@Slf4j
@Getter
public class AcquisitionServerStart {

    public static final AcquisitionServerStart COMM_SERVER = new AcquisitionServerStart();

    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private final ChannelInitializer channelInitializer = new AcquisitionServerInitializer();
    private Channel serverChannel;
    private static final int DEFAULT_PORT = 1313;

    /**
     * 启动server(该方法会在同步等待关闭事件处阻塞)
     *
     * @param port
     */
    public void start(int port) {

        //通过引导程序，创建服务监听信道
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioDatagramChannel.class)//IO操作的多线程事件循环器
                .handler(channelInitializer);
        // 绑定端口，开始接收进来的连接
        ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();//绑定当前机器所有网卡的port端口
        log.info("服务已在[{}]端口启动监听", port);
        serverChannel = future.channel();

    }

    /**
     * 关闭server
     */
    public void destroy() {
        if (serverChannel != null) {
            try {
                serverChannel.close().sync();
                log.info("serverChannel closed~");
                serverChannel = null;
                workGroup.shutdownGracefully().sync();
                log.info("workGroup closed~");
            } catch (InterruptedException e) {
                log.error("netty server关闭时发生异常:{}", e.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            log.error("请指定server的监听端口");
            return;
        }
        //监听shutdownHook
        addShutDownHook();

        Integer port = null;
        try {
            port = Integer.valueOf(args[0]);
            COMM_SERVER.start(port);
        } catch (NumberFormatException e) {
            log.error("{}不是有效的端口号", port);
        }

    }

    /**
     * jvm退出时的操作
     */
    public static void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("监听到退出信号,JVM即将退出");
                //shutdown netty server
                COMM_SERVER.destroy();
                //shutdown log4j2
                log.info("Shutting down log4j2");
                LogManager.shutdown();

            }
        });
    }
}
