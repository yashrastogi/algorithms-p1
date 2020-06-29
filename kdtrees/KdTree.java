/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;

    public KdTree() {

    }

    public static void main(String[] args) {
        KdTree kdt = new KdTree();
        kdt.insert(new Point2D(0.5, 0.7));
        kdt.insert(new Point2D(0.4, 0.8));
        kdt.insert(new Point2D(0.6, 0.7));
        kdt.insert(new Point2D(0.9, 0.3));
        kdt.insert(new Point2D(0.2, 0.1));
    }

    public void insert(Point2D val) {
        if (val == null) throw new IllegalArgumentException();
        root = insert(root, val, true);
    }


    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) return new Node(point, null, null, null);
        double comp = (isVertical ? point.x() - node.pt.x() : point.y() - node.pt.y());
        if (comp < 0) node.leftOrBottom = insert(node.leftOrBottom, point, !isVertical);
        else if (comp > 0) node.rightOrTop = insert(node.rightOrTop, point, !isVertical);
        else if (point.equals(node.pt)) {

        }
        else node.rightOrTop = insert(node.rightOrTop, point, !isVertical);
        return node;
    }

    private static class Node {
        private Point2D pt;      // the point
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
