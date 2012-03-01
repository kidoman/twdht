package com.thoughtworks.dht.contenthashing;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/* Understands network topology of nodes */
public class Ring<TKey, TValue> {
    private final TreeMap<Double, Node<TKey, TValue>> nodes;
    private final NodeLookupStrategy<TKey, TValue> lookupStrategy;

    public Ring(NodeLookupStrategy lookupStrategy) {
        this.lookupStrategy = lookupStrategy;
        this.nodes = new TreeMap<Double, Node<TKey, TValue>>();
    }

    private void validateState(TKey key) {
        checkState(!nodes.isEmpty(), "No nodes found.");
        checkNotNull(key, "Null key is not allowed.");
    }

    private Node<TKey, TValue> lookupNode(TKey key) {
        return lookupStrategy.lookup(nodes, key);
    }

    public void put(TKey key, TValue value) {
        validateState(key);

        lookupNode(key).put(key, value);
    }

    public TValue get(TKey key) {
        validateState(key);

        return lookupNode(key).get(key);
    }

    public void addNode(double index, Node<TKey, TValue> node) {
        Map.Entry<Double, Node<TKey, TValue>> entry = nodes.ceilingEntry(index);
        nodes.put(index, node);

        Node<TKey, TValue> nextNode;
        if (entry == null)
            nextNode = nodes.firstEntry().getValue();
        else
            nextNode = entry.getValue();

        nextNode.syncWith(index, node);
    }

    public int totalNodes() {
        return nodes.size();
    }
}
