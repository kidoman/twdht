package com.thoughtworks.dht.contenthashing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeTest {
    @Test
    public void canStoreData() {
        Node<String, String> node = new Node<String, String>(new HashingStrategy<String>());
        node.put("key", "value");

        assertEquals("value", node.get("key"));
    }

    @Test
    public void copiesProperDataToNewNode() {
        HashingStrategy<String> hashingStrategy = mock(HashingStrategy.class);
        Node<String, String> oldNode = new Node<String, String>(hashingStrategy);

        when(hashingStrategy.index("key")).thenReturn(0.29);

        oldNode.put("key", "value");

        Node<String, String> newNode = new Node<String, String>(new HashingStrategy<String>());

        oldNode.copyDataTo(0.3, newNode);

        assertEquals("value", newNode.get("key"));
        assertNull(oldNode.get("key"));
    }
    
    @Test
    public void copiesOutOfIndexDataToNewNode() {
        HashingStrategy<String> hashingStrategy = mock(HashingStrategy.class);
        Node<String, String> oldNode = new Node<String, String>(hashingStrategy);
        
        when(hashingStrategy.index("key")).thenReturn(0.4);

        oldNode.put("key", "value");
        
        Node<String, String> newNode = new Node<String, String>(hashingStrategy);
        
        oldNode.copyOutOfIndexDataTo(0.2, newNode);
        
        assertEquals("value", newNode.get("key"));
        assertNull(oldNode.get("key"));
    }
}
