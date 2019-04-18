package com.ms.zg.book.pactconsumer;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.RequestResponsePact;
import com.ms.zg.book.pactconsumer.common.JsonUtils;
import com.ms.zg.book.pactconsumer.model.Role;
import com.ms.zg.book.pactconsumer.model.User;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static org.assertj.core.api.Assertions.assertThat;

public class UserClientDSLTest {
    //创建一个RestTemplate用于测试契约
    private RestTemplate restTemplate = new RestTemplate();

    //直接编写一个测试方法
    @Test
    public void test_pact_contract_get_user_by_id() {
        final User user = new User("1", "zhanggang",
                new Role[]{new Role(1, "admin")});
        final String userJson = JsonUtils.toJson(user);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        //使用ConsumerPactBuilder构建一个契约
        final RequestResponsePact requestResponsePact = ConsumerPactBuilder
                .consumer("order-service")
                .hasPactWith("user-service")
                .given("Dsl test GET /user/{id}")
                .uponReceiving("Get user by id for dsl test")
                .method("GET")
                .path("/users/1")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(userJson)
                .toPact();

        //测试契约
        MockProviderConfig config = MockProviderConfig.createDefault();
        PactVerificationResult result = runConsumerTest(requestResponsePact, config, (mockServer, pactTestExecutionContext) -> {
            final User userResponse = restTemplate.getForObject(mockServer.getUrl() + "/users/{0}",
                    User.class, "1");
            assertThat(userResponse).isNotNull();
            assertThat(userResponse.getUsername()).isEqualTo("zhanggang");
            assertThat(userResponse.getRoles()).extracting(Role::getName).contains("admin");
        });
        assertThat(result).isEqualTo(PactVerificationResult.Ok.INSTANCE);
    }

}
