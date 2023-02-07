package com.gugnv.example.guava;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;

/**
 * @author yuliang Liu
 * @date 2020/1/8 17:04
 */
public class MoreExecutorsDemo {
    public static void main(String[] args) {
        Executor executor = MoreExecutors.sameThreadExecutor();
        executor.execute(() -> System.out.println("I am running in " + Thread.currentThread().getName()));
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("I am running in " + Thread.currentThread().getName());
            }
        });
    }
}
