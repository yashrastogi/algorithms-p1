import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import edu.princeton.cs.algs4.StdDraw
import java.util.*

class KdTree {
    private class Node(val point: Point2D, val rect: RectHV?, val leftBottom: Node, val rightTop: Node)

    private val nodes: TreeSet<Node>

    // is the set empty?
    val isEmpty: Boolean
        get() = nodes.size == 0

    // number of points in the set
    val size: Int
        get() = nodes.size


    fun insert(p: Point2D?) { // add the point to the set (if it is not already in the set)
        requireNotNull(p)
        nodes.add(Node(p, null, ))
    }

    operator fun contains(p: Point2D?): Boolean { // does the set contain point p?
        requireNotNull(p)
        return false;
    }

    fun draw() { // draw all points to standard draw
        val i = nodes.iterator()
        StdDraw.setPenColor(StdDraw.BLACK)
        StdDraw.setPenRadius(0.01)
        while (i.hasNext()) {
            val node = i.next()
            node.point.draw()
        }
    }

    fun range(rect: RectHV?): Iterable<Point2D> { // all points that are inside the rectangle (or on the boundary)
        requireNotNull(rect)
        val i = nodes.iterator()
        val rangePoints = ArrayList<Point2D>()
        while (i.hasNext()) {
            val node = i.next()
            if (rect.contains(node.point)) {
                rangePoints.add(node.point)
            }
        }
        return rangePoints
    }

    fun nearest(p: Point2D?): Point2D? { // a nearest neighbor in the set to point p; null if the set is empty
        requireNotNull(p)
        val i = nodes.iterator()
        var minPoint: Point2D? = null
        var minDist: Double? = null
        while (i.hasNext()) {
            val node = i.next()
            val currDist = node.point.distanceTo(p)
            if (minDist == null) {
                minPoint = node.point
                minDist = currDist
            } else if (currDist < minDist) {
                minPoint = node.point
                minDist = currDist
            }
        }
        return minPoint
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val set = PointSET().apply {
                insert(Point2D(1.0, 2.0))
                insert(Point2D(3.0, 4.0))
                insert(Point2D(5.0, 6.0))
                insert(Point2D(7.0, 8.0))
            }
            set.draw()
            println("Size: " + set.size)
            println("Nearest pt. to (3, 3): " + set.nearest(Point2D(3.0, 3.0)))
        }
    }

    init { // construct an empty set of points
        nodes = TreeSet()
    }
}