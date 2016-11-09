package com.shuyun.sbd.utils.zookeeper.curator.mastersel;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/8
 *
 * @author yue.zhang
 */
public class LeaderSelectorCurator {

    private static final int CLIENT_QTY = 10;

    private static final String PATH = "/leader";

    public static void main(String [] args){

        List<CuratorFramework> clients = Lists.newArrayList();
        List<WorkServer> workServers = Lists.newArrayList();

        try{

            for(int i = 0 ; i < CLIENT_QTY; i++){
                CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",new ExponentialBackoffRetry(1000,3));
                clients.add(client);

                WorkServer workServer = new WorkServer(client,PATH,"Client #" + i);

                workServer.setListener(new RunningListener() {
                    @Override
                    public void processStart(Object context) {
                        System.out.println(context.toString()+" processStart...");
                    }

                    @Override
                    public void processStop(Object context) {
                        System.out.println(context.toString()+" processStop...");
                    }

                    @Override
                    public void processActiveEnter(Object context) {
                        System.out.println(context.toString()+" processActiveEnter...");
                    }

                    @Override
                    public void processActiveExit(Object context) {
                        System.out.println(context.toString()+" processActiveExit...");
                    }
                });

                workServers.add(workServer);

                client.start();
                workServer.start();

            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Shutting down...");
            for (WorkServer workServer : workServers) {
                CloseableUtils.closeQuietly(workServer);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }

    }

}
