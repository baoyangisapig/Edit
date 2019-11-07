/**
 * Created by ashesh on 2/19/2017.
 */

import java.io.IOException;

/**
 * Created by ashesh on 2/16/2017.
 */
public class PlotterExample {

  /**
   * Main function of an example to plotter.
   *
   * @param args Args of the main function.
   */
  public static void main(String[] args) {
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);

    plotter.setDimensions(-300, 300, -350, 350);
    for (int x = -200; x < 200; x += 20) {
      for (int y = 0; y <= x; y += 20) {
        plotter.addCircle(x, y, 10);
        plotter.addPoint(x, y);
        plotter.addLine(x, y, x + 20, y);
        plotter.addLine(x, y, x, y + 20);
      }
    }

    try {
      plotter.write("example.png");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
