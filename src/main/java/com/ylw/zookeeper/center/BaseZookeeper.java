package com.ylw.zookeeper.center;

import org.I0Itec.zkclient.ZkClient;

public abstract  class BaseZookeeper {
    // zk连接地址
    private static final String CONNECTSTRING = "192.168.162.131:2181";

    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);
}
