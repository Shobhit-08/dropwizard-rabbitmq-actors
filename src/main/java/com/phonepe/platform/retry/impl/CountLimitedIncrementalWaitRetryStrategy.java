/*
 * Copyright (c) 2019 Santanu Sinha <santanu.sinha@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.phonepe.platform.retry.impl;

import com.github.rholder.retry.BlockStrategies;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.phonepe.platform.retry.RetryStrategy;
import com.phonepe.platform.retry.config.CountLimitedIncrementalWaitRetryConfig;
import com.phonepe.platform.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

/**
 * Limits retry time
 */
public class CountLimitedIncrementalWaitRetryStrategy extends RetryStrategy {
    public CountLimitedIncrementalWaitRetryStrategy(CountLimitedIncrementalWaitRetryConfig config) {
        super(RetryerBuilder.<Boolean>newBuilder()
                .retryIfException(exception -> CommonUtils.isRetriable(config.getRetriableExceptions(),
                        exception))
                .withStopStrategy(StopStrategies.stopAfterAttempt(config.getMaxAttempts()))
                .withBlockStrategy(BlockStrategies.threadSleepStrategy())
                .withWaitStrategy(
                        WaitStrategies.incrementingWait(config.getInitialWaitTime().toMilliseconds(),
                                TimeUnit.MILLISECONDS,
                                config.getWaitIncrement().toMilliseconds(),
                                TimeUnit.MILLISECONDS))
                .build());
    }
}