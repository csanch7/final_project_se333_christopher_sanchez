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

package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for the {@link Owner}
 */
class OwnerTests {

	private Owner owner;

	private Pet pet1;

	private Pet pet2;

	@BeforeEach
	void setup() {
		owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Portland");
		owner.setTelephone("5551234567");

		pet1 = new Pet();
		pet1.setName("Fluffy");

		pet2 = new Pet();
		pet2.setName("Spot");
		pet2.setId(1);
	}

	@Test
	void testOwnerProperties() {
		assertThat(owner.getFirstName()).isEqualTo("John");
		assertThat(owner.getLastName()).isEqualTo("Doe");
		assertThat(owner.getAddress()).isEqualTo("123 Main St");
		assertThat(owner.getCity()).isEqualTo("Portland");
		assertThat(owner.getTelephone()).isEqualTo("5551234567");
	}

	@Test
	void testAddPetWhenPetIsNew() {
		owner.addPet(pet1);

		assertThat(owner.getPets()).contains(pet1);
	}

	@Test
	void testGetPetByNameIgnoreNewFalse() {
		owner.addPet(pet1);

		assertThat(owner.getPet("Fluffy", false)).isEqualTo(pet1);
	}

	@Test
	void testGetPetByNameIgnoreNewTrue() {
		owner.addPet(pet1);
		// pet1 is new, so when ignoreNew=true and it's new, it should still be returned
		Pet result = owner.getPet("Fluffy", true);
		// The method returns null for new pets when ignoreNew=true
		// This is the expected behavior - ignoreNew=true means skip new pets
		assertThat(result).isNull();
	}

	@Test
	void testGetPetByNameIgnoreNewFalseWithNewPet() {
		owner.addPet(pet1);
		// When ignoreNew=false, even new pets should be returned
		Pet result = owner.getPet("Fluffy", false);
		assertThat(result).isEqualTo(pet1);
	}

	@Test
	void testGetPetByNameCaseInsensitive() {
		owner.addPet(pet1);

		assertThat(owner.getPet("fluffy")).isEqualTo(pet1);
		assertThat(owner.getPet("FLUFFY")).isEqualTo(pet1);
	}

	@Test
	void testGetPetByNameNotFound() {
		owner.addPet(pet1);

		assertThat(owner.getPet("Fido")).isNull();
	}

	@Test
	void testGetPetByIdWithExistingId() {
		owner.addPet(pet1);
		// pet2 has ID 1 but is not added to owner via addPet since it's not new
		// Let me manually add it to the pets list to test getPet by ID
		owner.getPets().add(pet2);

		assertThat(owner.getPet(1)).isEqualTo(pet2);
	}

	@Test
	void testGetPetByIdNotFound() {
		owner.addPet(pet1);

		assertThat(owner.getPet(999)).isNull();
	}

	@Test
	void testAddVisit() {
		// Manually add pet2 to owner's pets list as if it was loaded from DB
		owner.getPets().add(pet2);
		Visit visit = new Visit();

		owner.addVisit(1, visit);

		assertThat(pet2.getVisits()).contains(visit);
	}

	@Test
	void testAddVisitWithNullPetId() {
		assertThatThrownBy(() -> owner.addVisit(null, new Visit())).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Pet identifier must not be null");
	}

	@Test
	void testAddVisitWithNullVisit() {
		owner.addPet(pet2);

		assertThatThrownBy(() -> owner.addVisit(1, null)).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Visit must not be null");
	}

	@Test
	void testAddVisitWithInvalidPetId() {
		assertThatThrownBy(() -> owner.addVisit(999, new Visit())).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid Pet identifier");
	}

	@Test
	void testToString() {
		String result = owner.toString();

		assertThat(result).contains("John")
			.contains("Doe")
			.contains("123 Main St")
			.contains("Portland")
			.contains("5551234567");
	}

}
