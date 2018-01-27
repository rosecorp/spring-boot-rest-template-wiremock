package com.rosecorp.wiremock;

import org.junit.Test;

public class WiremockApplicationTests {

	@Test
	public void contextLoads() {
		String name = "Java Lambda";
		Processor stringProcessor = (String str, Integer value) -> str.length() + value;
		int length = stringProcessor.getStringLength(name, 1000);

		System.out.println(length);
	}

	@FunctionalInterface
	interface Processor {
		int getStringLength(String str, Integer value);
	}
}
