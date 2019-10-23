package com.junzhang.serviceredislock;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketTest {

     // AtomicInteger
    // 2. 修改为 AtomicInteger 原子操作类
    private int count = 20;

    // 3. Lock
    Lock lock = new ReentrantLock();


    @Test
    public void test() throws InterruptedException {
        TicketRunnable tr = new TicketRunnable();
        Thread t1 = new Thread(tr,"窗口A");
        Thread t2 = new Thread(tr,"窗口B");
        Thread t3 = new Thread(tr,"窗口C");
        Thread t4 = new Thread(tr,"窗口D");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        Thread.currentThread().join();
    }


    public class TicketRunnable implements Runnable{

        @Override
        public void run() {
            while (count > 0){
//                if(count > 0){
//                    System.out.println(Thread.currentThread().getName()+" 售出了第"+count--+"张票");
//                }

                // 1.synchronized
//                synchronized (this.getClass()){
//                    if(count > 0){
//                        System.out.println(Thread.currentThread().getName()+" 售出了第"+count--+"张票");
//                    }
//                }

                // 3.lock
//                lock.lock();
//                try {
//                    if (count > 0) {
//                        System.out.println(Thread.currentThread().getName() + " 售出了第" + count-- + "张票");
//                    }
//                }catch (Exception e){
//                }finally {
//                    lock.unlock(); // 避免死锁
//                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
