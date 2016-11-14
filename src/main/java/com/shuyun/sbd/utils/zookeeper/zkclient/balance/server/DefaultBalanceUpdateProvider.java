package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {

    private String serverPath;

    private ZkClient zkClient;

    public DefaultBalanceUpdateProvider(String serverPath, ZkClient zkClient) {
        this.serverPath = serverPath;
        this.zkClient = zkClient;
    }

    @Override
    public boolean addBalance(Integer step) {

        Stat stat = new Stat();
        ServerData sd;

        while (true){
            try {
                sd = zkClient.readData(this.serverPath,stat);
                sd.setBalance(sd.getBalance() + step);
                zkClient.writeData(this.serverPath, sd, stat.getVersion());
                System.out.println("============= ServerData addBalance: port: " + sd.getPort() + "  balance: " + sd.getBalance() + " ==============");
                return true;
            }catch (ZkBadVersionException e){
                // 当发生该错误时，说明有其他线程并发操作addBalance，所保证此次操作成功的方法是进行重试, while(true)
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

    }

    @Override
    public boolean reduceBalance(Integer step) {
        Stat stat = new Stat();
        ServerData sd;

        while (true){
            try {
                sd = zkClient.readData(this.serverPath,stat);
                final Integer currentBalance = sd.getBalance();
                sd.setBalance(currentBalance > step ? currentBalance - step : 0); // balance 不能为负数
                zkClient.writeData(this.serverPath, sd, stat.getVersion());
                System.out.println("============= ServerData reduceBalance: port: " + sd.getPort() + "  balance: " + sd.getBalance() + " ==============");
                return true;
            }catch (ZkBadVersionException e){
                // 当发生该错误时，说明有其他线程并发操作addBalance，所保证此次操作成功的方法是进行重试, while(true)
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}
