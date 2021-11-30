package com.yhwt.pda.model;

import lombok.Data;

import java.net.InetSocketAddress;

/**
 * 接收到的数据模型
 * @author ZhengMaoDe
 * @date 2021-11-29
 */
@Data
public class ReceiveData {
    private InetSocketAddress sender;
    private String data;
}
