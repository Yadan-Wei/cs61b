package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import static java.lang.Math.sqrt;

public class PercolationStats {

    private double[] sampleP;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        sampleP = new double[T];
        this.T = T;
        for (int i = 0; i < T; i++) {
            Percolation sample = pf.make(N);
            while (!sample.percolates()) {
                int randomRow = StdRandom.uniform(0, N);
                int randomCol = StdRandom.uniform(0, N);
                sample.open(randomRow, randomCol);
            }
            double threshold = (double) sample.numberOfOpenSites() / (N * N);
            sampleP[i] = threshold;
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sampleP);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sampleP);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / sqrt(T);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / sqrt(T);
    }

    // public static void main(String[] args) {
    //    PercolationFactory pf = new PercolationFactory();
    //    PercolationStats ps = new PercolationStats(40,30, pf);
    //    System.out.println(ps.mean());
    //    System.out.println(ps.stddev());
    //}
}
