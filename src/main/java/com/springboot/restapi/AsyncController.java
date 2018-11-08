package com.springboot.restapi;

import com.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/async")
public class AsyncController {

    protected static Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncTaskExecutor executor;

    /**
     * 同步请求，不受任何server 端设置 timeout 参数影响，
     * 只要进入，client会一直等待，除非client有设置timeout
     * @return
     */
    @RequestMapping("longSync")
    @ResponseBody
    public User longSync() {
        logger.info("Entering controller");
        Callable<User> asyncTask = new UserTimeCaller(60000);
        User result = null;
        try {
            result = asyncTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Leaving  controller");
        return result;
    }

    /**
     * Callable 将在 WebAsyncManager.taskExecutor的 thread 中运行 (WebAsyncUtils.getAsyncManager(request))
     * @return
     */

    @RequestMapping(value = "shortCallable", produces = "application/json;charset=UTF-8")
    public Callable<User> shortCallable() {
        logger.info("Entering controller");
        Callable<User> asyncTask = new UserTimeCaller(10000);
        logger.info("Leaving  controller");
        return asyncTask;
    }

    /**
     *  tomcat AsyncTimeout = 30s (default),决定async 的timeout
     *  org.springframework.web.context.request.async.AsyncRequestTimeoutException: null
     * @return
     */
    @RequestMapping("longCallable")
    public Callable<User> longCallable() {
        logger.info("Entering controller");
        Callable<User> asyncTask = new UserTimeCaller(60000); //
        logger.info("Leaving  controller");
        return asyncTask;
    }


    /**
     * 需要手动 DeferredResult.setResult() 设置结果
     * @return
     */
    @RequestMapping(value = "shortDeferred", produces = "application/json;charset=UTF-8")
    public DeferredResult<User> shortDeferredResult() {
        logger.info("Deferred time request -> Entering controller");
        DeferredResult<User> result = new DeferredResult<>();
        result.onTimeout(() -> logger.info("shortDeferredResult -> onTimeout"));
        result.onCompletion(() -> logger.info("shortDeferredResult -> onCompletion"));
        new Thread(new UserDeferred(10000, result), "MyThread").start();
        logger.info("Deferred time request -> Leaving  controller");
        return result;
    }

    @RequestMapping(value = "longDeferred", produces = "application/json;charset=UTF-8")
    public DeferredResult<User> longDeferredResult() {
        logger.info("Deferred time request -> Entering controller");
        DeferredResult<User> result = new DeferredResult<>(20000l, new User("time out"));
        result.onCompletion(() -> logger.info("longDeferredResult -> onCompletion"));
        result.onTimeout(() -> {
            logger.info("longDeferredResult -> onTimeout");
            // result.setErrorResult(new User("time out"));
        });
        new Thread(new UserDeferred(60000, result), "MyThread").start();
        logger.info("Deferred time request -> Leaving  controller");
        return result;
    }

    /**
     * 需要a task executor.  <task:executor></task:executor>
     *
     * @return
     */
    @RequestMapping(value = "shortWebAsync", produces = "application/json;charset=UTF-8")
    public WebAsyncTask<User> shortWebAsyncTask() {
        logger.info("Entering controller");
        Callable<User> asyncTask = new UserTimeCaller(10000);
        logger.info("Leaving  controller");
        return new WebAsyncTask<>(asyncTask);
    }

    /**
     * 处理timeoute
     *
     * @return
     */
    @RequestMapping(value = "longWebAsync", produces = "application/json;charset=UTF-8")
    public WebAsyncTask<User> longWebAsyncTask() {
        logger.info("Entering controller");
        Callable<User> asyncTask = new UserTimeCaller(60000);
        logger.info("Leaving  controller");
        WebAsyncTask<User> userWebAsyncTask = new WebAsyncTask<>(20000l, executor, asyncTask);
        userWebAsyncTask.onCompletion(() -> logger.info("longWebAsync -> onCompletion"));
        userWebAsyncTask.onTimeout(() -> {
            logger.info("longWebAsync -> onTimeout");
            return new User("time out");
        });
        return userWebAsyncTask;
    }
}


class UserTimeCaller implements Callable<User> {

    private long sleepMillis;

    public UserTimeCaller(long sleepMillis) {
        this.sleepMillis = sleepMillis;
    }

    @Override
    public User call() throws Exception {
        AsyncController.logger.info("begin call() ...");
        Thread.sleep(sleepMillis);
        AsyncController.logger.info("end call() !");
        return new User("Hello World ! Thread[" + Thread.currentThread() + "]");
    }
}

class UserDeferred implements Runnable {

    private long sleepMillis;
    private DeferredResult deferredResult;

    public UserDeferred(long sleepMillis, DeferredResult deferredResult) {
        this.sleepMillis = sleepMillis;
        this.deferredResult = deferredResult;
    }

    @Override
    public void run() {
        AsyncController.logger.info("begin run() ...");
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AsyncController.logger.info("end run() !");
        deferredResult.setResult(new User("Hello World ! Thread[" + Thread.currentThread() + "]"));
    }
}