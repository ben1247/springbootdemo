package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Component: 业务场景：任务队列化
 * Description: 使用有序节点，这样做有2个好处
 * 第一，序列号指定了任务被队列化的顺序
 * 第二，可以通过很少的工作为任务创建基于序列号的唯一路径。
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class Client implements Watcher{

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private ZooKeeper zk;

    private String hostPort;

    private final static String NODE_PATH = "/tasks";



    public Client(String hostPort){
        this.hostPort = hostPort;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public void startZK()throws Exception{
        zk = new ZooKeeper(hostPort,15000,this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    /**
     * 创建任务
     * @param command
     * @return
     * @throws Exception
     */
    public String createTask(String command)throws Exception{
        try{
            String name = zk.create(NODE_PATH + "/task-",command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);

            // 我们无法确定使用CreateMode.EPHEMERAL_SEQUENTIAL调用create的序列号，create方法会返回新建节点的名称
            return name;
        }catch (KeeperException.NodeExistsException e){
            throw new Exception(command + " already appears to be running");
        }catch (KeeperException.ConnectionLossException e){
        }
        return null;
    }


    // ===================== 主节点等待新任务进行分配 ====================================
    // 注意：主节点在成功分配任务后，会删除/tasks节点下对应的任务。这种方式简化了主节点角色接收新任务并分配的设计，
    // 如果任务列表中混合的已分配和未分配的任务，主节点还需要区分这些任务。


    /**
     * 获取任务信息
     */
    public void getTasks(){
        zk.getChildren(NODE_PATH,tasksChangeWatcher,tasksGetChildrenCallback,null);
    }

    Watcher tasksChangeWatcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if(event.getType() == Event.EventType.NodeChildrenChanged){
                assert NODE_PATH.equals(event.getPath());
                getTasks();
            }
        }
    };

    AsyncCallback.ChildrenCallback tasksGetChildrenCallback = new AsyncCallback.ChildrenCallback(){
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    getTasks();
                    break;
                case OK:
                    if(children != null){
                        assignTasks(children); // 分配任务
                    }
                    break;
                default:
                    LOGGER.error("getChildren failed. ",KeeperException.create(KeeperException.Code.get(rc),path));
            }

        }
    };

    /**
     * 分配任务
     */
    public void assignTasks(List<String> tasks){
        for(String task : tasks){
            getTaskData(task);
        }
    }

    public void getTaskData(String task){
        zk.getData(NODE_PATH + "/" + task,false,taskDataCallback,task);
    }

    AsyncCallback.DataCallback taskDataCallback = new AsyncCallback.DataCallback(){
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    getTaskData((String)ctx);
                    break;
                case OK:
                    // Choose worker at random
                    int worker = new Random().nextInt(getWorkerList().size());
                    String designatedWorker = getWorkerList().get(worker);

                    // assign task to randomly chosen worker
                    String assignmentPath = "/assign/" + designatedWorker + "/" + ctx;

                    createAssignment(assignmentPath,data);
                    break;
                default:
                    LOGGER.error("Error when trying to get task data.",KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    /**
     * 创建分配节点，路径形式为/assign/worker-id/task-num
     * @param path
     * @param data
     */
    public void createAssignment(String path , byte [] data){
        zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT,assignTaskCallback,data);
    }

    AsyncCallback.StringCallback assignTaskCallback = new AsyncCallback.StringCallback(){
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    createAssignment(path,(byte[])ctx);
                    break;
                case OK:
                    LOGGER.info("Task assigned correctly: " + name);
                    deleteTask(name.substring(name.lastIndexOf("/")+1)); // 分配完任务后就可以删除/tasks 下对应的任务节点了
                    break;
                default:
                    LOGGER.error("Error when trying to assign task. ", KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    public void deleteTask(String path){
        zk.delete(path,-1,deleteTaskCallback,null);
    }

    AsyncCallback.VoidCallback deleteTaskCallback = new AsyncCallback.VoidCallback(){
        @Override
        public void processResult(int rc, String path, Object ctx) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    deleteTask(path);
                    break;
                case OK:
                    LOGGER.info("Task Delete correctly: " + path);
                    break;
                default:
                    LOGGER.error("Error when trying to delete task",KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    // 测试数据，真实情况应从zk取出放入缓存中
    private List<String> getWorkerList(){
        List<String> list = new ArrayList<>();
        list.add("worker1");
        list.add("worker2");
        list.add("worker3");
        list.add("worker4");
        list.add("worker5");
        list.add("worker6");
        return list;
    }

    // ========================= 客户段等待任务的执行结果 ============================

    void submitTask(String task , TaskObject taskCtx){
        taskCtx.setTask(task);
        zk.create("/tasks/task-",
                task.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL,
                createTaskCallback,
                taskCtx);
    }

    AsyncCallback.StringCallback createTaskCallback = new AsyncCallback.StringCallback(){

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    submitTask(((TaskObject)ctx).getTask(),(TaskObject)ctx);
                    break;
                case OK:
                    LOGGER.info("My created task name: " + name);
                    ((TaskObject) ctx).setTaskName(name);
                    watchStatus("/status/" + name.replace("/tasks/",""),ctx);
                    break;
                default:
                    LOGGER.error("Something went wrong",KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    ConcurrentHashMap<String,Object> ctxMap = new ConcurrentHashMap<>();

    void watchStatus(String path , Object ctx){
        ctxMap.put(path,ctx);
        zk.exists(path,statusWatcher,existsCallback,ctx);
    }

    Watcher statusWatcher = new Watcher(){
        @Override
        public void process(WatchedEvent event) {
            if(event.getType() == Event.EventType.NodeCreated){
                assert event.getPath().contains("/status/tasks-");
                zk.getData(event.getPath(),false,taskDataCallback,ctxMap.get(event.getPath()));
            }
        }
    };


    AsyncCallback.StatCallback existsCallback = new AsyncCallback.StatCallback(){
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    watchStatus(path,ctx);
                    break;
                case OK:
                    if(stat != null){
                        zk.getData(path,false,taskDataCallback,null);
                    }
                    break;
                case NONODE:
                    break;
                default:
                    LOGGER.error("Something went wrong when checking if the status node exists: " + KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    Op deleteZnode(String z){
        return Op.delete(z,-1);
    }

    public void testMultiOp() throws KeeperException, InterruptedException {
        List<OpResult> results = zk.multi(Arrays.asList(deleteZnode("/a/b"),deleteZnode("/a")));
    }

    public static void main(String[]args)throws Exception{
        String hostPort =  "127.0.0.1:2181";
        Client c = new Client(hostPort);
        c.startZK();

        String name = c.createTask("cmd");

        System.out.print("Created: " + name);

        c.stopZK();
    }
}
