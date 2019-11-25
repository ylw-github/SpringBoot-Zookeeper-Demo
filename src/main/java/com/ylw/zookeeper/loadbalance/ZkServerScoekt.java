package com.ylw.zookeeper.loadbalance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZkServerScoekt implements Runnable {

    private int port = 18080;

    public ZkServerScoekt(int port) {
        this.port = port;
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
                e2.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 18080;
        ZkServerScoekt server = new ZkServerScoekt(port);
        Thread thread = new Thread(server);
        thread.start();
    }
}
