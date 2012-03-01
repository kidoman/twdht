package com.thoughtworks.dht.contenthashing;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashingStrategyTest {
    @Test
    public void canProviderIndexForKey() {
        HashingStrategy hashingStrategy = new HashingStrategy();
        
        assertEquals(0.9329823758297093, hashingStrategy.index("key"), 0.0);
    }
}
