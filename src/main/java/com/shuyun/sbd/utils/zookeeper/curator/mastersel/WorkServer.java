package com.shuyun.sbd.utils.zookeeper.curator.mastersel;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component:
 * Description:
 * Date: 16/11/8
 *
 * @author yue.zhang
 */
public class WorkServer extends LeaderSelectorListenerAdapter implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(WorkServer.class);
    private final String name;
    private final LeaderSelector leaderSelector;
    private RunningListener listener;

    public RunningListener getListener() {
        return listener;
    }

    public void setListener(RunningListener listener) {
        this.listener = listener;
    }

    public WorkServer(CuratorFramework client , String path , String name){
        this.name = name;

        // create a leader selector using the given path for management
        // all participants in a given leader selection must use the same path
        // ExampleClient here is also a LeaderSelectorListener but this isn't required
        leaderSelector = new LeaderSelector(client,path,this);

        leaderSelector.autoRequeue();
    }

    public void start()throws IOException{
        leaderSelector.start();
        processStart(this.name);
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
        processStop(this.name);
    }

    /**
     * 拿到了master
     * @param client
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        processActiveEnter(this.name);
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        }catch (InterruptedException e){
            System.err.println(name + " was interrupted.");
            Thread.currentThread().interrupt();
        }finally {
            //System.out.println(name + " relinquishing leadership.\n");
            processActiveExit(this.name);
        }
    }

    // ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ 下面的几个方法是业务自定义方法，目前就只是打印出来 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝

    private void processStart(Object context){
        if(listener != null){
            try{
                listener.processStart(context);
            }catch (Exception e){
                logger.error("processStart failed",e);
            }
        }
    }

    private void processStop(Object context) {
        if (listener != null) {
            try {
                listener.processStop(context);
            } catch (Exception e) {
                logger.error("processStop failed", e);
            }
        }
    }

    private void processActiveEnter(Object context) {
        if (listener != null) {
            try {
                listener.processActiveEnter(context);
            } catch (Exception e) {
                logger.error("processActiveEnter failed", e);
            }
        }
    }

    private void processActiveExit(Object context) {
        if (listener != null) {
            try {
                listener.processActiveExit(context);
            } catch (Exception e) {
                logger.error("processActiveExit failed", e);
            }
        }
    }

}
