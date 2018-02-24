package com.rosecorp.wiremock.config;

import com.rosecorp.wiremock.service.RemoteCallService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, AppConfigTest.SpringConfig.class})
public class AppConfigTest {

    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    private RemoteCallService remoteCallService;

    @Test
    public void test() throws Exception {

        String message = retryTemplate.execute(context -> this.remoteCallService.call());

        Mockito.verify(remoteCallService, times(3)).call();
        Assert.assertEquals(message, "Completed");
    }

    @Configuration
    public static class SpringConfig {

        @Bean
        public RemoteCallService remoteCallService() throws Exception {
            RemoteCallService remoteService = mock(RemoteCallService.class);
            when(remoteService.call())
                    .thenThrow(new RuntimeException("Remote Exception 1"))
                    .thenThrow(new RuntimeException("Remote Exception 2"))
                    .thenReturn("Completed");
            return remoteService;
        }
    }

}