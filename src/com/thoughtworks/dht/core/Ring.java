package com.thoughtworks.dht.core;

import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/* Understands network topology of nodes */
public class Ring<TKey, TValue> {
    private final TreeSet<Node<TKey, TValue>> nodes;

    public Ring() {
        this.nodes = new TreeSet<Node<TKey, TValue>>();
    }

    private void validateState(TKey key) {
        checkState(!nodes.isEmpty(), "No nodes found.");
        checkNotNull(key, "Null key is not allowed.");
    }

    private interface NodeIdentifiedAction<TKey, TValue> {
        TValue apply(Node<TKey, TValue> node, TKey key);
    }
    
    private TValue traverseNodes(TKey key, NodeIdentifiedAction<TKey, TValue> func) {
        for(Node<TKey, TValue> node : nodes)
            if(node.canStore(key))
                return func.apply(node, key);
        
        return func.apply(nodes.first(), key);
    }

    public void put(TKey key, final TValue value) {
        validateState(key);

        traverseNodes(key, new NodeIdentifiedAction<TKey, TValue>() {
            @Override
            public TValue apply(Node<TKey, TValue> node, TKey key) {
                node.put(key, value);

                return null;
            }
        });
    }

    public TValue get(TKey key) {
        validateState(key);

        return traverseNodes(key, new NodeIdentifiedAction<TKey, TValue>() {
            @Override
            public TValue apply(Node<TKey, TValue> node, TKey key) {
                return node.get(key);
            }
        });
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public int totalNodes() {
        return nodes.size();
    }
}
