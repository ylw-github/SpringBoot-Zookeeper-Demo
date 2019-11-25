package com.ylw.zookeeper.slave;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    // 创建zk连接
    ZkClient zkClient = new ZkClient("192.168.162.131:2181");
    private String path = "/election";

    @Value("${server.port}")
    private String serverPort;

    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动完成...");
        createEphemeral();
        // 创建事件监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {

            // 节点被删除
            public void handleDataDeleted(String dataPath) throws Exception {
                // 主节点已经挂了，重新选举
                System.out.println("主节点已经挂了，重新开始选举");
                createEphemeral();
            }

            public void handleDataChange(String dataPath, Object data) throws Exception {

            }
        });

    }

    private void createEphemeral() {
        try {
            zkClient.createEphemeral(path, serverPort);
            ElectionMaster.isSurvival = true;
            System.out.println("serverPort：" + serverPort + ",选举成功....");
        } catch (Exception e) {
            ElectionMaster.isSurvival = false;
        }
    }

}