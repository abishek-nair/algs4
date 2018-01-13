import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int dimension;
    private final int virtualTopSiteIndex;
    private final int virtualBottomSiteIndex;
    private final WeightedQuickUnionUF quickUnionUF;

    private boolean[][] siteOpenStatusArray;
    private int openSitesCount;

    public Percolation(final int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.dimension = n;
        this.openSitesCount = 0;
        this.virtualTopSiteIndex = 0;
        this.virtualBottomSiteIndex = n * n;
        this.siteOpenStatusArray = new boolean[n][n];

        // To map the n*n grid, and additional nodes for the virtualTop
        // virtualBottom site.
        int nodesCount = n * n + 2;
        this.quickUnionUF = new WeightedQuickUnionUF(nodesCount);
    }

    public void open(final int rowP, final int colP) {
        /*
        * open should mark the site as being opened and create connections
        * between this site and all it's adjacent open sites using the
        * union-find::union method
        * */

        int row = rowP, col = colP;
        validateCoordinatesInput(row, col);

        if (isOpen(row, col)) {
            return;
        }

        row -= 1;
        col -= 1;

        int siteIndex = convert2DTo1DCoordinate(row, col);

        if (row == 0) {

            // connect top row site to the virtualTop node
            quickUnionUF.union(siteIndex, this.virtualTopSiteIndex);
        }

        if (row == (this.dimension - 1)) {

            // connect bottom row site to the virtualBottom node
            quickUnionUF.union(siteIndex, this.virtualBottomSiteIndex);
        }

        siteOpenStatusArray[row][col] = true;

        openSitesCount++;
        joinWithAdjacentSites(row, col);
    }

    private void joinWithAdjacentSites(final int row, final int col) {

        int siteIndex = convert2DTo1DCoordinate(row, col);

        if (row > 0) {
            // int topNeighbourIndex = convert2DTo1DCoordinate(row - 1, col);
            connectSites(siteIndex, row - 1, col);
        }

        if (row < (this.dimension - 1)) {
            // int bottomNeighbourIndex = convert2DTo1DCoordinate(row + 1, col);
            connectSites(siteIndex, row + 1, col);
        }

        if (col > 0) {
            // int leftNeighbourIndex = convert2DTo1DCoordinate(row, col - 1);
            connectSites(siteIndex, row, col - 1);
        }

        if (col < (this.dimension - 1)) {
            // int rightNeighbourIndex = convert2DTo1DCoordinate(row, col+1);
            connectSites(siteIndex, row, col + 1);
        }

    }

    private void connectSites(
        final int nodeIndex, final int newRow, final int newCol) {

        if (!isOpen(newRow + 1, newCol + 1)) {
            return;
        }

        int newSiteIndex = convert2DTo1DCoordinate(newRow, newCol);
        quickUnionUF.union(nodeIndex, newSiteIndex);
    }

    public boolean isOpen(final int rowP, final int colP) {

        int row = rowP, col = colP;
        validateCoordinatesInput(row, col);
        row -= 1;
        col -= 1;

        boolean isSiteOpen = siteOpenStatusArray[row][col];

        return isSiteOpen;
    }

    public boolean isFull(final int rowP, final int colP) {

        int row = rowP, col = colP;
        validateCoordinatesInput(row, col);
        row -= 1;
        col -= 1;

        boolean isSiteFull = false;

        if (isOpen(rowP, colP)) {

            int nodeIndex = convert2DTo1DCoordinate(row, col);
            isSiteFull = quickUnionUF.connected(
                                        nodeIndex,
                                        this.virtualTopSiteIndex);
        }

        return isSiteFull;
    }

    public int numberOfOpenSites() {

        return openSitesCount;
    }

    public boolean percolates() {

        boolean doesPercolate =
                quickUnionUF.connected(
                        this.virtualTopSiteIndex,
                        this.virtualBottomSiteIndex);

        return doesPercolate;
    }

    private int convert2DTo1DCoordinate(final int row, final int col) {

        int linearDimension = ((this.dimension * row) + col);

        return linearDimension;
    }

    private void validateCoordinatesInput(final int row, final int col) {

        if (row < 1 || row > this.dimension) {
            throw new IllegalArgumentException(
                    String.format("Row index is out of bounds. r:%d, c:%d",
                                    row, col));
        }

        if (col < 1 || col > this.dimension) {
            throw new IllegalArgumentException(
                    String.format("Col index is out of bounds. r:%d, c:%d",
                                    row, col));
        }
    }

    public static void main(final String[] args) {

        Percolation percolation = new Percolation(1);

        percolation.open(1,  1);
        percolation.percolates();
    }

}
