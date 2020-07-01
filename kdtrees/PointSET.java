/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }            // does the set contain point p?

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);
        for (Point2D point : points) point.draw();
    }       // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> rangePoints = new ArrayList<>();
        for (Point2D p : points) if (rect.contains(p)) rangePoints.add(p);
        return rangePoints;
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double minDist = -1;
        Point2D minPoint = null;
        for (Point2D curr : points) {
            double pToCurrDistance = p.distanceSquaredTo(curr);
            if (minDist == -1 || pToCurrDistance < minDist) {
                minDist = pToCurrDistance;
                minPoint = curr;
            }
        }
        return minPoint;
    } // a nearest neighbor in the set to point p; null if the set is empty

}
