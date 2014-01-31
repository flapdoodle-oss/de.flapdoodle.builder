/**
 * Copyright (C) 2013
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.builder.examples;

import de.flapdoodle.builder.AbstractBuilder;
import de.flapdoodle.builder.Attribute;
import de.flapdoodle.builder.IBuilder;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author mmosmann
 */
public class TestExampleReadMeCode {
		// ### Usage
	// ->
	// some simple builder patterns...
	// <-

	// #### Simple Builder
	@Test
	public void simpleBuilder() {
		// ->
		class MinMax {

			private final int min;
			private final int max;

			public MinMax(int min, int max) {
				this.min = min;
				this.max = max;
			}

			public int min() {
				return min;
			}

			public int max() {
				return max;
			}

		}

		class MinMaxBuilder extends AbstractBuilder<MinMaxBuilder, MinMax> {

			private final Attribute<Integer> MIN = Attribute.with("min", Integer.class);
			private final Attribute<Integer> MAX = Attribute.with("max", Integer.class);

			public MinMaxBuilder() {
				setDefault(MIN, 4);
				setDefault(MAX, 8);
			}

			public MinMaxBuilder min(int min) {
				super.set(MIN, min);
				return this;
			}

			public MinMaxBuilder max(int min) {
				super.set(MAX, min);
				return this;
			}

			@Override
			public MinMax build() {
				return new MinMax(get(MIN), get(MAX));
			}
		}

		MinMax minMax = new MinMaxBuilder()
						.min(1)
						.max(2)
						.build();

		assertEquals(1, minMax.min());
		assertEquals(2, minMax.max());
		
		try {
			new MinMaxBuilder().min(2).min(7);
			fail("should not be reached");
		} catch (IllegalArgumentException iax) {
			assertEquals("min(class java.lang.Integer) already set to 2", iax.getLocalizedMessage());
		}
		// <-
	}
}
