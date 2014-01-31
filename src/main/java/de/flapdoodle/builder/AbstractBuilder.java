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
package de.flapdoodle.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mmosmann
 */
public abstract class AbstractBuilder<B extends AbstractBuilder<B,V>,V> implements IBuilder<B,V> {

	Map<Attribute<?>, Object> propertyMap = new HashMap<Attribute<?>, Object>();
	Set<Attribute<?>> propertyHadDefaultValueMap = new HashSet<Attribute<?>>();

	protected <T> Property<T> property(Attribute<T> typedProperty) {
		return new Property<T>(typedProperty);
	}

	protected <T> T setDefault(Attribute<T> property, T value) {
		T old = set(property, value);
		if (!propertyHadDefaultValueMap.add(property)) {
			throw new IllegalArgumentException("" + property + " is already set with default value");
		}
		return old;
	}

	protected <T> T overwriteDefault(Attribute<T> property, T value) {
		T old = set(property, value);
		propertyHadDefaultValueMap.add(property);
		return old;
	}

	protected <T> T set(Attribute<T> property, T value) {
		T old = (T) propertyMap.put(property, value);
		boolean onlyDefaultValueWasSet = propertyHadDefaultValueMap.remove(property);

		if ((old != null) && (!onlyDefaultValueWasSet)) {
			throw new IllegalArgumentException("" + property + " already set to " + old);
		}
		return old;
	}

	protected <T> T get(Attribute<T> property) {
		T ret = (T) propertyMap.get(property);
		if (ret == null) {
			throw new IllegalArgumentException("" + property + " not set");
		}
		return ret;
	}

	protected <T> T get(Attribute<T> property, T defaultValue) {
		T ret = (T) propertyMap.get(property);
		return ret != null
						? ret
						: defaultValue;
	}

	private class Property<T> {

		private final Attribute<T> typedProperty;

		public Property(Attribute<T> typedProperty) {
			this.typedProperty = typedProperty;
		}

		public T set(T value) {
			return AbstractBuilder.this.set(typedProperty, value);
		}

		public T setDefault(T value) {
			return AbstractBuilder.this.setDefault(typedProperty, value);
		}

		public T overwriteDefault(T value) {
			return AbstractBuilder.this.overwriteDefault(typedProperty, value);
		}

		public T get() {
			return AbstractBuilder.this.get(typedProperty);
		}
	}

}
