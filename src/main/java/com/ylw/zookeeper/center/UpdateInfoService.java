package com.ylw.zookeeper.center;

import org.springframework.stereotype.Service;

@Service
public class UpdateInfoService extends BaseZookeeper {
    public String updateInfo(String key, String value) {
        try {
            zkClient.writeData("/" + key, value);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
}
