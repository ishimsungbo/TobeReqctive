package com.im.reac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 100개의 요청해보기
@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);  //증가시킴

    public static void main(String[] args) throws InterruptedException{
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url ="http://localhost:8080/callable";

        StopWatch main = new StopWatch();
        main.start();

        for(int i=0 ; i < 100 ; i++){
            es.execute(()->{
                int idx = counter.addAndGet(1);
                log.info("Thread {} ",idx);

                StopWatch sw = new StopWatch();
                sw.start();

                rt.getForObject(url, String.class);

                sw.stop();
                log.info("Elapsed: {} {}" , idx, sw.getTotalTimeSeconds());
            });
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS); //지정된 시간이

        main.stop();
        log.info("Total: {} " + main.getTotalTimeSeconds());
    }
}
