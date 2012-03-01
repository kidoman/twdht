package com.thoughtworks.dht.contenthashing;

import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeLookupStrategyTest {
    @Test
    public void canProvideTheNodeBasedOnIndex() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeLookupStrategy<String, String> nodeLookupStrategy = new NodeLookupStrategy<String, String>(hashingStrategy);

        final Node<String, String> node = new Node<String, String>(new HashingStrategy<String>());
        TreeMap<Double, Node<String, String>> nodes = new TreeMap<Double, Node<String, String>>() {{
            put(0.1, node);
        }};

        assertSame(node, nodeLookupStrategy.lookup(nodes, "key"));
    }

    @Test
    public void providesTheFirstNodeIfNoneOfTheNodesMatch() {
        HashingStrategy<String> hashingStrategy = mock(HashingStrategy.class);
        NodeLookupStrategy<String, String> nodeLookupStrategy = new NodeLookupStrategy<String, String>(hashingStrategy);
        Node<String, String> firstNode = new Node<String, String>(new HashingStrategy<String>());
        TreeMap<Double, Node<String, String>> nodes = new TreeMap<Double, Node<String, String>>();

        nodes.put(0.1, firstNode);
        nodes.put(0.2, new Node<String, String>(new HashingStrategy<String>()));
        nodes.put(0.3, new Node<String, String>(new HashingStrategy<String>()));

        when(hashingStrategy.index("key")).thenReturn(0.4);

        assertSame(firstNode, nodeLookupStrategy.lookup(nodes, "key"));
    }
}
