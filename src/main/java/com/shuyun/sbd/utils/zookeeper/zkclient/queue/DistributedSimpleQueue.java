package com.shuyun.sbd.utils.zookeeper.zkclient.queue;

import org.I0Itec.zkclient.ExceptionUtil;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/16
 *
 * @author yue.zhang
 */
public class DistributedSimpleQueue<T> {

    protected final ZkClient zkClient;

    protected final String root; // 代表根节点

    protected static final String NODE_NAME = "n_"; // 顺序节点的名称

    public DistributedSimpleQueue(ZkClient zkClient , String root){
        this.zkClient = zkClient;
        this.root = root;
    }

    public int size(){
        return zkClient.countChildren(root);
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * 向队列提交数据
     * @return
     */
    public boolean offer(T element) throws Exception{
        String nodeFullPath = root.concat("/").concat(NODE_NAME);
        try {
            zkClient.createPersistentSequential(nodeFullPath, element);
        }catch (ZkNoNodeException e){
            zkClient.createPersistent(root);
            offer(element);
        }catch (Exception e){
            throw ExceptionUtil.convertToRuntimeException(e);
        }
        return true;
    }

    // 向队列取数据
    public T poll() throws Exception{

        try{

            List<String> children = zkClient.getChildren(root);
            if(children.size() == 0){
                return null;
            }

            Collections.sort(children, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return getNodeNumber(o1,NODE_NAME).compareTo(getNodeNumber(o2,NODE_NAME));
                }
            });

            for(String nodeName : children){
                String nodeFullPath = root.concat("/").concat(nodeName);

                try {
                    T node = zkClient.readData(nodeFullPath);
                    zkClient.delete(nodeFullPath);
                    return node;
                }catch (ZkNoNodeException e){
                    // 说明有其他节点已经消费掉了
                }
            }

            return null;

        }catch (Exception e){
            throw ExceptionUtil.convertToRuntimeException(e);
        }

    }

    private String getNodeNumber(String str , String nodeName){
        int index = str.lastIndexOf(nodeName);
        if(index >= 0){
            index += NODE_NAME.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

}
