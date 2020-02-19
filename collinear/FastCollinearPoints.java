/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            // int i=0;
            final Point currPoint = points[i];
            Arrays.sort(points, (o1, o2) -> currPoint.slopeOrder().compare(o1, o2));
            List<Point> eqSlopePts = new ArrayList<Point>();
            for (int j = 0; j < points.length - 1; j++) {
                if (currPoint.slopeTo(points[j + 1]) == currPoint.slopeTo(points[j])) {
                    eqSlopePts.add(points[j + 1]);
                    if (eqSlopePts.isEmpty()) eqSlopePts.add(points[j]);
                    if (j == points.length - 2 && eqSlopePts.size() > 2) {
                        Collections.sort(eqSlopePts);
                        segments.add(
                                new LineSegment(currPoint, eqSlopePts.get(eqSlopePts.size() - 1)));
                    }
                }
                else {
                    if (eqSlopePts.size() > 2) {
                        Collections.sort(eqSlopePts);
                        segments.add(
                                new LineSegment(currPoint, eqSlopePts.get(eqSlopePts.size() - 1)));
                    }
                    eqSlopePts.clear();
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("rs1423.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    int numberOfSegments() {
        return segments.size();
    }
}
