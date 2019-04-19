package com.ms.zg.book.moscow;

import com.github.macdao.moscow.ContractAssertion;
import com.github.macdao.moscow.ContractContainer;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public abstract class ContractTestBase {
    //获取本地服务端口号
    @LocalServerPort
    private int port;
    //new一个TestName用于获取测试方法名
    @Rule
    public final TestName testName = new TestName();
    //加载moscow契约文件
    private static final ContractContainer contractContainer =
            new ContractContainer(Paths.get("src/test/resources/moscow"));

    //使用testName. getMethodName方法自动根据测试方法名测试契约
    protected void assertContract() {
        new ContractAssertion(contractContainer.findContracts(testName.getMethodName()))
                .setPort(port)
                .assertContract();
    }
}

