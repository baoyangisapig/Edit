import java.util.ArrayList;
import java.util.List;


/**
 * the class represents the Cluster.
 *
 * @Author Yang Bao & YiMing Chu
 */

public class Cluster {
  private Point center;
  private List<Point> groups = new ArrayList<>();
  private double dif;

  /**
   * Constructor of cluster.
   *
   * @param center Center of current cluster.
   */
  public Cluster(Point center) {
    this.center = center;
    this.dif = Double.MAX_VALUE;
  }

  /**
   * Add point to this cluster.
   *
   * @param newPoint Point to be added to this cluster.
   */
  public void addPoint(Point newPoint) {
    if (!groups.contains(newPoint)) {
      groups.add(newPoint);
    }
  }

  /**
   * Get err of current cluster.
   *
   * @return Err of current cluster.
   */
  public double getDif() {
    return dif;
  }

  /**
   * Set err of current cluster.
   *
   * @param dif Err of current cluster.
   */
  public void setDif(double dif) {
    this.dif = dif;
  }

  /**
   * Get center of current cluster.
   *
   * @return Center of current cluster.
   */
  public Point getCenter() {
    return center;
  }

  /**
   * Set center of current cluster.
   *
   * @param center Center of cluster.
   */
  public void setCenter(Point center) {
    this.center = center;
  }

  /**
   * Get points of current cluster.
   *
   * @return Group of points of current cluster.
   */
  public List<Point> getGroups() {
    return groups;
  }
}