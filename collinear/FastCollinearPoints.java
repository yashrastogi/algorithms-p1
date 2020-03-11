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
    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        // arg checks
        if (points == null) throw new IllegalArgumentException();
        for (Point pt : points) {
            if (pt == null) throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            Point pt = points[i];
            for (int j = 0; j < points.length; j++) {
                if (pt.compareTo(points[j]) == 0 && j != i) throw new IllegalArgumentException();
            }
        }

        Point[] sortedPointsOne = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(sortedPointsOne);
        Point[] slopeSortedPts = Arrays.copyOfRange(sortedPointsOne, 0, points.length);
        segments = new ArrayList<LineSegment>();
        // int indexEvaluatedTill = 0;
        for (int i = 0; i < sortedPointsOne.length; i++) {
            final Point currPoint = sortedPointsOne[i];
            Arrays.sort(slopeSortedPts, (o1, o2) -> currPoint.slopeOrder().compare(o1, o2));
            List<Point> eqSlopePts = new ArrayList<Point>();
            // if (i > indexEvaluatedTill) indexEvaluatedTill = 0;
            for (int j = 0; j < slopeSortedPts.length - 1; j++) {
                if (Double.compare(currPoint.slopeTo(slopeSortedPts[j + 1]),
                                   currPoint.slopeTo(slopeSortedPts[j])) == 0) {
                    if (eqSlopePts.isEmpty()) eqSlopePts.add(slopeSortedPts[j]);
                    eqSlopePts.add(slopeSortedPts[j + 1]);
                    if (j == slopeSortedPts.length - 2 && eqSlopePts.size() > 2) {
                        Collections.sort(eqSlopePts);
                        segments.add(
                                new LineSegment(currPoint, eqSlopePts.get(eqSlopePts.size() - 1)));
                        // indexEvaluatedTill = j;
                    }
                }
                else {
                    if (eqSlopePts.size() > 2) {
                        Collections.sort(eqSlopePts);
                        segments.add(
                                new LineSegment(currPoint, eqSlopePts.get(eqSlopePts.size() - 1)));
                        // indexEvaluatedTill = j;
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

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public int numberOfSegments() {
        return segments.size();
    }
}
