package com.im.reac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

// 2022-05-04 퓨처 기능.. 자바 Asunc 공부해보기
@SpringBootApplication
@Slf4j
@EnableAsync
public class RxReactiveApplication {

    @Component
    public static class MyService{
        @Async(value = "tp")
        public ListenableFuture<String> hello() throws InterruptedException{
            log.info("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello");
        }
    }

    @Bean
    ThreadPoolTaskExecutor tp(){
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100);   //
        te.setQueueCapacity(200);  //대기해... 10개가 차면 큐가 차면...그때 맥스를 실행
        te.setThreadNamePrefix("mythread"); // ==> mythread 1 를 출력할듯...
        te.initialize();
        return te;
    }

    public static void main(String[] args) {

        try(ConfigurableApplicationContext c = SpringApplication.run(RxReactiveApplication.class, args)){
        };
    }

    @Autowired MyService myService;

    @Bean
    ApplicationRunner run(){
        return args ->{
            log.info("run()");
            ListenableFuture<String> f = myService.hello();
            f.addCallback(s->System.out.println(s),e -> System.out.println(e.getMessage())); // Callback 처리
            log.info("exit");
        };
    }

}
