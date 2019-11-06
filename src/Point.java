/**
 * the class represents the Points.
 *
 * @Author Yang Bao & YiMing Chu
 */
public class Point {
  private double[] coordinate;
  private int id;
  private int clusterId;
  private double dist;

  public Point(double[] coordinate) {
    this.id = -1;
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

  public void setDist(double dist) {
    this.dist = dist;
  }

}