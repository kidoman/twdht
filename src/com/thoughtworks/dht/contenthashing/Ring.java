package com.thoughtworks.dht.contenthashing;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/* Understands network topology of nodes */
public class Ring<TKey, TValue> {
    private final NodeAllocationMap<TKey, TValue> nodeAllocationMap;
    private final HashingStrategy<TKey> hashingStrategy;

    public Ring(HashingStrategy<TKey> hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
        nodeAllocationMap = new NodeAllocationMap<TKey, TValue>();
    }

    private void validateState(TKey key) {
        checkState(!nodeAllocationMap.isEmpty(), "No nodes found.");
        checkNotNull(key, "Null key is not allowed.");
    }

    private NodeAllocation<TKey, TValue> lookupNode(TKey key) {
        return nodeAllocationMap.allocationFor(hashingStrategy.index(key));
    }

    public void put(TKey key, TValue value) {
        validateState(key);

        lookupNode(key).put(key, value);
    }

    public TValue get(TKey key) {
        validateState(key);

        return lookupNode(key).get(key);
    }

    public void addNode(double newNodeIndex, Node<TKey, TValue> newNode) {
        if (!nodeAllocationMap.isEmpty())
            newNode.inheritDataFrom(newNodeIndex, nodeAllocationMap.allocationFor(newNodeIndex));

        nodeAllocationMap.add(new NodeAllocation<TKey, TValue>(newNodeIndex, newNode));
    }
}
