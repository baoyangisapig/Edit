import org.junit.Test;

/**
 * the class represents the Junit4 test for LinearRegression.
 */
public class LinearRegressionTest {
  @Test
  public void testLR() {
    String filePath = "./src/data/linedata-5.txt";

    double[] X = HelperUtils.getX(filePath);
    double[] Y = HelperUtils.getY(filePath);

    LinearRegression lr = new LinearRegression(X, Y);
    lr.drawOutput("lR.png");
  }
}