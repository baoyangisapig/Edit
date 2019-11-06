import java.io.IOException;

/**
 * @program: Assignment6
 * @description:
 * @author: Nan Sun
 * @create: 2019-10-29 17:55
 **/

public class LinearRegression {

  private double a;
  private double b;
  private double c;

  private double[] X;
  private double[] Y;

  /**
   * Performs a linear regression on the data points {@code (y[i], x[i])}.
   *
   * @param x the values of the predictor variable
   * @param y the corresponding values of the response variable
   */
  public LinearRegression(double[] x, double[] y) {
    // Initialize the data
    this.X = x;
    this.Y = y;

    if (x.length != y.length) {
      throw new IllegalArgumentException("array lengths are not equal");
    }
    int len = x.length;

    // first pass
    double sumx = 0.0, sumy = 0.0;
    for (int i = 0; i < len; i++) {
      sumx += x[i];
      sumy += y[i];
    }
    double xbar = sumx / len;
    double ybar = sumy / len;

    // second pass: compute summary statistics
    double Sxx = 0.0, Syy = 0.0, Sxy = 0.0;
    for (int i = 0; i < len; i++) {
      Sxx += (x[i] - xbar) * (x[i] - xbar);
      Syy += (y[i] - ybar) * (y[i] - ybar);
      Sxy += (x[i] - xbar) * (y[i] - ybar);
    }

    // third pass: calculate d
    double d = 2 * Sxy / (Sxx - Syy);

    // fourth pass: calculate theta
    // iteye.com/blog/elingwange-1550707
    double t = Math.atan(d);

    // fifth pass: make f(t) positive
    // 𝑓(𝑡)=(𝑠𝑦𝑦−𝑠𝑥𝑥)∗𝑐𝑜𝑠(𝑡)−2∗𝑠𝑥𝑦∗𝑠𝑖𝑛(𝑡)
    double f = (Syy - Sxx) * Math.cos(t) - 2 * Sxy * Math.sin(t);
    if (f <= 0) {
      t += Math.toRadians(180.0);
    }

    // sixth pass:
    a = Math.cos(t / 2);
    b = Math.sin(t / 2);
    c = -a * xbar - b * ybar;

  }

  public double[] getPara() {
    return new double[]{a, b, c};
  }

  /**
   * Output drawing function.
   *
   * @param outputPath the file path of the output path.
   */
  public void drawOutput(String outputPath) {
    double givenX1 = -300;
    double givenX2 = 300;
    double[] point1 = new double[]{givenX1, (-c - a * givenX1) / b};
    double[] point2 = new double[]{givenX2, (-c - a * givenX2) / b};
    // Set the graph size
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);
    plotter.setDimensions(-300, 300, -350, 350);
    // Add data set points
    for (int i = 0; i < X.length; i++) {
      plotter.addPoint((int) X[i], (int) Y[i]);
    }
    // Add the fitted line
    plotter.addLine((int) point1[0], (int) point1[1], (int) point2[0], (int) point2[1]);
    try {
      plotter.write(outputPath);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}