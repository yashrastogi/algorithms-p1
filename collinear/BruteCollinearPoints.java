/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    List<LineSegment> segments;
    int numSegments;

    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        segments = new ArrayList<>();
        numSegments = 0;
        for (int i=0; i<points.length; i++) {
            for (int j=i+1; j<points.length; j++) {
                for (int k=j+1; k<points.length; k++) {
                    for (int l=k+1; l<points.length; l++) {
                        if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[l]) == points[i].slopeTo(points[k])) {
                            numSegments++;
                            segments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Point points[] = new Point[5];
        points[0] = new Point(3,3);
        points[1] = new Point(1,1);
        points[2] = new Point(2,2);
        points[3] = new Point(4,4);
        points[4] = new Point(1,2);
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for(LineSegment seg : bcp.segments) {
            System.out.println(seg);
        }
    }
}
