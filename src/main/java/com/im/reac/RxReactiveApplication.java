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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/*
    1. sevlet1 thread : req -> blocking(DB, API) < A중간에 무슨 쓰레드가 있다면. >-> res(html)    : A를 돌리는 순간 스레드가 다른 작업을 할 수 있다면 좋을텐데...
    2. sevlet2
    3. sevlet3
    4. sevlet4
    5. sevlet5

    * 5개가 차면 Q에 걸리고  latency(자극과 반응 사이의 시간) 높아짐. 처리율은 낮아짐.
    레이턴시(latency)는 자극과 반응 사이의 시간이며,
    더 일반적인 관점에서는 관찰되는 시스템에서의 어떠한 물리적 변화에 대한 원인과 결과 간의 지연 시간이다.

    * 스레드를 많아지면 oom, cpu가 무하가 걸림.

    ==> 서블릿 3.0에서 부터 비동기 서블릿 기능 추가
        서블릿 3.1부터 논블럭킹, CallBack 로 함... 이상적인 웹기반이 됨.

        *** 스프링을 쓰면서 어떻게 사용하는가
 */

@SpringBootApplication
@Slf4j
@EnableAsync
public class RxReactiveApplication {

    @RestController
    public static class MyController{

        @GetMapping("/callable")
        private Callable<String> callable() throws InterruptedException {
            log.info("callable");
            return () -> {
                log.info("async");
                Thread.sleep(2000);
                return "hello";
            };
        }
//        public String callable() throws InterruptedException{
//            log.info("async");
//            Thread.sleep(2000);
//            return "hello";
//        }
    }


    public static void main(String[] args) {
        SpringApplication.run(RxReactiveApplication.class, args);
    }



}
