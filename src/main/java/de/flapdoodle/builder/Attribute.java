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

/**
 *
 * @author mmosmann
 */
public final class Attribute<T> {
	private final String name;
	private final Class<T> type;

	public Attribute(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	public Class<T> type() {
		return type;
	}

	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return name+"("+type+")";
	}
	
	public static <T> Attribute<T> with(String name, Class<T> type) {
		return new Attribute<T>(name, type);
	}
	
	
	

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 41 * hash + (this.type != null ? this.type.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Attribute<?> other = (Attribute<?>) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		return this.type == other.type || (this.type != null && this.type.equals(other.type));
	}
	
	
	
	
}
