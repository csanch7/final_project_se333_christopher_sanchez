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

package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.aot.DisabledInAotMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Integration tests for the {@link PetClinicApplication}
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisabledInAotMode
class PetClinicApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

	@Test
	void applicationClassExists() {
		PetClinicApplication app = new PetClinicApplication();
		assertThat(app).isNotNull();
		assertThat(app.getClass().getName()).isEqualTo("org.springframework.samples.petclinic.PetClinicApplication");
	}

	@Test
	void applicationHasMainMethod() {
		assertThat(PetClinicApplication.class.getMethods())
			.anySatisfy(method -> assertThat(method.getName()).isEqualTo("main"));
	}

}
