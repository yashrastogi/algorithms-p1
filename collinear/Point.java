/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point pt1 = new Point(2, 3);
        Point pt2 = new Point(4, 4);
        Point pt3 = new Point(3, 4);
        System.out.println(pt3.compareTo(pt2));
        System.out.println(pt2.slopeTo(pt3));
        System.out.println(pt1.slopeOrder().compare(pt2, pt3));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        double diffy = that.y - this.y;
        double diffx = that.x - this.x;
        if (diffy > 0)
            return -1;
        if (diffy == 0) return Double.compare(0, diffx);
        return 1;
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // Failed on trial 11 of 100000
        // p                        = (2, 7)
        // q                        = (2, 7)
        // student   p.slopeTo(q) = 0.0
        // reference p.slopeTo(q) = -Infinity
        double diffx = that.x - this.x;
        double diffy = that.y - this.y;
        double slope = diffy / diffx;
        if (this.compareTo(that) == 0)      // Equal Line Segments
            slope = Double.NEGATIVE_INFINITY;
        else if (diffy == 0 && diffx != 0)  // Horizontal Line Segment
            slope = 0.0;
        else if (diffx == 0 && diffy != 0)  // Vertical Line Segment
            slope = Double.POSITIVE_INFINITY;
        return slope;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new BySlopeOrder();
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class BySlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slopeP1 = slopeTo(p1);
            // slopeP1 *= 10000;
            // slopeP1 = (int) slopeP1;
            // slopeP1 /= 10000;
            double slopeP2 = slopeTo(p2);
            // slopeP2 *= 10000;
            // slopeP2 = (int) slopeP2;
            // slopeP2 /= 10000;
            return Double.compare(slopeP1, slopeP2);
        }
    }
}
