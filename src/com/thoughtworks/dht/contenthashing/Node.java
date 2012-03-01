package com.thoughtworks.dht.contenthashing;

import java.util.HashMap;
import java.util.Map;

/* Understands an addressable shard in a distributed key value store */
public class Node<TKey, TValue> {
    private final HashMap<TKey, TValue> values;
    private final HashingStrategy<TKey> hashingStrategy;

    public Node(HashingStrategy<TKey> hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
        values = new HashMap<TKey, TValue>();
    }

    public void put(TKey key, TValue value) {
        values.put(key, value);
    }

    public TValue get(TKey key) {
        return values.get(key);
    }

    public void copyDataTo(double newNodeIndex, Node<TKey, TValue> newNode) {
        for (Map.Entry<TKey, TValue> value : values.entrySet()) {
            if (hashingStrategy.index(value.getKey()) <= newNodeIndex) {
                newNode.put(value.getKey(), value.getValue());
                values.remove(value.getKey());
            }
        }
    }

    public void copyOutOfIndexDataTo(double myIndex, Node<TKey, TValue> newNode) {
        for(Map.Entry<TKey, TValue> value : values.entrySet()) {
            if (hashingStrategy.index(value.getKey()) > myIndex) {
                newNode.put(value.getKey(), value.getValue());
                values.remove(value.getKey());
            }
        }
    }
}
