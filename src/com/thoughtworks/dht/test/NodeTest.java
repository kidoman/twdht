package com.thoughtworks.dht.test;

import com.thoughtworks.dht.core.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {
    @Test(expected = IllegalArgumentException.class)
    public void canOnlyCreateNodeWithPositiveNonZeroIndex() {
        new Node(-0.1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void canOnlyCreateNodeWithIndexLessThanOne() {
        new Node(1.1);
    }
    
    @Test
    public void canStoreData() {
        Node<String, String> node = new Node(0.1);
        node.put("key", "value");
        
        assertEquals("value", node.get("key"));
    }
    
    @Test
    public void nodeOfHigherIndexValueIsLarger() {
        assertEquals(1, new Node(0.2).compareTo(new Node(0.1)));
    }
    
    @Test
    public void nodeOfLowerIndexIsFirst(){
        assertEquals(-1, new Node(0.3).compareTo(new Node(0.4)));
    }
    
    @Test
    public void nodesOfEqualIndexAreEqual() {
        assertEquals(0, new Node(0.2).compareTo(new Node(0.2)));
    }

    @Test
    public void willStoreValueForValidIndex() {
        Node<String, String> node = new Node<String, String>(0.5);

        assertTrue(node.canStore("aas"));
    }
    
    @Test
    public void willNotStoreValueForInvalidIndex() {
        Node<String, String> node = new Node<String, String>(0.4);

        assertFalse(node.canStore("aas"));
    }
}
