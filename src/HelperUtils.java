import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Helper utils providing basic functions such as Maths and I/O.
 */
public class HelperUtils {

  /**
   * Get Euclid distance of two points.
   *
   * @param p1 First point.
   * @param p2 Second point.
   * @return Distance between the two pointer.
   */
  public double getEuclidDistance(Point p1, Point p2) {
    double count_dis = 0;
    double[] p1_local_array = p1.getCoordinate();
    double[] p2_local_array = p2.getCoordinate();

    if (p1_local_array.length != p2_local_array.length) {
      throw new IllegalArgumentException("length of array must be equal");
    }

    for (int i = 0; i < p1_local_array.length; i++) {
      count_dis += Math.pow(p1_local_array[i] - p2_local_array[i], 2);
    }

    return Math.sqrt(count_dis);
  }

  /**
   * Read string from file.
   *
   * @param filePath Path of file to read.
   * @return String retrieved from the file.
   */
  public List<String> readFromFile(String filePath) {
    List<String> list = new ArrayList<String>();
    try {
      String encoding = "UTF-8";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
          list.add(lineTxt);
        }
        bufferedReader.close();
        read.close();
      } else {
        System.out.println("the path is wrong");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  /**
   * Get all the x coordinates from file of path.
   *
   * @param path Path of file to read.
   * @return All the x coordinates retrieved from file of path.
   */
  public static double[] getX(String path) {
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile(path);
    int size = rawDataList.size();
    double[] x = new double[size];
    for (int i = 0; i < size; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      x[i] = Double.parseDouble(pos[0]);
    }
    return x;
  }

  /**
   * Get all the y coordinates from file of path.
   *
   * @param path Path of file to read.
   * @return All the y coordinates retrieved from file of path.
   */
  public static double[] getY(String path) {
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile(path);
    int size = rawDataList.size();
    double[] y = new double[size];
    for (int i = 0; i < size; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      y[i] = Double.parseDouble(pos[1]);
    }
    return y;
  }

  /**
   * Generate a random color.
   *
   * @return Generated random color.
   */
  public static Color produceColor() {
    Random rand = new Random();
    return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
  }

}