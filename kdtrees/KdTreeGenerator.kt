import edu.princeton.cs.algs4.StdOut
import edu.princeton.cs.algs4.StdRandom

object KdTreeGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        val n = args[0].toInt()
        for (i in 0 until n) {
            val x = StdRandom.uniform(0.0, 1.0)
            val y = StdRandom.uniform(0.0, 1.0)
            StdOut.printf("%8.6f %8.6f\n", x, y)
        }
    }
}