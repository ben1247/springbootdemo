package com.shuyun.sbd.utils.zookeeper.zkclient.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/11
 *
 * @author yue.zhang
 */
public class ManageServer {

    private String serversPath;
    private String commandPath;
    private String configPath;
    private ZkClient zkClient;
    private ServerConfig config;
    private IZkChildListener childListener;
    private IZkDataListener dataListener;
    private List<String> workServerList;

    public ManageServer(String serversPath , String commandPath , String configPath , ZkClient zkClient ,ServerConfig config){
        this.serversPath = serversPath;
        this.commandPath = commandPath;
        this.zkClient = zkClient;
        this.config = config;
        this.configPath = configPath;
        this.childListener = new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                workServerList = currentChilds;

                System.out.println("work server list changed , new list is ");
                execList();
            }
        };
        this.dataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                String cmd = new String((byte [])data );
                System.out.println("cmd:  " + cmd);
                exeCmd(cmd);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // ignore
            }
        };
    }

    public void start(){
        initRunning();
    }

    public void stop(){
        zkClient.unsubscribeDataChanges(commandPath, dataListener);
        zkClient.unsubscribeChildChanges(serversPath, childListener);
    }

    private void initRunning(){
        zkClient.subscribeDataChanges(commandPath, dataListener);
        zkClient.subscribeChildChanges(serversPath, childListener);
    }

    /**
     * 1. list: 查询出workServerList
     * 2. create: 创建config节点
     * 3. modify: 修改config节点的内容
     * @param cmdType
     */
    private void exeCmd(String cmdType){
        if("list".equals(cmdType)){
            execList();
        }else if("create".equals(cmdType)){
            execCreate();
        }else if("modify".equals(cmdType)){
            execModify();
        }else {
            System.out.println("error command! "+cmdType);
        }
    }

    private void execList(){
        System.out.println(workServerList.toString());
    }

    private void execCreate(){
        if(!zkClient.exists(configPath)){
            try {
                zkClient.createPersistent(configPath, JSON.toJSONString(config).getBytes());
            }catch (ZkNodeExistsException e){
                zkClient.writeData(configPath,JSON.toJSONString(config).getBytes());
            }catch (ZkNoNodeException e){
                // 父节点路径
                String parentDir = configPath.substring(0,configPath.lastIndexOf("/"));
                zkClient.createPersistent(parentDir,true);
                execCreate();
            }

        }
    }

    private void execModify(){
        // 作为演示，随意修改config的一个属性
        config.setDbUser(config.getDbUser() + "_Modify");

        try {
            zkClient.writeData(configPath,JSON.toJSONString(config).getBytes());
        }catch (ZkNoNodeException e){
            execCreate();
        }

    }

}
