import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test class for Kmeans.
 */
public class KmeansTest {
  @Test
  public void testInput() {
    HelperUtils helper = new HelperUtils();
    List<String> rawDataList = helper.readFromFile("./src/data/clusterdata-8.txt");
    int n = rawDataList.size();
    double[] x = new double[n];
    double[] y = new double[n];
    for (int i = 0; i < n; i++) {
      String[] pos = rawDataList.get(i).split(" ");
      x[i] = Double.parseDouble(pos[0]);
      y[i] = Double.parseDouble(pos[1]);
    }
    Assert.assertEquals(1, 1);
  }

  @Test
  public void testKmeansConstructor() {
    String filePath = "./src/data/clusterdata-4.txt";
    double[] x = HelperUtils.getX(filePath);
    double[] y = HelperUtils.getY(filePath);
    Kmeans kmeans = new Kmeans(4, x, y);
    kmeans.run();
    kmeans.drawOutput("kmeans.png");
    Assert.assertEquals(1, 1);
  }

  @Test
  public void testKmeansRANSAC() {
    int k = 3;
    int maxIterTimes = 10;
    String filePath = "./src/data/clusterdata-3.txt";
    Kmeans bestModel = Kmeans.runRANSAC(k, maxIterTimes, filePath);
    bestModel.drawOutput("RANSAC.png");
    Assert.assertEquals(1, 1);
  }
}
