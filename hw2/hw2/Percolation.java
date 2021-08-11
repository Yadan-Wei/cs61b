package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF sites; // sites with bottom site.
    private WeightedQuickUnionUF sites2; // sites without bottom site.
    private int topSite;
    private int bottomSite;
    private boolean[][] flagOpen;
    private int numOpen = 0;

    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        sites = new WeightedQuickUnionUF( N * N + 2); // initialize a grid includes top and bottom sites
        sites2 = new WeightedQuickUnionUF(N * N + 1); // initialize a grid includes top sites.
        topSite = N * N; // the last two sites are set to be top and bottom sites
        bottomSite = N * N + 1;

        //union top site with all first row sites
        for (int i = 0; i < N; i ++) {
            sites.union(topSite,xyTo1D(0, i));
            sites2.union(topSite, xyTo1D(0, i));
        }
        // union bottom site with all last row sites
        for (int i = 0;i < N; i++) {
            sites.union(bottomSite, xyTo1D(N - 1, i));
        }

        flagOpen = new boolean[N][N];
        for ( int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                flagOpen[i][j] = false;
            }
        }
    }

    private void validateSite(int row, int col) {
        if (row >= N || row < 0 || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void connectNeighbor(int row, int col) {
        int siteConnected = xyTo1D(row, col);
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            sites.union(xyTo1D(row - 1,col),siteConnected);
            sites2.union(xyTo1D(row - 1,col),siteConnected);
        }
        if (row + 1 < N && isOpen(row + 1, col)){
            sites.union(xyTo1D(row + 1, col), siteConnected);
            sites2.union(xyTo1D(row + 1, col), siteConnected);
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            sites.union(xyTo1D(row, col - 1), siteConnected);
            sites2.union(xyTo1D(row, col - 1), siteConnected);
        }
        if (col + 1 < N && isOpen(row, col + 1)) {
            sites.union(xyTo1D(row, col + 1), siteConnected);
            sites2.union(xyTo1D(row, col + 1), siteConnected);
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        if (isOpen(row, col)) {
            return;
        }
        flagOpen[row][col] = true;
        numOpen += 1;
        connectNeighbor(row,col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return flagOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return sites2.connected(xyTo1D(row, col), topSite);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(bottomSite, topSite);
    }
    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
