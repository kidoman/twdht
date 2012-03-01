package com.thoughtworks.dht.contentaddressable;

import com.thoughtworks.dht.contenthashing.HashingStrategy;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NodeTest {
    @Test
    public void canBeConstructedUsingAHashingStrategy() {
        assertNotNull(fromCoords(0, 0, 1, 1));
    }
    
    @Test
    public void diagonalAdjacentNodesAreNotNeighbours() {
        assertFalse(fromCoords(0.0, 0.0, 0.5, 0.5).isAdjacentTo(fromCoords(0.5, 0.5, 1, 1)));
        assertFalse(fromCoords(0.0, 0.25, 0.25, 0.5).isAdjacentTo(fromCoords(0.25, 0, 0.5, 0.25)));
    }

    @Test
    public void nodesSharingAHorizontalEdgeAreNeighbours() {
        assertTrue(fromCoords(0.0, 0.0, 0.5, 0.5).isAdjacentTo(fromCoords(0.25, 0.5, 0.75, 1.0)));
    }

    @Test
    public void nodesSharingAVerticalEdgeAreNeighbours() {
        assertTrue(fromCoords(0.0, 0.0, 0.5, 0.5).isAdjacentTo(fromCoords(0.5, 0.25, 1.0, 1.0)));
    }

    @Test
    public void nodesStackedVerticallyButNotAdjacentAreNotNeighbours() {
        assertFalse(fromCoords(0.0, 0.0, 0.5, 0.5).isAdjacentTo(fromCoords(0.6, 0.5, 1, 1)));
    }

    @Test
    public void nodesStackedHorizontallyButNotAdjacentAreNotNeighbours() {
        assertFalse(fromCoords(0.0, 0.0, 0.5, 0.5).isAdjacentTo(fromCoords(0.5, .75, 1, 1)));
    }

    @Test
    public void nonStackedNodesAreNotNeighbours() {
        assertFalse(fromCoords(0.0, 0.0, 0.25, 0.25).isAdjacentTo(fromCoords(0.5, 0.5, 1, 1)));
    }

    @Test
    public void canSplitItselfHorizontallyAndStillBeNeighbours() {
        Node parentNode = fromCoords(0.0, 0.0, 1.0, 1.0);

        List<Node> split = parentNode.splitHorizontal();

        assertEquals(2, split.size());
        assertTrue(split.get(0).isAdjacentTo(split.get(1)));
        assertTrue(split.get(1).isAdjacentTo(split.get(0)));
    }

    @Test
    public void canSplitItselfVerticallyAndStillBeNeighbours() {
        Node parentNode = fromCoords(0.0, 0.0, 1.0, 1.0);

        List<Node> split = parentNode.splitVertical();

        assertEquals(2, split.size());
        assertTrue(split.get(0).isNeighboursWith(split.get(1)));
        assertTrue(split.get(1).isNeighboursWith(split.get(0)));
    }

    @Test
    public void canSplitItselfMultipleTimesAndHaveProperNeighbours() {
        Node parentNode = fromCoords(0.0, 0.0, 1.0, 1.0);

        List<Node> split1 = parentNode.splitHorizontal();
        List<Node> split2 = split1.get(0).splitVertical();
        List<Node> split3 = split1.get(1).splitHorizontal();

        /* Testing structure:
         *
         *   +++++++++++++++++
         *   +       +       +
         *   + s2.0  + s2.1  +
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
    
    @Test
    public void splittingRemovesTheSplitNodeFromItsNeighboursNeighbourList() {
        Node parentNode = fromCoords(0.0, 0.0, 1.0, 1.0);
        
        List<Node> split1 = parentNode.splitVertical();
        List<Node> split2 = split1.get(1).splitVertical();
        
        Node nodeLeft = split1.get(0);
        Node nodeCenter = split2.get(0);
        Node nodeRight = split2.get(1);

        List<Node> split3 = nodeCenter.splitVertical();
        
        Node nodeCenter1 = split3.get(0);
        Node nodeCenter2 = split3.get(1);

        /* Testing structure:
         *
         *   ++++++++++++++++++++++++++
         *   +          +    +    +   +
         *   +          +    +    +   +
         *   +     l    + c1 + c2 + r +
         *   +          +    +    +   +
         *   +          +    +    +   +
         *   +          +    +    +   +
         *   ++++++++++++++++++++ +++++
         *
         */
        
        assertFalse(nodeLeft.isNeighboursWith(nodeCenter));
        assertFalse(nodeRight.isNeighboursWith(nodeCenter));
        assertTrue(nodeLeft.isNeighboursWith(nodeCenter1));
        assertFalse(nodeRight.isNeighboursWith(nodeCenter1));
        assertFalse(nodeLeft.isNeighboursWith(nodeCenter2));
        assertTrue(nodeRight.isNeighboursWith(nodeCenter2));
    }

    private void assertNeighboursWith(Node parentNode, Node... nodes) {
        for (Node node : nodes)
            assertTrue(parentNode.isNeighboursWith(node));
    }

    private Node fromCoords(double blX, double blY, double trX, double trY) {
        return new Node(new Point(blX, blY), new Point(trX, trY));
    }
}
