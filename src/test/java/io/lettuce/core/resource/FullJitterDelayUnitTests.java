/*
 * Copyright 2011-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * @author Jongyeol Choi
 * @author Mark Paluch
 */
class FullJitterDelayUnitTests {

    @Test
    void shouldNotCreateIfLowerBoundIsNegative() {
        assertThatThrownBy(() -> Delay.fullJitter(-1, 100, 1, TimeUnit.MILLISECONDS))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateIfLowerBoundIsSameAsUpperBound() {
        assertThatThrownBy(() -> Delay.fullJitter(100, 100, 1, TimeUnit.MILLISECONDS))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negativeAttemptShouldReturnZero() {

        Delay delay = Delay.fullJitter();

        assertThat(delay.createDelay(-1)).isEqualTo(Duration.ZERO);
    }

    @Test
    void zeroShouldReturnZero() {

        Delay delay = Delay.fullJitter();

        assertThat(delay.createDelay(0)).isEqualTo(Duration.ZERO);
    }

    @Test
    void testDefaultDelays() {

        Delay delay = Delay.fullJitter();

        assertThat(delay.createDelay(1).toMillis()).isBetween(0L, 1L);
        assertThat(delay.createDelay(2).toMillis()).isBetween(1L, 2L);
        assertThat(delay.createDelay(3).toMillis()).isBetween(2L, 4L);
        assertThat(delay.createDelay(4).toMillis()).isBetween(4L, 8L);
        assertThat(delay.createDelay(5).toMillis()).isBetween(8L, 16L);
        assertThat(delay.createDelay(6).toMillis()).isBetween(16L, 32L);
        assertThat(delay.createDelay(7).toMillis()).isBetween(32L, 64L);
        assertThat(delay.createDelay(8).toMillis()).isBetween(64L, 128L);
        assertThat(delay.createDelay(9).toMillis()).isBetween(128L, 256L);
        assertThat(delay.createDelay(10).toMillis()).isBetween(256L, 512L);
        assertThat(delay.createDelay(11).toMillis()).isBetween(512L, 1024L);
        assertThat(delay.createDelay(12).toMillis()).isBetween(1024L, 2048L);
        assertThat(delay.createDelay(13).toMillis()).isBetween(2048L, 4096L);
        assertThat(delay.createDelay(14).toMillis()).isBetween(4096L, 8192L);
        assertThat(delay.createDelay(15).toMillis()).isBetween(8192L, 16384L);
        assertThat(delay.createDelay(16).toMillis()).isBetween(15000L, 30000L);
        assertThat(delay.createDelay(17).toMillis()).isBetween(15000L, 30000L);
        assertThat(delay.createDelay(Integer.MAX_VALUE).toMillis()).isBetween(15000L, 30000L);
    }

}
