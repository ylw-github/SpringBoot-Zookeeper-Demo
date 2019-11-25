package com.ylw.zookeeper.slave;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/getServerInfo")
    public String getServerInfo() {
        return "serverPort:" + serverPort + (ElectionMaster.isSurvival ? "选举为主服务器" : "该服务器为从服务器");
    }
}