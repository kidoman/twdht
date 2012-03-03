package com.thoughtworks.dht.contenthashing;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeAllocationMapTest {
    @Test
    public void canTellIfItHasNoNodes() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();

        assertTrue(nodeAllocationMap.isEmpty());
    }
    
    @Test
    public void canProvideFirstNode() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();
        
        Node<String, String> firstNode = n(hashingStrategy);

        nodeAllocationMap.add(na(0.1, firstNode));
        nodeAllocationMap.add(na(0.2, n(hashingStrategy)));
        nodeAllocationMap.add(na(0.3, n(hashingStrategy)));

        assertNodeMatch(0.1, firstNode, nodeAllocationMap.first());
    }
    
    @Test
    public void updatesFirstNodeIfNewNodeBeingAddedIsBeforeTheOldFirstNode() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();
        
        nodeAllocationMap.add(na(0.1, n(hashingStrategy)));
        nodeAllocationMap.add(na(0.2, n(hashingStrategy)));
        
        Node<String, String> newFirstNode = n(hashingStrategy);
        
        nodeAllocationMap.add(na(0.05, newFirstNode));

        assertNodeMatch(0.05, newFirstNode, nodeAllocationMap.first());
    }
    
    @Test
    public void canProviderFirstNodeIndex() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();

        nodeAllocationMap.add(na(0.1, n(hashingStrategy)));
        nodeAllocationMap.add(na(0.2, n(hashingStrategy)));

        assertEquals(0.1, nodeAllocationMap.firstIndex(), 0.0);
    }
    
    @Test(expected = IllegalStateException.class)
    public void throwsIfAskedForFirstNodeIndexWithoutAFirstNode() {
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();
        
        nodeAllocationMap.firstIndex();
    }
    
    @Test
    public void canProvideNextHigherNode() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();

        Node<String, String> matchingNode = n(hashingStrategy);
        Node<String, String> higherNode = n(hashingStrategy);
        Node<String, String> nextHigherNode = n(hashingStrategy);
        
        nodeAllocationMap.add(na(0.1, n(hashingStrategy)));
        nodeAllocationMap.add(na(0.2, matchingNode));
        nodeAllocationMap.add(na(0.3, higherNode));
        nodeAllocationMap.add(na(0.5, nextHigherNode));

        assertNodeMatch(0.2, matchingNode, nodeAllocationMap.allocationFor(0.2));
        assertNodeMatch(0.3, higherNode, nodeAllocationMap.allocationFor(0.25));
        assertNodeMatch(0.5, nextHigherNode, nodeAllocationMap.allocationFor(0.4));
    }

    @Test
    public void firstNodeBecomesTheNextNodeWhenNoNextNodeExists() {
        HashingStrategy<String> hashingStrategy = new HashingStrategy<String>();
        NodeAllocationMap<String, String> nodeAllocationMap = new NodeAllocationMap<String, String>();

        Node<String, String> firstNode = n(hashingStrategy);
        
        nodeAllocationMap.add(na(0.1, firstNode));
        nodeAllocationMap.add(na(0.2, n(hashingStrategy)));
        nodeAllocationMap.add(na(0.5, n(hashingStrategy)));

        assertNodeMatch(0.1, firstNode, nodeAllocationMap.allocationFor(0.6));
    }
    
    private void assertNodeMatch(double index, Node<String, String> node, NodeAllocation allocation) {
        Assert.assertEquals(index, allocation.index(), 0.0);
        assertSame(node, allocation.node());
    }

    private Node<String, String> n(HashingStrategy<String> hashingStrategy) {
        return new Node<String, String>(hashingStrategy);
    }

    private NodeAllocation<String, String> na(double index, Node<String, String> node) {
        return new NodeAllocation<String, String>(index,  node);
    }
}
