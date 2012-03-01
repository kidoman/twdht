package com.thoughtworks.dht.contentaddressable;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    @Test
    public void diagonalAdjacentNodesAreNotNeighbours() {
        assertFalse(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.5, 0.5), new Point(1, 1))));
    }

    @Test
    public void nodesSharingAHorizontalEdgeAreNeighbours() {
        assertTrue(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.25, 0.5), new Point(0.75, 1.0))));
    }

    @Test
    public void nodesSharingAVerticalEdgeAreNeighbours() {
        assertTrue(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.5, 0.25), new Point(1.0, 1.0))));
    }

    @Test
    public void nodesStackedVerticallyButNotAdjacentAreNotNeighbours() {
        assertFalse(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.6, 0.5), new Point(1, 1))));
    }

    @Test
    public void nodesStackedHorizontallyButNotAdjacentAreNotNeighbours() {
        assertFalse(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.5, .75), new Point(1, 1))));
    }
    
    @Test
    public void nonStackedNodesAreNotNeighbours() {
        assertFalse(new Node(new Point(0.0, 0.0), new Point(0.25, 0.25)).isAdjacentTo(new Node(new Point(0.5, 0.5), new Point(1, 1))));
    }
    

}
