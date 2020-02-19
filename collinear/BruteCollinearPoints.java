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
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
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


        Point[] sortedPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(sortedPoints);
        segments = new ArrayList<>();
        for (int i = 0; i < sortedPoints.length; i++) {
            for (int j = i + 1; j < sortedPoints.length; j++) {
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    for (int m = k + 1; m < sortedPoints.length; m++) {
                        if (Double
                                .compare(sortedPoints[i].slopeTo(sortedPoints[j]),
                                         sortedPoints[i].slopeTo(sortedPoints[k]))
                                == 0
                                && Double.compare(sortedPoints[i].slopeTo(sortedPoints[m]),
                                                  sortedPoints[i].slopeTo(sortedPoints[k])) == 0) {
                            if (sortedPoints[i] != sortedPoints[m])
                                segments.add(new LineSegment(sortedPoints[i], sortedPoints[m]));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("aaainput1.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        points[2] = null;

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            // p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public LineSegment[] segments() {
        LineSegment[] segArr = segments.toArray(new LineSegment[segments.size()]);

        return segArr;
    }

    public int numberOfSegments() {
        return segments.size();
    }
}
