package com.thoughtworks.dht.contentaddressable;

/* Understands a point in key space */
public class Point {
    private final double x;
    private final double y;
    
    public double x() {
        return x;
    }
    
    public double y() {
        return y;
    }
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean horizontallyAlignedTo(Point other) {
        return this.x() == other.x();
    }
    
    public boolean verticallyAlignedTo(Point other) {
        return this.y() == other.y();
    }
    
    public boolean aheadOfHorizontally(Point other) {
        return x() > other.x();
    }
    
    public boolean aheadOfVertically(Point other) {
        return y() > other.y();
    }
}
