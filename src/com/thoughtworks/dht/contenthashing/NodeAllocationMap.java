package com.thoughtworks.dht.contenthashing;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkState;

/* Understands the layout of nodes in a ring */
public class NodeAllocationMap<TKey, TValue> {
    private final TreeMap<Double, Node<TKey, TValue>> nodes;

    public NodeAllocationMap() {
        nodes = new TreeMap<Double, Node<TKey, TValue>>();
    }

    public void add(NodeAllocation<TKey, TValue> allocation) {
        nodes.put(allocation.index(), allocation.node());
    }

    public double firstIndex() {
        checkState(!isEmpty());
        return nodes.firstKey();
    }

    public NodeAllocation<TKey, TValue> first() {
        return new NodeAllocation<TKey, TValue>(nodes.firstEntry());
    }

    public NodeAllocation<TKey, TValue> allocationFor(double index) {
        Map.Entry<Double, Node<TKey, TValue>> ceilingEntry = nodes.ceilingEntry(index);

        return ceilingEntry == null ? first() : new NodeAllocation<TKey, TValue>(ceilingEntry);
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
