import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() { // construct an empty set of points
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() { // is the set empty?
        return points.size() == 0;
    }

    public int size() { // number of points in the set
        return points.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();

        points.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();

        return points.contains(p);
    }

    public void draw() { // draw all points to standard draw
        Iterator<Point2D> i = points.iterator();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        while (i.hasNext()) {
            Point2D p = i.next();
            System.out.println(p.x() + "" + p.y());
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();

        Iterator<Point2D> i = points.iterator();
        List<Point2D> rangePoints = new ArrayList<>();
        while (i.hasNext()) {
            Point2D p = i.next();
            if (rect.contains(p)) {
                rangePoints.add(p);
            }
        }
        return rangePoints;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();

        Iterator<Point2D> i = points.iterator();
        Point2D minPoint = null;
        Double minDist = null;
        while (i.hasNext()) {
            Point2D e = i.next();
            Double currDist = e.distanceTo(p);
            if (minDist == null) {
                minPoint = e;
                minDist = currDist;
            } else if (currDist < minDist) {
                minPoint = e;
                minDist = currDist;
            }
        }
        return minPoint;
    }

    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.insert(new Point2D(1, 2));
        set.insert(new Point2D(3, 4));
        set.insert(new Point2D(5, 6));
        set.insert(new Point2D(7, 8));
        set.draw();
        System.out.println("Size: " + set.size());
        System.out.println("Nearest pt. to (3, 3): " + set.nearest(new Point2D(3, 3)));
    }
}