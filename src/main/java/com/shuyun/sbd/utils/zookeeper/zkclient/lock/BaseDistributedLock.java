package com.shuyun.sbd.utils.zookeeper.zkclient.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Component: 基础分布式锁
 * Description:
 * Date: 16/11/14
 *
 * @author yue.zhang
 */
public abstract class BaseDistributedLock implements DistributedLock{

    private final ZkClientExt client;

    private final String path;

    private final String basePath;

    private final String lockName;

    private static final Integer MAX_RETRY_COUNT = 10;

    public BaseDistributedLock(ZkClientExt client , String path , String lockName){
        this.client = client;
        this.basePath = path;
        this.path = path.concat("/").concat(lockName);
        this.lockName = lockName;
    }

    private void deleteOurPath(String ourPath) throws Exception{
        client.delete(ourPath);
    }

    private String createLockNode(ZkClient client , String path) throws Exception{
        return client.createEphemeralSequential(path,null);
    }

    private boolean waitToLock(long startMillis , long millisToWait, String ourPath) throws Exception{
        boolean haveTheLock = false;
        boolean doDelete = false;
        try{

            while (!haveTheLock){

                List<String> children = getSortedChildren();
                String sequenceNodeName = ourPath.substring(basePath.length()+1);
                int ourIndex = children.indexOf(sequenceNodeName);
                if(ourIndex<0){
                    throw new ZkNoNodeException("节点没有找到: " + sequenceNodeName);
                }


                boolean isGetTheLock = ourIndex == 0; // 如果是最小的那个接口（第0个节点）则说明已经获得锁
                String pathToWatch = isGetTheLock ? null: children.get(ourIndex - 1); // 获取次小的节点，监控这个节点的变化，一旦有变化则立马进行锁竞争
                if(isGetTheLock){
                    haveTheLock = true;
                }else {
                    String previousSequencePath = basePath.concat("/").concat(pathToWatch);

                    final CountDownLatch latch = new CountDownLatch(1);
                    // 监听次小节点的删除事件
                    final IZkDataListener previousListener = new IZkDataListener() {
                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {

                        }

                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            latch.countDown();
                        }
                    };

                    try {

                        client.subscribeDataChanges(previousSequencePath,previousListener);


                    }catch (ZkNoNodeException e){
                        e.printStackTrace();
                    }finally {
                        client.unsubscribeDataChanges(previousSequencePath,previousListener);
                    }

                }
            }

        }catch (Exception e){
            //发生异常需要删除节点
            doDelete = true;
            throw e;
        }finally {
            if(doDelete){
                deleteOurPath(ourPath);
            }
        }

        return haveTheLock;
    }

    private List<String> getSortedChildren() throws Exception{
        try{
            List<String> children = client.getChildren(basePath);

            Collections.sort(children, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    return getLockNodeNumber(lhs,lockName).compareTo(getLockNodeNumber(rhs,lockName));
                }
            });

            return children;

        }catch (ZkNoNodeException e){
            client.createPersistent(basePath,true);
            return getSortedChildren();
        }
    }

    private String getLockNodeNumber(String str, String lockName){
        int index = str.lastIndexOf(lockName);
        if(index > 0){
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }
}
