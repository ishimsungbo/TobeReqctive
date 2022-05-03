package com.im.reac.fut;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureEx {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();

        Future<String> f = es.submit( () -> {
            Thread.sleep(2000);
            log.info("Asyc");
            return "Hello";
        });

        System.out.println(f.isDone());
        Thread.sleep(2100);
        log.info("Exit");
        System.out.println(f.isDone());
        System.out.println(f.get());

    }

}
