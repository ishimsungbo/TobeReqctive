package com.im.reac.fut;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
public class FutureEx02 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<String> f = new FutureTask<String>(() -> {
           Thread.sleep(2000);
           log.info("Asyc");
           return "Hello";
        }) {  //비동기 작업이 끝날 경우
            @Override
            protected void done() {
                try{
                    System.out.println(get());
                }catch (ExecutionException | InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        es.execute(f); //비동기 작업을 하는 기본 메서드.  이전과 동일함
        es.shutdown(); // 명시하지 않으면 끝나지 않음..그래서 빨간색이 계속 있었음
    }

}
