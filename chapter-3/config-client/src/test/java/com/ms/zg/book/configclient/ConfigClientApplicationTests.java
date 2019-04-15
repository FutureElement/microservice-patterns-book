package com.ms.zg.book.configclient;

import com.ms.zg.book.configclient.common.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigClientApplicationTests {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void contextLoads() {
        final String appName = applicationConfig.getAppName();

        assertThat(appName).isEqualTo("test-configuration");
    }

}
