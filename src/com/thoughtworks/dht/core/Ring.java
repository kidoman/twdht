package com.thoughtworks.dht.core;

import java.util.SortedSet;
import java.util.TreeSet;

/* Understands network topology of nodes */
public class Ring<TKey, TValue> {
    private final TreeSet<Node<TKey, TValue>> nodes;

    public Ring() {
        this.nodes = new TreeSet<Node<TKey, TValue>>();
    }

    private void validateState(TKey key) {
        if (nodes.isEmpty())
            throw new IllegalStateException("No nodes found.");

        if (key == null)
            throw new IllegalArgumentException("Null key is not allowed.");
    }

    public void put(TKey key, TValue value) {
        validateState(key);
        
        for(Node<TKey, TValue> node : nodes){
            if(node.canStore(key)){
                node.put(key, value);
                return;
            }
        }

        nodes.first().put(key,value);
    }

    public TValue get(TKey key) {
        validateState(key);

        for(Node<TKey, TValue> node : nodes){
            if(node.canStore(key)){
                return node.get(key);
            }
        }
        
        return nodes.first().get(key);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public int totalNodes() {
        return nodes.size();
    }
}
