package com.im.reac.fut;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureEx01 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<String> f = new FutureTask<String>(() -> {
           Thread.sleep(2000);
           log.info("Asyc");
           return "Hello";
        });

        es.execute(f); //비동기 작업을 하는 기본 메서드.  이전과 동일함

        System.out.println(f.isDone());
        Thread.sleep(2100);
        log.info("Exit");
        System.out.println(f.isDone());
        System.out.println(f.get());

        es.shutdown(); // 명시하지 않으면 끝나지 않음..그래서 빨간색이 계속 있었음
    }

}
