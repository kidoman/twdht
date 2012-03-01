package com.thoughtworks.dht.contenthashing;

import java.util.Map;
import java.util.TreeMap;

/* Understands the rules governing the mapping of the key space to the nodes */
public class NodeLookupStrategy<TKey, TValue> {
    private final HashingStrategy<TKey> hashingStrategy;

    public NodeLookupStrategy(HashingStrategy<TKey> hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public Node<TKey, TValue> lookup(TreeMap<Double, Node<TKey, TValue>> nodes, TKey key) {
        Map.Entry<Double, Node<TKey, TValue>> entry = nodes.ceilingEntry(hashingStrategy.index(key));
        if (entry != null)
            return entry.getValue();

        return nodes.firstEntry().getValue();
    }
}
