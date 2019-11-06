import org.junit.Test;

public class LinearRegressionTest {
  @Test
  public void testLR() {
    String filePath = "./src/data/L.txt";

    double[] X = Util.getX(filePath);
    double[] Y = Util.getY(filePath);

    LinearRegression lr = new LinearRegression(X, Y);
    lr.drawOutput("lR.png");
  }
}