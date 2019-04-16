package com.ms.zg.book.circuitbreaker.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class HelloCommand2 extends HystrixCommand<String> {

    private final String name;

    public HelloCommand2(String name) {
        //信号量
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloGroup")).
                andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(
                        HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE))
        );
        this.name = name;

    }

    @Override
    protected String run() {
        //..这里一般执行我们真正的调用逻辑，此处我们模拟一个异常的发生
        throw new IllegalArgumentException("test failed");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
}

