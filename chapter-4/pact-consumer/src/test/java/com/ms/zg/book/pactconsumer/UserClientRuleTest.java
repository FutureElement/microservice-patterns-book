package com.ms.zg.book.pactconsumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.ms.zg.book.pactconsumer.common.JsonUtils;
import com.ms.zg.book.pactconsumer.model.Role;
import com.ms.zg.book.pactconsumer.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserClientRuleTest {
    //定义服务提供者
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("user-service", this);

    //创建消费者契约
    @Pact(consumer = "order-service")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        final User user = new User("1", "zhanggang", new Role[]{new Role(1, "admin")});
        final String userJson = JsonUtils.toJson(user);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return builder
                .given("Rule test GET /user/{id}")
                .uponReceiving("Get user by id for rule test")
                .method("GET")
                .path("/users/1")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(userJson).toPact();
    }

    //测试契约
    @Test
    @PactVerification
    public void runTest() {
        //使用mockProvider获取服务提供者的URL
        final User user = new RestTemplate().getForObject(mockProvider.getUrl() + "/users/{0}",
                User.class, "1");
        //断言接口返回结果是否符合预期
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("zhanggang");
        assertThat(user.getRoles()).extracting(Role::getName).contains("admin");
    }

}
