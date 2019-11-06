import java.util.ArrayList;
import java.util.List;

/**
 * @program: Assignment6
 * @description:
 * @author: Nan Sun
 * @create: 2019-10-29 18:56
 **/

public class Cluster {
  private int id;
  private Point center;
  private List<Point> members = new ArrayList<Point>();
  private double err;

  public Cluster(int id, Point center) {
    this.id = id;
    this.center = center;
    this.err = Double.MAX_VALUE;
  }

  public Cluster(int id, Point center, List<Point> members) {
    this.id = id;
    this.center = center;
    this.members = members;
    this.err = Double.MAX_VALUE;
  }

  public void addPoint(Point newPoint) {
    if (!members.contains(newPoint)) {
      members.add(newPoint);
    }
  }

  public int getId() {
    return id;
  }

  public Point getCenter() {
    return center;
  }

  public void setCenter(Point center) {
    this.center = center;
  }

  public List<Point> getMembers() {
    return members;
  }

  public double getErr() {
    return err;
  }

  public void setErr(double err){
    this.err = err;
  }
}