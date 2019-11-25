package com.ylw.zookeeper.center;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private UpdateInfoService updateInfoService;

    @RequestMapping("/getInfo")
    public String getInfo() {
        return configUtils.getUserKey();
    }

    @RequestMapping("/updateInfo")
    public String updateInfo(String key, String value) {
        String updateInfo = updateInfoService.updateInfo(key, value);
        return updateInfo;
    }
}