package com.shuyun.sbd.utils.zookeeper.zkclient.queue;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Component: 阻塞分布队列
 * Description:
 * Date: 16/11/16
 *
 * @author yue.zhang
 */
public class DistributedBlockingQueue<T> extends DistributedSimpleQueue<T> {

    public DistributedBlockingQueue(ZkClient zkClient, String root) {
        super(zkClient, root);
    }

    @Override
    public T poll() throws Exception{

        while (true){
            final CountDownLatch latch = new CountDownLatch(1);
            IZkChildListener childListener = new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    latch.countDown();
                }
            };
            zkClient.subscribeChildChanges(root,childListener);

            try{

                T node = super.poll();
                if(node != null){
                    return node;
                }else {
                    latch.await();
                }

            }finally {
                zkClient.unsubscribeChildChanges(root,childListener);
            }
        }

    }
}
