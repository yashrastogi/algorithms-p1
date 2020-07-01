/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {

    }

    public static void main(String[] args) {
        // initialize the two data structures with point from file
        // String filename = args[0];
        In in = new In("inputs\\circle10.txt");
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
    }

    public void insert(Point2D val) {
        if (val == null) throw new IllegalArgumentException();
        size++;
        root = insert(root, val, true);
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) return new Node(point, new RectHV(0, 0, 1, 1), null, null);
        double diff = (isVertical ? point.x() - node.pt.x() : point.y() - node.pt.y());
        if (diff < 0) {
            node.leftOrBottom = insert(node.leftOrBottom, point, !isVertical);
            if (isVertical) node.leftOrBottom.rect = new RectHV(0, 0, node.pt.x(), 1);

            else node.leftOrBottom.rect = new RectHV(0, 0, 1, node.pt.y());
        }
        else if (diff > 0) {
            node.rightOrTop = insert(node.rightOrTop, point, !isVertical);
            if (isVertical) node.rightOrTop.rect = (node.pt.x() < 0.5 ?
                                                    new RectHV(node.pt.x(), 0, 1 - node.pt.x(), 1) :
                                                    new RectHV(1 - node.pt.x(), 0, node.pt.x(), 1));

            else node.rightOrTop.rect = (node.pt.y() < 0.5 ?
                                         new RectHV(0, node.pt.y(), 1, 1 - node.pt.y()) :
                                         new RectHV(0, 1 - node.pt.y(), 1, node.pt.y()));
        }
        else if (point.equals(node.pt)) {
            size--;
        }
        else {
            node.rightOrTop = insert(node.rightOrTop, point, !isVertical);
            if (isVertical) node.rightOrTop.rect = (node.pt.x() < 0.5 ?
                                                    new RectHV(node.pt.x(), 0, 1 - node.pt.x(), 1) :
                                                    new RectHV(1 - node.pt.x(), 0, node.pt.x(), 1));

            else node.rightOrTop.rect = (node.pt.y() < 0.5 ?
                                         new RectHV(0, node.pt.y(), 1, 1 - node.pt.y()) :
                                         new RectHV(0, 1 - node.pt.y(), 1, node.pt.y()));
        }
        return node;
    }

    public Point2D nearest(Point2D that) {
        if (that == null) throw new IllegalArgumentException();
        double minDist = -1;
        Point2D minPoint = null;
        return null;
    }

    public void draw() {
        drawRecurse(root, false);
    }

    private void drawRecurse(Node node, boolean palatt) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.03);
        node.pt.draw();
        if (palatt) {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
        }
        StdDraw.setPenRadius(0.01);
        node.rect.draw();
        drawRecurse(node.rightOrTop, palatt);
        drawRecurse(node.leftOrBottom, !palatt);
    }

    public boolean contains(Point2D pt) {
        if (pt == null) throw new IllegalArgumentException();
        Node node = contains(root, pt, true);
        if (node == null) return false;
        return true;
    }

    private Node contains(Node node, Point2D pt, boolean isVertical) {
        if (node == null) return null;
        double diff = (isVertical ? pt.x() - node.pt.x() : pt.y() - node.pt.y());
        if (diff > 0) return contains(node.rightOrTop, pt, !isVertical);
        else if (diff < 0) return contains(node.leftOrBottom, pt, !isVertical);
        else // if (diff == 0)
        {
            if (pt.equals(node.pt)) {
                return node;
            }
            return contains(node.rightOrTop, pt, !isVertical);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> points = new ArrayList<Point2D>();
        rangeRecurse(root, points, rect);
        return points;
    }

    private void rangeRecurse(Node node, List<Point2D> points, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.pt)) points.add(node.pt);
        rangeRecurse(node.leftOrBottom, points, rect);
        rangeRecurse(node.rightOrTop, points, rect);
        // return node;
    }

    private static class Node {
        private final Point2D pt;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node leftOrBottom;        // the left/bottom subtree
        private Node rightOrTop;        // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.pt = p;
            this.rect = rect;
            this.leftOrBottom = lb;
            this.rightOrTop = rt;
        }
    }
}
