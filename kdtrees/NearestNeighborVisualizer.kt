import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdDraw

object NearestNeighborVisualizer {
    @JvmStatic
    fun main(args: kotlin.Array<kotlin.String>): kotlin.Unit {
//
//            var t = 0.0
//            while (true) {
//                val x = Math.sin(t)
//                val y = Math.cos(t)
//                StdDraw.clear()
//                StdDraw.filledCircle(x, y, 0.05)
//                StdDraw.filledCircle(-x, -y, 0.05)
//                StdDraw.show()
//                StdDraw.pause(20)
//                t += 0.02
//            }

        // initialize the two data structures with point from file
        val filename = args[0]
        val `in` = In(filename)
        val brute = PointSET()
        val kdtree = KdTree()
        while (!`in`.isEmpty) {
            val x = `in`.readDouble()
            val y = `in`.readDouble()
            val p = edu.princeton.cs.algs4.Point2D(x, y)
            kdtree.insert(p)
            brute.insert(p)
        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering()
        while (true) {

            // the location (x, y) of the mouse
            val x = StdDraw.mouseX()
            val y = StdDraw.mouseY()
            val query = edu.princeton.cs.algs4.Point2D(x, y)

            // draw all of the points
            StdDraw.clear()
            StdDraw.setPenColor(StdDraw.BLACK)
            StdDraw.setPenRadius(0.01)
            brute.draw()

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03)
            StdDraw.setPenColor(StdDraw.RED)
            brute.nearest(query)?.draw()
            StdDraw.setPenRadius(0.02)

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE)
            kdtree.nearest(query)?.draw()
            StdDraw.show()
            StdDraw.pause(40)
        }
    }
}