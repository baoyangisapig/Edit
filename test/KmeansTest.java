import org.junit.Test;

import java.util.List;

public class KmeansTest {
  @Test
  public void testInput() {
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile("./src/data/k-8.txt");
    int N = rawDataList.size();
    double[] X = new double[N];
    double[] Y = new double[N];

    for (int i = 0; i < N; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      X[i] = Double.parseDouble(pos[0]);
      Y[i] = Double.parseDouble(pos[1]);
    }

//    System.out.println(Arrays.toString(X));
//    System.out.println(Arrays.toString(Y));
  }

  @Test
  public void testKmeansConstructor() {

    //String filePath = "./src/data/clusterdata-3.txt";
    String filePath = "./src/data/k_8.txt";

    double[] X = HelperUtils.getX(filePath);
    double[] Y = HelperUtils.getY(filePath);

    Kmeans kmeans = new Kmeans(8, X, Y);
    kmeans.run();
    kmeans.drawOutput("kmeans.png");
  }

  @Test
  public void testKmeansRANSAC() {
    int k = 3;
    int maxIterTimes = 10;
    String filePath = "./src/data/clusterdata-3.txt";
    Kmeans bestModel = Kmeans.runRANSAC(k, maxIterTimes, filePath);
    bestModel.drawOutput("RANSAC.png");
  }
}
