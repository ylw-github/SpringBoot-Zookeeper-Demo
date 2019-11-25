package com.ylw.zookeeper.lock.old;


public class OrderServiceSync implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    public void run() {
        getNumber();
    }

    public void getNumber() {
        synchronized (this) {
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
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
