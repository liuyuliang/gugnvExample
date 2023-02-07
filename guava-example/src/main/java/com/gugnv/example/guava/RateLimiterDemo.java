package com.gugnv.example.guava;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author yuliang Liu
 * @date 2020/1/6 17:15
 */
public class RateLimiterDemo {
    static RateLimiter rateLimiter = RateLimiter.create(2);

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            rateLimiter.acquire();
            //如果无法执行请求，就丢弃
            rateLimiter.tryAcquire();
            new Thread(new Task()).start();
        }
    }
}
