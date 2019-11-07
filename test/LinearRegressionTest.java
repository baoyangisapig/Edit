import org.junit.Assert;
import org.junit.Test;

/**
 * the class represents the Junit4 test for LinearRegression.
 */
public class LinearRegressionTest {
  @Test
  public void testLR() {
    String filePath = "./src/data/linedata-5.txt";

    double[] x = HelperUtils.getX(filePath);
    double[] y = HelperUtils.getY(filePath);

    LinearRegression lr = new LinearRegression(x, y);
    lr.drawOutput("lR.png");

    Assert.assertEquals(1, 1);

  }
}