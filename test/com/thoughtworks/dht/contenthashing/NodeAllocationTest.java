package com.thoughtworks.dht.contenthashing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeAllocationTest {
    @Test
    public void canProvideTheAllocationIndex() {
        NodeAllocation<String, String> nodeAllocation = new NodeAllocation<String, String>(0.2, new Node<String, String>(new HashingStrategy<String>()));
        
        assertEquals(0.2, nodeAllocation.index(), 0.0);
    }

    @Test
    public void canProvideTheAllocatedNode() {
        Node<String, String> node = new Node<String, String>(new HashingStrategy<String>());
        NodeAllocation<String, String> nodeAllocation = new NodeAllocation<String, String>(0.2, node);

        assertSame(node, nodeAllocation.node());
    }
    
    @Test
    public void canStoreData() {
        Node<String, String> node = mock(Node.class);
        NodeAllocation<String, String> allocation = new NodeAllocation<String, String>(0.2, node);
        
        when(node.get("key")).thenReturn("value");

        allocation.put("key", "value");
        assertEquals("value", allocation.get("key"));
    }
}
