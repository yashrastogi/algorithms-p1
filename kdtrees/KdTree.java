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


public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {
    }

    public static void main(String[] args) {
        // initialize the two data structures with point from file
        // String filename = args[0];
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.1, 0.8));
        kdtree.insert(new Point2D(0.8, 0.1));
        kdtree.draw();
        PointSET brute = new PointSET();
        brute.insert(new Point2D(0.1, 0.8));
        brute.insert(new Point2D(0.8, 0.1));
        kdtree.draw();

        Point2D query = new Point2D(0.1, 0.7);
        StdDraw.setPenRadius(0.02);
        query.draw();
        StdDraw.setPenRadius(0.04);
        StdDraw.setPenColor(StdDraw.RED);
        kdtree.nearest(query).draw();
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.BLUE);
        brute.nearest(query).draw();
    }

    public void insert(Point2D val) {
        if (val == null)
            throw new IllegalArgumentException();
        size++;
        root = insert(root, val, true);
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        double x1, x2, y1, y2;

        if (node == null)
            return new Node(point, new RectHV(0, 0, 1, 1), null, null);

        double diff = (isVertical ? point.x() - node.pt.x() : point.y() - node.pt.y());

        if (diff < 0) {
            node.leftOrBottom = insert(node.leftOrBottom, point, !isVertical);
            x1 = node.rect.xmin();
            y1 = node.rect.ymin();

            if (isVertical) {
                x2 = node.pt.x();
                y2 = node.rect.ymax();
            }
            else {
                x2 = node.rect.xmax();
                y2 = node.pt.y();
            }
            node.leftOrBottom.rect = new RectHV(x1, y1, x2, y2);
        }
        else if (point.equals(node.pt)) {
            size--;
        }
        else if (diff >= 0) {
            node.rightOrTop = insert(node.rightOrTop, point, !isVertical);

            if (isVertical) { // RIGHT
                x1 = node.pt.x();
                y1 = node.rect.ymin();
            }
            else { // TOP
                x1 = node.rect.xmin();
                y1 = node.pt.y();
            }
            x2 = node.rect.xmax();
            y2 = node.rect.ymax();

            node.rightOrTop.rect = new RectHV(x1, y1, x2, y2);
        }
        return node;
    }

    public Point2D nearest(Point2D that) {
        if (that == null) throw new IllegalArgumentException();
        return nearestRecurse(root, that, null);
    }

    // To find a closest point to a given query point, start at the root and recursively search in both
    // subtrees using the following pruning rule: search a node only only if it might contain a
    // point that is closer than the best one found so far. The effectiveness of the pruning rule depends
    // on quickly finding a nearby point. To do this, organize the recursive method so that when there are
    // two possible subtrees to go down, you always choose the subtree that is on the same side of the
    // splitting line as the query point as the first subtree to explore—the closest point found while
    // exploring the first subtree may enable pruning of the second subtree.

    private Point2D nearestRecurse(Node node, Point2D pt, Point2D minPt) {
        if (node != null) {
            if (node.pt.equals(pt)) {
                // System.out.println('B');
                return pt;
            }
            else {
                double minDist = minPt != null ? minPt.distanceSquaredTo(pt) : 3;
                if (pt.distanceSquaredTo(node.pt) < minDist) {
                    minDist = node.pt.distanceSquaredTo(pt);
                    minPt = node.pt;
                }
                double distLeft = node.leftOrBottom != null ?
                                  node.leftOrBottom.rect.distanceSquaredTo(pt) : 3;
                double distRight = node.rightOrTop != null ?
                                   node.rightOrTop.rect.distanceSquaredTo(pt) : 3;
                // System.out.println(distLeft + " r: "+distRight);
                if (distLeft < distRight) {
                    // System.out.println(
                    //         1 + " " + node.pt + " " + distLeft + " " + distRight + " " + minDist);
                    if (distLeft < minDist) {
                        nearestRecurse(node.leftOrBottom, pt, minPt);
                        nearestRecurse(node.rightOrTop, pt, minPt);
                    }

                }
                else if (distRight < distLeft) {
                    // System.out.println(
                    //         2 + " " + node.pt + " " + distLeft + " " + distRight + " " + minDist);
                    if (distRight < minDist) {
                        nearestRecurse(node.rightOrTop, pt, minPt);
                        nearestRecurse(node.leftOrBottom, pt, minPt);
                    }
                }
                else {
                    // System.out.println(
                    //         3 + " " + node.pt + " " + distLeft + " " + distRight + " " + minDist);
                    if (node.rightOrTop != null && node.rightOrTop.rect.contains(pt)) {
                        nearestRecurse(node.rightOrTop, pt, minPt);
                        nearestRecurse(node.leftOrBottom, pt, minPt);
                    }
                    else {
                        nearestRecurse(node.leftOrBottom, pt, minPt);
                        nearestRecurse(node.rightOrTop, pt, minPt);
                    }
                }
            }

        }
        return minPt;
    }

    public void draw() {
        drawRecurse(root, false);
    }

    private void drawRecurse(Node node, boolean palatt) {
        if (node == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.pt.draw();
        // if (palatt) {
        //     StdDraw.setPenColor(StdDraw.BLUE);
        // }
        // else {
        //     StdDraw.setPenColor(StdDraw.RED);
        // }
        // StdDraw.setPenRadius(0.01);
        // node.rect.draw();
        drawRecurse(node.rightOrTop, palatt);
        drawRecurse(node.leftOrBottom, !palatt);
    }

    public boolean contains(Point2D pt) {
        if (pt == null)
            throw new IllegalArgumentException();
        Node node = contains(root, pt, true);
        if (node == null)
            return false;
        return true;
    }

    private Node contains(Node node, Point2D pt, boolean isVertical) {
        if (node == null)
            return null;
        double diff = (isVertical ? pt.x() - node.pt.x() : pt.y() - node.pt.y());
        if (diff > 0)
            return contains(node.rightOrTop, pt, !isVertical);
        else if (diff < 0)
            return contains(node.leftOrBottom, pt, !isVertical);
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
        List<Point2D> points = new ArrayList<>();
        if (root == null) return points;
        rangeRecurse(root, points, rect);
        return points;
    }

    private void rangeRecurse(Node node, List<Point2D> points, RectHV rect) {
        if (rect.contains(node.pt)) points.add(node.pt);

        boolean intersectsL = node.leftOrBottom != null && rect
                .intersects(node.leftOrBottom.rect);
        boolean intersectsR = node.rightOrTop != null && rect.intersects(node.rightOrTop.rect);

        if (intersectsL && intersectsR) {
            rangeRecurse(node.leftOrBottom, points, rect);
            rangeRecurse(node.rightOrTop, points, rect);
        }
        else if (intersectsL)
            rangeRecurse(node.leftOrBottom, points, rect);
        else if (intersectsR)
            rangeRecurse(node.rightOrTop, points, rect);
    }

    private static class Node {
        private final Point2D pt; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node leftOrBottom; // the left/bottom subtree
        private Node rightOrTop; // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.pt = p;
            this.rect = rect;
            this.leftOrBottom = lb;
            this.rightOrTop = rt;
        }
    }
}
