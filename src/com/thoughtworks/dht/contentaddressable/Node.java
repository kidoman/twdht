package com.thoughtworks.dht.contentaddressable;

/* Understands the key value pairs in its region */
public class Node {
    private final Point bottomLeft;
    private final Point topRight;

    public Node(Point bottomLeft, Point topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }
    
    private boolean sharesHorizontalEdgeWith(Node other) {
        return this.topRight.verticallyAlignedTo(other.bottomLeft) &&
               this.topRight.aheadOfHorizontally(other.bottomLeft);
    }
    
    private boolean sharesVerticalEdgeWith(Node other) {
        return this.topRight.horizontallyAlignedTo(other.bottomLeft) &&
               this.topRight.aheadOfVertically(other.bottomLeft);
    }

    public boolean isAdjacentTo(Node other) {
        return this.sharesHorizontalEdgeWith(other) || other.sharesHorizontalEdgeWith(this) ||
               this.sharesVerticalEdgeWith(other) || other.sharesVerticalEdgeWith(this);
    }
}
