package com.ms.zg.book.pactprovider;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

//使用Pact提供的PactRunner启动类
@RunWith(PactRunner.class)
//定义服务提供者的名称
@Provider("user-service")
//契约文件路径
@PactFolder("PactContracts")
public class UserServicePactContractTest {

    @TestTarget
    public final Target target = new HttpTarget(8081);

    @BeforeClass
    public static void start() {
        SpringApplication.run(PactProviderApplication.class);
    }

    //@State的值与JSON文件中providerStates的保持一致
    @State("Dsl test GET /user/{id}")
    public void test_get_user_by_id() {
    }
}

