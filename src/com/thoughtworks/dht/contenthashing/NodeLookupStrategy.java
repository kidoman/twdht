package com.thoughtworks.dht.contenthashing;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.Map;
import java.util.TreeMap;

/* Understands the rules governing the mapping of the key space to the nodes */
public class NodeLookupStrategy<TKey, TValue> {
    private double getIndex(TKey key) {
        HashCode hc = Hashing.sha512()
                .newHasher()
                .putString(key.toString())
                .putLong(key.hashCode())
                .hash();

        return (hc.asLong() & 0xffff) / (double) 0xffff;
    }

    public Node<TKey, TValue> lookup(TreeMap<Double, Node<TKey, TValue>> nodes, TKey key) {
        Map.Entry<Double, Node<TKey, TValue>> entry = nodes.ceilingEntry(getIndex(key));
        if (entry != null)
            return entry.getValue();
        return nodes.firstEntry().getValue();
    }
}
