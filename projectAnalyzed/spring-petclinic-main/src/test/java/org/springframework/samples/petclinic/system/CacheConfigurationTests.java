/*
 * Copyright 2012-2025 the original author or authors.
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

package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.aot.DisabledInAotMode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link CacheConfiguration}
 */
@SpringBootTest
@DisabledInAotMode
class CacheConfigurationTests {

	@Test
	void testCacheConfigurationClassExists() {
		assertThat(CacheConfiguration.class).isNotNull();
	}

	@Test
	void testApplicationContextLoadsSuccessfully(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

	@Test
	void testCacheConfigurationIsLoadedInContext(ApplicationContext context) {
		assertThat(context.getBeansOfType(CacheConfiguration.class)).isNotEmpty();
	}

}
