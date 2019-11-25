package com.ylw.zookeeper.lock.zk;

public interface Lock {

    //获取到锁的资源
    void getLock();

    // 释放锁
    void unLock();
}
