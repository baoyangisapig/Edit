import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.lang.Math;

/**
 * @program: Assignment6
 * @description:
 * @author: Nan Sun
 * @create: 2019-10-29 17:55
 **/

/**
 * The class for Kmeans.
 * Thanks to article https://blog.csdn.net/xsdxs/article/details/53435667.
 */
public class Kmeans{

  private Point[] points;
  private Point[] clusterCenters;
  private Cluster[] clusters;
  private Util helper;
  private int maxIterTimes;
  private String outputPath;

  /**
   * The construct function for Kmeans class.
   *
   * @param k k for K means
   * @param x the X coordinates of the data sets.
   * @param y the Y coordinates of the data sets.
   */
  public Kmeans(int k, double[] x, double[] y) {
    // Initialize the helper object.
    helper = new Util();
    // Set the maximum iteration times.
    maxIterTimes = 100;
    // Initialize all the points in Point type
    points = new Point[x.length];
    for (int i = 0; i < x.length; i++) {
      points[i] = new Point(new double[]{x[i], y[i]});
    }

    // Generate the random index of the points as cluster centers.
    Set<Integer> initialIndSet = new HashSet<>();
    Random rand = new Random();
    int ranNum;
    for (int i = 0; i < k; i++) {
      do {
        ranNum = rand.nextInt(x.length);
      } while (initialIndSet.contains(ranNum));
      initialIndSet.add(ranNum);
    }

    Integer[] initialIndArr = new Integer[k];
    initialIndSet.toArray(initialIndArr);

    // Initialize the centerPosition array.
    clusterCenters = new Point[k];
    for (int i = 0; i < k; i++) {
      clusterCenters[i] = new Point(new double[]{x[initialIndArr[i]], y[initialIndArr[i]]});
//      System.out.println(clusterCenters[i].toString());
    }

    // Initialize the Cluster
    clusters = new Cluster[k];
    for (int i = 0; i < k; i++) {
      clusters[i] = new Cluster(i, clusterCenters[i]);
//      System.out.println(clusters[i].toString());
    }
  }

  /**
   * Allocate the best cluster for each points.
   */
  private void clustering() {
    for (int i = 0; i < points.length; i++) {
      double minDis = Integer.MAX_VALUE;
      for (int j = 0; j < clusters.length; j++) {
        double tmpDis = (double) Math.min(helper.getEuclideanDis(points[i], clusters[j].getCenter()), minDis);
        if (tmpDis < minDis) {
          minDis = tmpDis;
          points[i].setClusterId(j);
          points[i].setDist(tmpDis);
        }
      }
    }
    // Clear all the clusters.
    for (int i = 0; i < clusters.length; i++) {
      clusters[i].getMembers().clear();
    }
    // Update all the points into the corresponding cluster.
    for (int i = 0; i < points.length; i++) {
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
        newCenter[0] += clusters[i].getMembers().get(j).getlocalArray()[0];
        newCenter[1] += clusters[i].getMembers().get(j).getlocalArray()[1];
      }
      newCenter[0] /= clusters[i].getMembers().size();
      newCenter[1] /= clusters[i].getMembers().size();

      double ne = 0;
      for (int j = 0; j < clusters[i].getMembers().size(); j++) {
        ne += helper.getEuclideanDis(clusters[i].getMembers().get(j), new Point(newCenter));
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
    for (int i = 0; i < maxIterTimes; i++) {
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
      double[] pos = p.getlocalArray();
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
      Color color = Util.randomColorGenerator();
      for (int j = 0; j < cluster.getMembers().size(); j++) {
        double[] pos = cluster.getMembers().get(j).getlocalArray();
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
    double[] X = Util.getX(path);
    double[] Y = Util.getY(path);
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