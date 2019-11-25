package com.ylw.zookeeper.lock.old;


import java.util.concurrent.locks.ReentrantLock;

public class OrderServiceSyncLock implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    // 使用lock锁
    private java.util.concurrent.locks.Lock lock = new ReentrantLock();

    public void run() {
        getNumber();
    }

    public void getNumber() {
        try {
            // synchronized (this) {
            lock.lock();
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
            // }

        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        System.out.println("####生成唯一订单号###");
        OrderService orderService = new OrderService();
        for (int i = 0; i < 100; i++) {
            new Thread(orderService).start();
        }

    }
}
