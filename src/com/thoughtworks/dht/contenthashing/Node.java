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

    public void inheritDataFrom(double newNodeIndex, NodeAllocation<TKey, TValue> oldNodeAllocation) {
        for (Map.Entry<TKey, TValue> value : oldNodeAllocation.node().values.entrySet()) {
            double valueIndex = hashingStrategy.index(value.getKey());

            boolean valueBelongsInUs = valueIndex <= newNodeIndex;
            boolean firstNodeValue = valueIndex > oldNodeAllocation.index();
            boolean replacingFirstNode = newNodeIndex < oldNodeAllocation.index();

            if (valueBelongsInUs || (firstNodeValue && replacingFirstNode))
                moveFrom(oldNodeAllocation.node(), value);
        }
    }

    private void moveFrom(Node<TKey, TValue> oldNode, Map.Entry<TKey, TValue> value) {
        put(value.getKey(), value.getValue());
        oldNode.values.remove(value.getKey());
    }
}
