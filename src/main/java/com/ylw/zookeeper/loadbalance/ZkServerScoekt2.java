package com.ylw.zookeeper.loadbalance;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZkServerScoekt2 implements Runnable {

    private static int port = 18081;  //socket 服务启动后的所使用的 端口号

    public static void main(String[] args) throws IOException {
        ZkServerScoekt server = new ZkServerScoekt(port);  //构造函数传入port
        regServer();  //去zk注册
        Thread thread = new Thread(server);
        thread.start();
    }

    public ZkServerScoekt2(int port) {
        this.port = port;
    }

    //注册服务
    public static void regServer() {
        //1、建立zk连接
        ZkClient zkClient = new ZkClient("192.168.162.131", 5000, 10000);
        //2.先创建父节点
        String root = "/toov5";
        if (!zkClient.exists(root)) {
            //如果父节点不存，直接创建父节点
            zkClient.createPersistent(root);  //持久节点
        }
        //3、创建子节点
        String nodeName = root + "/service_" + port;
        String nodeValue = "127.0.0.1:" + port;
        if (zkClient.exists(nodeName)) {  //如果存在 直接删除掉
            zkClient.delete(nodeName);
        }
        zkClient.createEphemeral(nodeName, "127.0.0.1:" + port);
        System.out.println("服务注册成功" + nodeName);

    }


    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server start port:" + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e2) {

            }
        }
    }
}
