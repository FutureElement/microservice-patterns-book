package com.ms.zg.book.circuitbreaker.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloCommand extends HystrixCommand<String> {

    private final String name;

    public HelloCommand(String name) {
        //设置线程池的key
        super(HystrixCommandGroupKey.Factory.asKey("HelloGroup"));
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

