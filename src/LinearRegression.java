import java.io.IOException;

/**
 * The algorithm to simulate linearRegression.
 *
 * @Author Yang Bao&YiMing Chu
 */

public class LinearRegression {
  final double X1 = -400;
  final double X2 = 400;
  private double p1;
  private double p2;
  private double p3;
  final int Width = 400;
  final int Height = 400;
  final int xmin = -300;
  final int xmax = 300;
  final int ymin = -350;
  final int ymax = 350;
  private double[] oX;
  private double[] oY;

  /**
   * The Constructor for LinearRegression.
   *
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public LinearRegression(double[] x, double[] y) {
    this.oX = x;
    this.oY = y;
    if (x.length != y.length) {
      throw new IllegalArgumentException("the length is not equal");
    }
    int len = x.length;
    double sum_x = 0.0;
    double sum_y = 0.0;
    for (int i = 0; i < len; i++) {
      sum_x += x[i];
      sum_y += y[i];
    }
    double aver_x = sum_x / len;
    double aver_y = sum_y / len;
    double f1 = 0.0;
    double f2 = 0.0;
    double f3 = 0.0;
    for (int i = 0; i < len; i++) {
      f1 += (x[i] - aver_x) * (x[i] - aver_x);
      f2 += (y[i] - aver_y) * (y[i] - aver_y);
      f3 += (x[i] - aver_x) * (y[i] - aver_y);
    }
    double d = 2 * f3 / (f1 - f2);
    double t = Math.atan(d);
    double f = (f2 - f1) * Math.cos(t) - 2 * f3 * Math.sin(t);
    t = f <= 0 ? t + Math.toRadians(180.0) : t;
    p1 = Math.cos(t / 2);
    p2 = Math.sin(t / 2);
    p3 = -p1 * aver_x - p2 * aver_y;
  }

  /**
   * the function to draw the image.
   *
   * @param outputPath the path of dataset.
   */
  public void drawOutput(String outputPath) {
    double[] point1 = new double[]{X1, (-p3 - p1 * X1) / p2};
    double[] point2 = new double[]{X2, (-p3 - p1 * X2) / p2};
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(Width);
    plotter.setHeight(Height);
    plotter.setDimensions(xmin, xmax, ymin, ymax);
    for (int i = 0; i < oX.length; i++) {
      plotter.addPoint((int) oX[i], (int) oY[i]);
    }
    plotter.addLine((int) point1[0], (int) point1[1], (int) point2[0], (int) point2[1]);
    try {
      plotter.write(outputPath);
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }
}