package com.ylw.zookeeper.center;

import org.I0Itec.zkclient.IZkDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner extends BaseZookeeper implements ApplicationRunner {

    @Autowired
    private ConfigUtils configUtils;

    // 启动后执行方法
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动成功...");
        String userValue = configUtils.getUserKey();
        String userKey = "/userKey";
        try {
            // 创建节点信息
            zkClient.createEphemeral(userKey, userValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        zkClient.subscribeDataChanges(userKey, new IZkDataListener() {
            public void handleDataDeleted(String dataPath) throws Exception {
            }

            // 当值发生变化的时候
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("dataPath:" + dataPath + ",data:" + data);
                final String strData = (String) data;
                configUtils.setUserKey(strData);
            }
        });

    }

}