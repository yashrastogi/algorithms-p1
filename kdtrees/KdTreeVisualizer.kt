import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdOut

object KdTreeVisualizer {
    @JvmStatic
    fun main(args: Array<String>) {
        val rect = RectHV(0.0, 0.0, 1.0, 1.0)
        StdDraw.enableDoubleBuffering()
        val kdtree = KdTree()
        while (true) {
            if (StdDraw.isMousePressed()) {
                val x = StdDraw.mouseX()
                val y = StdDraw.mouseY()
                StdOut.printf("%8.6f %8.6f\n", x, y)
                val p = Point2D(x, y)
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y)
//                    kdtree.insert(p)
                    StdDraw.clear()
//                    kdtree.draw()
                    StdDraw.show()
                }
            }
            StdDraw.pause(20)
        }
    }
}