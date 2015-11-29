package com.shuyun.sbd.utils.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.Collection;

/**
 * Component: 事务例子
 * Description: CuratorFramework提供了事务的概念，可以将一组操作放在一个原子事务中。
 * 什么叫事务？ 事务是原子的， 一组操作要么都成功，要么都失败。
 * Date: 15/11/28
 *
 * @author yue.zhang
 */
public class CuratorTransactionExample {

    public static void main(String[] args) throws Exception {

        String zookeeperConnectionString = "127.0.0.1";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .namespace("curatordemo")
                .connectString(zookeeperConnectionString)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        transaction(client);

        CloseableUtils.closeQuietly(client);

    }

    /**
     * this example shows how to use ZooKeeper's new transactions
     * @param client
     * @return
     * @throws Exception
     */
    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception {
        Collection<CuratorTransactionResult> results = client.inTransaction().create().forPath("/a/path", "some a data".getBytes())
                .and().create().forPath("/b/path","some b data".getBytes())
                .and().create().forPath("/c/path","some c data".getBytes())
                .and().commit();

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType());
        }
        return results;
    }

    /**
     * These next four methods show how to use Curator's transaction APIs in a
     * more traditional - one-at-a-time - manner
     * @param client
     * @return
     */
    public static CuratorTransaction startTransaction(CuratorFramework client) {
        // start the transaction builder
        return client.inTransaction();
    }

    /**
     * add a create operation
     * @param transaction
     * @return
     * @throws Exception
     */
    public static CuratorTransactionFinal addCreateToTransaction(CuratorTransaction transaction) throws Exception {
        return transaction.create().forPath("/a/path", "some data".getBytes()).and();
    }

    /**
     * add a delete operation
     * @param transaction
     * @return
     * @throws Exception
     */
    public static CuratorTransactionFinal addDeleteToTransaction(CuratorTransaction transaction) throws Exception {
        return transaction.delete().forPath("/another/path").and();
    }

    /**
     * commit the transaction
     * @param transaction
     * @throws Exception
     */
    public static void commitTransaction(CuratorTransactionFinal transaction) throws Exception {
        transaction.commit();
    }

}
