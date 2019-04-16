package com.ms.zg.book.circuitbreaker.command;

import com.ms.zg.book.circuitbreaker.model.User;
import com.ms.zg.book.circuitbreaker.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//继承HystrixCollapser类并且声明合并的类型、单次请求的类型和ID的类型
public class CommandCollapserGetUserById extends HystrixCollapser<List<User>, User, String> {
    private final String id;
    private final UserService userService;

    //注入用户id和用户服务
    public CommandCollapserGetUserById(String id, UserService userService) {
        this.id = id;
        this.userService = userService;
    }

    //获取请求参数，这里直接返回用户id
    @Override
    public String getRequestArgument() {
        return id;
    }

    //重写批量查询的方法
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collapsedRequests) {
        return new BatchUserCommand(collapsedRequests);
    }

    //重写响应和请求映射的方法
    @Override
    protected void mapResponseToRequests(List<User> batchResponse,
                                         Collection<CollapsedRequest<User, String>> collapsedRequests) {
        int count = 0;
        //根据查询顺序进行结果映射
        for (CollapsedRequest<User, String> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    //继承HystrixCommand，实现具体的批量查询方法
    public class BatchUserCommand extends HystrixCommand<List<User>> {

        private final Collection<HystrixCollapser.CollapsedRequest<User, String>> requests;

        private BatchUserCommand(Collection<HystrixCollapser.CollapsedRequest<User, String>> requests) {
            super(HystrixCommand.Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("test"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetUserById")));
            this.requests = requests;
        }

        @Override
        protected List<User> run() {
            List<String> ids = requests.stream()
                    .map(HystrixCollapser.CollapsedRequest::getArgument)
                    .collect(Collectors.toList());
            //调用依赖服务的批量查询用户的方法
            return userService.findUserByIds(ids);
        }
    }

}

