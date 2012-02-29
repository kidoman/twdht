package com.thoughtworks.dht;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodeTest {
    @Test
    public void canStoreData() {
        Node<String, String> node = new Node<String, String>();
        node.put("key", "value");

        assertEquals("value", node.get("key"));
    }
}
