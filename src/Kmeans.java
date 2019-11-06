import java.awt.*;
import java.util.Set;
import java.io.IOException;
import java.util.Random;
import java.util.HashSet;
import java.lang.Math;

/**
 * Class for implementation of K-means.
 */
public class Kmeans {

  /**
   * The K clusters of this KMeans.
   */
  private Cluster[] clusters;

  /**
   * Points of this KMeans.
   */
  private Point[] points;

  /**
   * Maximum iteration times.
   */
  private static int MAX_ITERATION_TIME = 100;

  /**
   * Utils for common functions including I/O operation and Math functions.
   */
  private HelperUtils utils;

  /**
   * Value of k for this K-means.
   */
  private int k;

  /**
   * Constructor of KMeans.
   *
   * @param k Constant value k for k-means.
   * @param x X coordinates.
   * @param y Y coordinates.
   */
  public Kmeans(int k, double[] x, double[] y) {
    this.k = k;
    initData(x.length, x, y);
  }

  /**
   * Init K-means.
   *
   * @param length Length of input data.
   * @param x      X coordinates.
   * @param y      Y coordinates.
   */
  private void initData(int length, double[] x, double[] y) {
    utils = new HelperUtils();

    // Initialize all the points.
    points = new Point[length];
    for (int i = 0; i < length; i++) {
      points[i] = new Point(new double[]{x[i], y[i]});
    }

    // Generate random k-sized collections of cluster centers.
    Set<Integer> set = new HashSet<>();
    Random rand = new Random();
    for (int i = 0; i < k; i++) {
      int index = rand.nextInt(length);
      while (set.contains(index)) {
        index = rand.nextInt(length);
      }
      set.add(index);
    }
    int[] centerIndex = new int[k];
    int k = 0;
    for (int num : set) {
      centerIndex[k++] = num;
    }

    // Initialize clusters.
    clusters = new Cluster[k];
    for (int i = 0; i < k; i++) {
      Point curCenter = new Point(new double[]{x[centerIndex[i]], y[centerIndex[i]]});
      clusters[i] = new Cluster(i, curCenter);
    }
  }

  /**
   * Traverse points and arrange them to the best clusters.
   */
  private void clustering() {
    // Clear clusters.
    for (int i = 0; i < clusters.length; i++) {
      clusters[i].getMembers().clear();
    }
    // Arrange points to clusters.
    for (int i = 0; i < points.length; i++) {
      double minDistance = Integer.MAX_VALUE;
      for (int j = 0; j < clusters.length; j++) {
        double distance = Math.min(utils.getEuclidDistance(points[i], clusters[j].getCenter())
                , minDistance);
        if (distance < minDistance) {
          minDistance = distance;
          points[i].setClusterId(j);
          points[i].setDist(distance);
        }
      }
      int id = points[i].getClusterid();
      clusters[id].addPoint(points[i]);
    }
  }

  /**
   * Calculate the new center for each cluster.
   *
   * @return if need further iteration.
   */
  private boolean calculateCenter() {
    boolean ifNeedIter = false;
    for (int i = 0; i < clusters.length; i++) {
      double[] newCenter = new double[2];  // 2-dimension
      for (int j = 0; j < clusters[i].getMembers().size(); j++) {
        newCenter[0] += clusters[i].getMembers().get(j).getCoordinate()[0];
        newCenter[1] += clusters[i].getMembers().get(j).getCoordinate()[1];
      }
      newCenter[0] /= clusters[i].getMembers().size();
      newCenter[1] /= clusters[i].getMembers().size();

      double ne = 0;
      for (int j = 0; j < clusters[i].getMembers().size(); j++) {
        ne += utils.getEuclidDistance(clusters[i].getMembers().get(j), new Point(newCenter));
      }
      ne /= clusters[i].getMembers().size();

      // If the relative error is greater the threshold, more iterations are needed
      if (Math.abs(ne - clusters[i].getErr()) / clusters[i].getErr() > 0.0001) {
        ifNeedIter = true;
      }
      // Set the new center
      clusters[i].setCenter(new Point(newCenter));
      // Set the new error
      clusters[i].setErr(ne);
    }
    return ifNeedIter;
  }

  /**
   * Run the process of Kmeans.
   */
  public void run() {
    int iterCnt = 0;
    for (int i = 0; i < MAX_ITERATION_TIME; i++) {
      clustering();  // Cluster all the points
      if (!calculateCenter()) {
        break;
      }
      iterCnt++;
    }
    System.out.println(String.format("Iteration Times: %d", iterCnt));
  }

  /**
   * Output drawing function.
   *
   * @param outputPath the file path of the output path.
   */
  public void drawOutput(String outputPath) {
    ImagePlotter plotter = new ImagePlotter();
    // Find the boundary of the image
    double[] maxBoundary = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
    double[] minBoundary = new double[]{Double.MAX_VALUE, Double.MAX_VALUE};
    for (Point p : points) {
      double[] pos = p.getCoordinate();
      if (pos[0] > maxBoundary[0]) {
        maxBoundary[0] = pos[0];
      }
      if ((pos[0] < minBoundary[0])) {
        minBoundary[0] = pos[0];
      }
      if (pos[1] > maxBoundary[1]) {
        maxBoundary[1] = pos[1];
      }
      if ((pos[1] < minBoundary[1])) {
        minBoundary[1] = pos[1];
      }
    }

    maxBoundary[0] += 100;
    maxBoundary[1] += 100;
    minBoundary[0] -= 100;
    minBoundary[1] -= 100;

    plotter.setWidth((int) Math.max(Math.abs(minBoundary[0]), Math.abs(maxBoundary[0])) + 100);
    plotter.setHeight((int) Math.max(Math.abs(minBoundary[1]), Math.abs(maxBoundary[1])) + 100);
    plotter.setDimensions((int) minBoundary[0], (int) maxBoundary[0], (int) minBoundary[1], (int) maxBoundary[1]);

    for (Cluster cluster : clusters) {
      Color color = HelperUtils.randomColorGenerator();
      for (int j = 0; j < cluster.getMembers().size(); j++) {
        double[] pos = cluster.getMembers().get(j).getCoordinate();
        plotter.addPoint((int) pos[0], (int) pos[1], color);
      }
    }
    try {
      plotter.write(outputPath);
    } catch (IOException e) {

    }
  }

  /**
   * Get the error for the Kmeans, where error is the average distance of the points and their
   * cluster.
   *
   * @return the error
   */
  private double getErr() {
    double e = 0;
    for (Cluster c : clusters) {
      e += c.getErr();
    }
    return e;
  }

  /**
   * Run ransac for certain times.
   *
   * @param k         the k for K means
   * @param iterTimes the given iterate times
   * @param path      the input file path
   * @return the best kmeans model
   */
  public static Kmeans runRANSAC(int k, int iterTimes, String path) {
    double[] X = HelperUtils.getX(path);
    double[] Y = HelperUtils.getY(path);
    Kmeans bestModel = new Kmeans(k, X, Y);
    double minErr = Double.MAX_VALUE;
    for (int i = 0; i < iterTimes; i++) {
      Kmeans localModel = new Kmeans(k, X, Y);
      localModel.run();
      double localErr = localModel.getErr();
//      System.out.println(localErr);
      if (localErr < minErr) {
        System.out.println(String.format("Last error: %f, New Error: %f", minErr, localErr));
        minErr = localErr;
        bestModel = localModel;
      }
    }
    return bestModel;
  }

}