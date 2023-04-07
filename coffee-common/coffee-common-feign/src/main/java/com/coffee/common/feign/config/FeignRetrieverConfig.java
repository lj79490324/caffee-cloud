package com.coffee.common.feign.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Feign重试配置
 * @author rabit
 */
@Slf4j
@Configuration
public class FeignRetrieverConfig extends Retryer.Default{


    public FeignRetrieverConfig() {
            this(100, SECONDS.toMillis(1), 5);
        }


    public FeignRetrieverConfig(long period, long maxPeriod, int maxAttempts) {
        super(period, maxPeriod, maxAttempts);
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.warn("【Feign重试：】【{}】", e.getMessage());
        super.continueOrPropagate(e);
    }

    @Override
    public Retryer clone() {
        return new FeignRetrieverConfig();
    }
}
