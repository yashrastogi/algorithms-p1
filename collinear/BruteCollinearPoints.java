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
    private List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        segments = new ArrayList<>();
        for (int i=0; i<points.length; i++) {
            for (int j=i+1; j<points.length; j++) {
                for (int k=j+1; k<points.length; k++) {
                    for (int l=k+1; l<points.length; l++) {
                        if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[l]) == points[i].slopeTo(points[k])) {
                            segments.add(new LineSegment(points[i], points[l]));
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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
