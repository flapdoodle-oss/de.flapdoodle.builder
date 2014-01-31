# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.

## de.flapdoodle.builder

java builder support library

### Maven

Stable (Maven Central Repository, Released: 99.99.2099 - wait 24hrs for [maven central](http://repo1.maven.org/maven2/de/flapdoodle/guava/de.flapdoodle.guava/maven-metadata.xml))

	<dependency>
		<groupId>de.flapdoodle.builder</groupId>
		<artifactId>de.flapdoodle.builder</artifactId>
		<version>1.0</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle.builder</groupId>
		<artifactId>de.flapdoodle.builder</artifactId>
		<version>1.1-SNAPSHOT</version>
	</dependency>

### Usage

some simple builder patterns...

#### Simple Builder

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
