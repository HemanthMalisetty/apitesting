package com.edr.businesscomponents;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.edr.testnglisteners.TestListener;

//@Listeners(TestListener.class)
public class EdrFlow2 {
	@Test (groups={"API_FIVE"})
	public void API_FIVE() {
		Assert.assertTrue(true);
	}
	@Test (groups={"API_SIX"})
	public void API_SIX() {
		Assert.assertTrue(false);
	}
	@Test (groups={"API_SEVEN"})
	public void API_SEVEN() {
		Assert.assertTrue(true);
	}
}
