package com.ms.zg.book.pactconsumer;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.ms.zg.book.pactconsumer.common.JsonUtils;
import com.ms.zg.book.pactconsumer.model.Role;
import com.ms.zg.book.pactconsumer.model.User;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserClientJunitTest extends ConsumerPactTestMk2 {
    //创建契约
    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        final User user = new User("1", "zhanggang",
                new Role[]{new Role(1, "admin")});
        final String userJson = JsonUtils.toJson(user);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        //使用PactDslWithProvider构建一个契约的请求和响应信息
        return builder
                .given("ConsumerPactTestMk2 test GET /user/{id}")
                .uponReceiving("get user by id")
                .method("GET")
                .path("/users/1")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(userJson)
                .toPact();

    }

    @Override
    protected String providerName() {
        return "user-service";
    }

    @Override
    protected String consumerName() {
        return "order-service";
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext pactTestExecutionContext) throws IOException {
        /*
        //使用Request.Get获取契约接口的返回JSON
        String userJson = Request.Get(mockServer.getUrl() + "/users/1").execute().returnContent().asString();
        //反序列化JSON
        final User user = JsonUtils.parseJson(userJson, User.class);
        */
        //使用RestTemplate获取契约接口的返回JSON
        final User user = new RestTemplate().getForObject(mockServer.getUrl() + "/users/{id}", User.class, "1");

        //断言测试接口响应是否符合预期
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("zhanggang");
        assertThat(user.getRoles()).extracting(Role::getName).contains("admin");

    }

}
