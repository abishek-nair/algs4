import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This class performs N-time simulation on the percolation
 * of a system to determine exactly when a system percolates.
 */
public class PercolationStats {

    private final int trialsCount;
    private final int dimension;
    private double[] percolationThresholdTrack;
    private static final double INTERVAL_FOR_95CONFIDENCE_LEVEL = 1.96;

    /**
     * Constructs a new instance of PercolationStats.
     * @param n The dimensions of the grid
     * @param trials No. of times the simulation must be executed on the system
     */
    public PercolationStats(final int n, final int trials) {

        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }

        if (trials <= 0) {
            String exMessage = "trials should be greater than 0";
            throw new IllegalArgumentException(exMessage);
        }

        this.dimension = n;
        this.trialsCount = trials;
        this.percolationThresholdTrack = new double[trialsCount];

        beginTrials();
    }

    private void beginTrials() {

        for (int trialIndex = 0; trialIndex < this.trialsCount; trialIndex++) {

            Percolation percolationModel = new Percolation(dimension);

            while (!percolationModel.percolates()) {

                int randRow = StdRandom.uniform(1, this.dimension + 1);
                int randCol = StdRandom.uniform(1, this.dimension + 1);

                percolationModel.open(randRow, randCol);
            }

            int openNodesCount = percolationModel.numberOfOpenSites();
            int totalNodesCount = this.dimension * this.dimension;
            double percolationThreshold =
                    (double) openNodesCount / totalNodesCount;

            this.percolationThresholdTrack[trialIndex] = percolationThreshold;
        }
    }

    public double mean() {

        double mean = StdStats.mean(this.percolationThresholdTrack);

        return mean;
    }

    public double stddev() {

        double stdDeviation = StdStats.stddev(this.percolationThresholdTrack);

        return stdDeviation;
    }

    public double confidenceLo() {

        double mean = mean();
        double stdDeviation = stddev();

        double confidenceLo =
            (mean
                - ((INTERVAL_FOR_95CONFIDENCE_LEVEL * stdDeviation))
                    / Math.sqrt(this.trialsCount));

        return confidenceLo;
    }

    public double confidenceHi() {

        double mean = mean();
        double stdDeviation = stddev();

        double confidenceHi =
            (mean
                + ((INTERVAL_FOR_95CONFIDENCE_LEVEL * stdDeviation))
                    / Math.sqrt(this.trialsCount));

        return confidenceHi;
    }

    public static void main(final String[] args) {

        int dimension, trialsCount;

        if (args.length < 2) {

            printUsage();
            return;
        }

        dimension = Integer.parseInt(args[0]);
        trialsCount = Integer.parseInt(args[1]);

        PercolationStats percolationStats =
            new PercolationStats(dimension, trialsCount);

        double sampleMean = percolationStats.mean();
        double stdDeviation = percolationStats.stddev();
        double confidence95Lo = percolationStats.confidenceLo();
        double confidence95Hi = percolationStats.confidenceHi();

        StdOut.println("mean\t\t = " + sampleMean);
        StdOut.println("stddev\t\t = " + stdDeviation);
        StdOut.println(
            "95% confidence interval\t\t = ["
                + confidence95Lo + ", " + confidence95Hi + "]");
    }

    private static void printUsage() {

        StdOut.println("java PercolationStats <grid-dimension> <trials-count>");
    }
}
