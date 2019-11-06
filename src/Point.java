/**
 * @program: Assignment6
 * @description:
 * @author: Nan Sun
 * @create: 2019-10-29 17:53
 **/
public class Point {
  private double[] coordinate;
  private int id;
  private int clusterId;  //which cluster it belongs to
  private double dist;     // the distance between the point and the cluster

  public Point(int id, double[] coordinate) {
    this.id = id;
    this.coordinate = coordinate;
  }

  public Point(double[] coordinate) {
    this.id = -1; //This point does not belong to any class
    this.coordinate = coordinate;
  }

  public double[] getCoordinate() {
    return coordinate;
  }

  public int getId() {
    return id;
  }

  public void setClusterId(int clusterId) {
    this.clusterId = clusterId;
  }

  public int getClusterid() {
    return clusterId;
  }

  public double getDist() {
    return dist;
  }

  public void setDist(double dist) {
    this.dist = dist;
  }

}