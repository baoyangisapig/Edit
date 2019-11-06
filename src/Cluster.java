import java.util.ArrayList;
import java.util.List;


/**
 * the class represents the Cluster.
 *
 * @Author Yang Bao & YiMing Chu
 */

public class Cluster {
  private int id;
  private Point center;
  private List<Point> groups = new ArrayList<>();
  private double dif;

  public Cluster(int id, Point center) {
    this.id = id;
    this.center = center;
    this.dif = Double.MAX_VALUE;
  }

  public void addPoint(Point newPoint) {
    if (!groups.contains(newPoint)) {
      groups.add(newPoint);
    }
  }

  public double getDif() {
    return dif;
  }

  public void setDif(double dif) {
    this.dif = dif;
  }

  public Point getCenter() {
    return center;
  }

  public void setCenter(Point center) {
    this.center = center;
  }

  public List<Point> getGroups() {
    return groups;
  }
}