import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelperUtils {

  /**
   * get the Geometric distance of two points.
   * @param p1 first point.
   * @param p2 second point.
   * @return the distance of the two pointer.
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
   * the class helps to read from file
   * @param filePath the path of the file
   * @return the result.
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
      }
      else {
        System.out.println("the path is wrong");
      }
    } catch (Exception e) {
    }

    return list;
  }

  public static double[] getX(String path){
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile(path);
    int size = rawDataList.size();
    double[] X = new double[size];
    for (int i = 0; i < size; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      X[i] = Double.parseDouble(pos[0]);
    }
    return X;
  }

  public static double[] getY(String path){
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile(path);
    int size = rawDataList.size();
    double[] Y = new double[size];
    for (int i = 0; i < size; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      Y[i] = Double.parseDouble(pos[1]);
    }
    return Y;
  }

  /**
   * the class is to help to produce a random color.
   * @return a random color
   */
  public static Color produceColor() {
    Random rand = new Random();
    return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
  }

}