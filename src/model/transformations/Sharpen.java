package model.transformations;

/** Represents a transformation which has the effect of sharpening images. */
public class Sharpen extends AFilter {

  /**
   * Default constructor that creates an instance of a sharpen filter, with the appropriate kernel
   * and size.
   */
  public Sharpen() {
    filter =
        new double[][] {
          {-0.0625, -0.0625, -0.0625, -0.0625, -0.0625},
          {-0.0625, 0.25, 0.25, 0.25, -0.0625},
          {-0.0625, 0.25, 1.0, 0.25, -0.0625},
          {-0.0625, 0.25, 0.25, 0.25, -0.0625},
          {-0.0625, -0.0625, -0.0625, -0.0625, -0.0625}
        };
    filterSize = this.filter.length;
  }
}
