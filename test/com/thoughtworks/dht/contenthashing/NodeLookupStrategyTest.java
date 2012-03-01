package com.thoughtworks.dht.contenthashing;

import com.thoughtworks.dht.contenthashing.Node;
import com.thoughtworks.dht.contenthashing.NodeLookupStrategy;
import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class NodeLookupStrategyTest {
    @Test
    public void canProvideTheNodeBasedOnIndex() {
        NodeLookupStrategy<String, String> nodeLookupStrategy = new NodeLookupStrategy<String, String>();
        final Node<String, String> node = new Node<String, String>();
        TreeMap<Double, Node<String, String>> nodes = new TreeMap<Double, Node<String, String>>() {{
            put(0.1, node);
        }};

        assertSame(node, nodeLookupStrategy.lookup(nodes, "key"));
    }

    @Test
    public void providesTheFirstNodeIfNoneOfTheNodesMatch() {
        NodeLookupStrategy<String, String> nodeLookupStrategy = new NodeLookupStrategy<String, String>();
        final Node<String, String> firstNode = new Node<String, String>();
        TreeMap<Double, Node<String, String>> nodes = spy(new TreeMap<Double, Node<String, String>>());

        nodes.put(0.1, firstNode);
        nodes.put(0.2, new Node<String, String>());
        nodes.put(0.3, new Node<String, String>());

        when(nodes.ceilingEntry(anyDouble())).thenReturn(null);

        assertSame(firstNode, nodeLookupStrategy.lookup(nodes, "key"));
    }
}
