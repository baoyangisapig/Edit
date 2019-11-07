/**
 * the class represents the Points.
 *
 * @Author Yang Bao & YiMing Chu
 */
public class Point {
  private double[] coordinate;
  private int clusterId;

  public Point(double[] coordinate) {
    this.coordinate = coordinate;
  }

  public double[] getCoordinate() {
    return coordinate;
  }

  public void setClusterId(int clusterId) {
    this.clusterId = clusterId;
  }

  public int getClusterid() {
    return clusterId;
  }

}