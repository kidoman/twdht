package com.thoughtworks.dht.contentaddressable;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    @Test
    public void diagonalAdjacentNodesAreNotNeighbours() {
        assertFalse(new Node(new Point(0.0, 0.0), new Point(0.5, 0.5)).isAdjacentTo(new Node(new Point(0.5, 0.5), new Point(1, 1))));
        assertFalse(new Node(new Point(0, 0.25), new Point(0.25, 0.5)).isAdjacentTo(new Node(new Point(0.25, 0), new Point(0.5, 0.25))));
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
    
    @Test
    public void canSplitItselfHorizontallyAndStillBeNeighbours() {
        Node parentNode = from(0.0, 0.0, 1.0, 1.0);
        
        List<Node> split = parentNode.splitHorizontal();
        
        assertEquals(2, split.size());
        assertTrue(split.get(0).isAdjacentTo(split.get(1)));
        assertTrue(split.get(1).isAdjacentTo(split.get(0)));
    }

    @Test
    public void canSplitItselfVerticallyAndStillBeNeighbours() {
        Node parentNode = from(0.0, 0.0, 1.0, 1.0);

        List<Node> split = parentNode.splitVertical();

        assertEquals(2, split.size());
        assertTrue(split.get(0).isAdjacentTo(split.get(1)));
        assertTrue(split.get(1).isAdjacentTo(split.get(0)));
    }
    
    @Test
    public void canSplitItselfMultipleTimesAndHaveProperNeighbours() {
        Node parentNode = from(0.0, 0.0, 1.0, 1.0);
        
        List<Node> split1 = parentNode.splitHorizontal();
        List<Node> split2 = split1.get(0).splitVertical();
        List<Node> split3 = split1.get(1).splitHorizontal();

        /* Testing structure:
         *
         *   +++++++++++++++++
         *   +       +       +
         *   + s2.0  + s2.0  +
         *   +       +       +
         *   +++++++++++++++++
         *   +      s3.0     +
         *   +++++++++++++++++
         *   +      s3.1     +
         *   +++++++++++++++++
         *
         */

        assertNeighboursWith(split2.get(0), split2.get(1), split3.get(0));
        assertNeighboursWith(split2.get(1), split2.get(0), split3.get(0));
        assertNeighboursWith(split3.get(0), split2.get(0), split2.get(1), split3.get(1));
        assertNeighboursWith(split3.get(1), split3.get(0));
    }
    
    private void assertNeighboursWith(Node parentNode, Node... nodes) {
        for(Node node : nodes)
            assertTrue(parentNode.isAdjacentTo(node));
    }

    private Node from(double blX, double blY, double trX, double trY) {
        return new Node(new Point(blX, blY), new Point(trX,  trY));
    }
}
