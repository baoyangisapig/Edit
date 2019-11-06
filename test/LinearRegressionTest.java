import org.junit.Test;

public class LinearRegressionTest {
  @Test
  public void testLR() {
    String filePath = "./src/data/linedata-6.txt";

    double[] X = HelperUtils.getX(filePath);
    double[] Y = HelperUtils.getY(filePath);

    LinearRegression lr = new LinearRegression(X, Y);
    lr.drawOutput("lR.png");
  }
}