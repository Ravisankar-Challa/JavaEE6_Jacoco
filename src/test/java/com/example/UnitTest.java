package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.example.control.UnitTestExample;

public class UnitTest {
	
	private UnitTestExample unitTestExample;
	
	@Before
	public void setup() {
		unitTestExample = new UnitTestExample();
	}
	@Test
	public void testAdd() {
		assertEquals(6, unitTestExample.add(4, 2));
	}
}
