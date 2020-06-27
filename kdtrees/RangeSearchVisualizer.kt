import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.RectHV
import edu.princeton.cs.algs4.StdDraw

object RangeSearchVisualizer {
    @JvmStatic
    fun main(args: kotlin.Array<kotlin.String>): kotlin.Unit {

        // initialize the data structures from file
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
        var x0 = 0.0
        var y0 = 0.0 // initial endpoint of rectangle
        var x1 = 0.0
        var y1 = 0.0 // current location of mouse
        var isDragging = false // is the user dragging a rectangle

        // draw the points
        StdDraw.clear()
        StdDraw.setPenColor(StdDraw.BLACK)
        StdDraw.setPenRadius(0.01)
        brute.draw()
        StdDraw.show()

        // process range search queries
        StdDraw.enableDoubleBuffering()
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x1 = StdDraw.mouseX()
                x0 = x1
                y1 = StdDraw.mouseY()
                y0 = y1
                isDragging = true
            } else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX()
                y1 = StdDraw.mouseY()
            } else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false
            }

            // draw the points
            StdDraw.clear()
            StdDraw.setPenColor(StdDraw.BLACK)
            StdDraw.setPenRadius(0.01)
            brute.draw()

            // draw the rectangle
            val rect = RectHV(java.lang.Math.min(x0, x1), java.lang.Math.min(y0, y1),
                    java.lang.Math.max(x0, x1), java.lang.Math.max(y0, y1))
            StdDraw.setPenColor(StdDraw.BLACK)
            StdDraw.setPenRadius()
            rect.draw()

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03)
            StdDraw.setPenColor(StdDraw.RED)
            for (p in brute.range(rect)) p.draw()

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(0.02)
            StdDraw.setPenColor(StdDraw.BLUE)
            for (p in kdtree.range(rect)) p.draw()
            StdDraw.show()
            StdDraw.pause(20)
        }
    }
}