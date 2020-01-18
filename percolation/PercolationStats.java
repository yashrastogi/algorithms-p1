/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] percolationThresholds;
    private final int trials;
    private double mean = 0, stddev = 0, confidenceLo = 0, confidenceHi = 0, sqrtTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        sqrtTrials = Math.sqrt(trials);
        percolationThresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                // System.out.println("Open "+(int) Math.round(StdRandom.uniform(1, n)) + ", "+(int) Math.round(StdRandom.uniform(1, n)));
                p.open((int) Math.floor(StdRandom.uniform(1, n + 1)),
                       (int) Math.floor(StdRandom.uniform(1, n + 1)));
            }
            percolationThresholds[trial] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException();
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean != 0) {
            return mean;
        }
        mean = StdStats.mean(percolationThresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev != 0) {
            return stddev;
        }
        if (trials == 1) {
            stddev = Double.NaN;
        }
        stddev = StdStats.stddev(percolationThresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (confidenceLo != 0) {
            return confidenceLo;
        }
        confidenceLo = mean() - ((CONFIDENCE_95 * stddev()) / sqrtTrials);
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (confidenceHi != 0) {
            return confidenceHi;
        }
        confidenceHi = mean() + ((CONFIDENCE_95 * stddev()) / sqrtTrials);
        return confidenceHi;
    }

}