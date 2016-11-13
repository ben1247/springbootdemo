package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ServerHandler extends ChannelHandlerAdapter{

    private final BalanceUpdateProvider balanceUpdateProvider;

    private static final Integer BALANCE_STEP = 1;

    public ServerHandler(BalanceUpdateProvider balanceUpdateProvider){
        this.balanceUpdateProvider = balanceUpdateProvider;
    }

    public BalanceUpdateProvider getBalanceUpdateProvider() {
        return balanceUpdateProvider;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client connect...");
        balanceUpdateProvider.addBalance(BALANCE_STEP);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client disConnect...");
        balanceUpdateProvider.reduceBalance(BALANCE_STEP);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
