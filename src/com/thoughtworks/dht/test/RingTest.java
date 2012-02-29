package com.thoughtworks.dht.test;

import com.thoughtworks.dht.core.Node;
import com.thoughtworks.dht.core.Ring;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class RingTest {
    @Test(expected = IllegalStateException.class)
    public void addingDataWithoutNodesFails() {
        new Ring<String, String>().put("key", "value");
    }

    @Test
    public void addingDataWithNodesSucceeds() {
        Ring ring = withEquallySpacedNodes(1);

        ring.put("key", "value");
    }

    @Test(expected = NullPointerException.class)
    public void addingNullKeyIsNotAllowed() {
        withEquallySpacedNodes(1).put(null, "value");
    }

    @Test(expected = NullPointerException.class)
    public void retrievingNullKeyIsNotAllowed() {
        withEquallySpacedNodes(1).get(null);
    }

    @Test
    public void canStoreValue() {
        Ring<String, String> ring = withEquallySpacedNodes(20);

        ring.put("key1", "value1");
        ring.put("key2", "value2");
        ring.put("key3", "value3");

        assertEquals("value1", ring.get("key1"));
        assertEquals("value3", ring.get("key3"));
        assertEquals("value2", ring.get("key2"));
    }

    @Test
    public void storesDataInTheAppropriateNode() {
        Node n1 = mock(Node.class), n2 = mock(Node.class), n3 = mock(Node.class);

        when(n1.canStore("key")).thenReturn(false);
        when(n2.canStore("key")).thenReturn(true);
        when(n3.canStore("key")).thenReturn(false);

        Ring ring = new Ring();
        ring.addNode(n1);
        ring.addNode(n2);
        ring.addNode(n3);

        ring.put("key", "value");

        verify(n2).put("key", "value");
    }

    @Test
    public void storesTheDataInTheFirstNodeWhenNoneOfTheNodesWantToStoreData() {
        Node n1 = mock(Node.class), n2 = mock(Node.class), n3 = mock(Node.class);

        when(n1.canStore("key")).thenReturn(false);
        when(n2.canStore("key")).thenReturn(false);
        when(n3.canStore("key")).thenReturn(false);

        Ring ring = new Ring();
        ring.addNode(n1);
        ring.addNode(n2);
        ring.addNode(n3);

        ring.put("key", "value");

        verify(n1).put("key", "value");
    }

    @Test
    public void returnsNullForMissingKey() {
        Ring<String, String> ring = withEquallySpacedNodes(2);

        assertNull(ring.get("key"));
    }

    @Test
    public void canProvideTotalNodeCount() {
        assertEquals(0, new Ring().totalNodes());
    }

    @Test
    public void canAddNode() {
        Ring ring = withEquallySpacedNodes(1);

        assertEquals(1, ring.totalNodes());
    }

    public Ring<String, String> withEquallySpacedNodes(int nodes) {
        Ring ring = new Ring<String, String>();
        double spacing = 1.0 / nodes;
        for (int i = 0; i < nodes; i++)
            ring.addNode(new Node(i * spacing));

        return ring;
    }
}
