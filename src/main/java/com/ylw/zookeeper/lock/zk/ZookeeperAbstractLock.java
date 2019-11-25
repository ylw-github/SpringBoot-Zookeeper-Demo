package com.ylw.zookeeper.lock.zk;


import org.I0Itec.zkclient.ZkClient;

/**
 * 将重复代码写入子类中..
 */
public abstract class ZookeeperAbstractLock implements Lock {

    // zk连接地址
    private static final String CONNECTSTRING = "192.168.162.131:2181";

    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);

    protected static final String PATH = "/lock";

    public void getLock() {
        if (tryLock()) {
            System.out.println("##获取lock锁的资源####");
        } else {
            // 等待
            waitLock();
            // 重新获取锁资源
            getLock();
        }

    }

    // 获取锁资源
    abstract boolean tryLock();

    // 等待
    abstract void waitLock();

    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
            System.out.println("释放锁资源...");
        }
    }

}