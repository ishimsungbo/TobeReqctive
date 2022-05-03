package com.im.reac.fut;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

//비동기 작업이 끝났을때 뭔가 담을 수 있는 것.?

/**
 * Future
 * CallBack를 구현...하는 방식이였음.
 */

@Slf4j
public class FutureEx03_callback {

    interface SuccessCallback{
        void onSuccess(String result);
    }
    interface ExceptionCallback{
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String>{

        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc , ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);  //null 이 어떤 상황에서도 오지 않도록 함. 컴파일 단계에서 에러
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try{
                sc.onSuccess(get());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                //e.printStackTrace();
            }catch (ExecutionException e){
                ec.onError(e.getCause());
                //e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask f = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            if (1==1) throw new RuntimeException("Async ERROR !!!");  //여기서 멈춤 .
            log.info("Async");
            return "Hello";
        },
                s-> System.out.println("Result : " + s),
                e-> System.out.println("Error : " + e.getMessage()));

        es.execute(f); //비동기 작업을 하는 기본 메서드.  이전과 동일함
        es.shutdown(); // 명시하지 않으면 끝나지 않음..그래서 빨간색이 계속 있었음
    }

}
