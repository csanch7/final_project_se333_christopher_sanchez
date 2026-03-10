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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test class for the {@link PetClinicRuntimeHints}
 */
class PetClinicRuntimeHintsTests {

	private PetClinicRuntimeHints runtimeHints;

	private RuntimeHints hints;

	private ClassLoader classLoader;

	@BeforeEach
	void setup() {
		this.runtimeHints = new PetClinicRuntimeHints();
		this.hints = new RuntimeHints();
		this.classLoader = Thread.currentThread().getContextClassLoader();
	}

	@Test
	void testRegisterHintsDoesNotThrowException() {
		assertThatCode(() -> runtimeHints.registerHints(hints, classLoader)).doesNotThrowAnyException();
	}

	@Test
	void testRegisterHintsWithRealRuntimeHints() {
		RuntimeHints hints = new RuntimeHints();
		runtimeHints.registerHints(hints, classLoader);
		// Test passes if no exception is thrown
	}

	@Test
	void testRegisterHintsWithDifferentClassLoader() {
		runtimeHints.registerHints(hints, PetClinicRuntimeHints.class.getClassLoader());
		// Test passes if no exception is thrown
	}

}
