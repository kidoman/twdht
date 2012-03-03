package com.thoughtworks.dht.contenthashing;

import java.util.Map;

/* Understands the allocation of nodes to the ring */
public class NodeAllocation<TKey, TValue> {
    private final double index;
    private final Node<TKey, TValue> node;

    public NodeAllocation(double index, Node<TKey, TValue> node) {
        this.index = index;
        this.node = node;
    }

    public NodeAllocation(Map.Entry<Double, Node<TKey, TValue>> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public double index() {
        return index;
    }

    public Node<TKey, TValue> node() {
        return node;
    }

    public void put(TKey key, TValue value) {
        node.put(key, value);
    }

    public TValue get(TKey key) {
        return node.get(key);
    }
}
