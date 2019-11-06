import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

  /**
   * Euclidean distance
   */
  public double getEuclideanDis(Point p1, Point p2) {
    double count_dis = 0;
    double[] p1_local_array = p1.getlocalArray();
    double[] p2_local_array = p2.getlocalArray();

    if (p1_local_array.length != p2_local_array.length) {
      throw new IllegalArgumentException("length of array must be equal!");
    }

    for (int i = 0; i < p1_local_array.length; i++) {
      count_dis += Math.pow(p1_local_array[i] - p2_local_array[i], 2);
    }

    return Math.sqrt(count_dis);
  }

  /**
   * ReadFile
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
        System.out.println("No such file");
      }
    } catch (Exception e) {
      System.out.println("Something wrong with file");
      e.printStackTrace();
    }

    return list;
  }

  public static double[] getX(String path){
    Util helper = new Util();
    List<String> rawDataList = helper.readFromFile(path);
    int N = rawDataList.size();
    double[] X = new double[N];
    for (int i = 0; i < N; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      X[i] = Double.parseDouble(pos[0]);
    }
    return X;
  }

  public static double[] getY(String path){
    Util helper = new Util();
    List<String> rawDataList = helper.readFromFile(path);
    int N = rawDataList.size();
    double[] Y = new double[N];
    for (int i = 0; i < N; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      Y[i] = Double.parseDouble(pos[1]);
    }
    return Y;
  }

  /**
   * Generate a random color.
   *
   * @return a random color
   */
  public static Color randomColorGenerator() {
    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    Color randomColor = new Color(r, g, b);
    return randomColor;
  }

}