package model.transformations;

/** Represents a transformation which has the effect of sharpening images. */
public class Blur extends AFilter {

  /**
   * Default constructor that creates an instance of a blur filter, with the appropriate kernel and
   * size.
   */
  public Blur() {
    filter =
        new double[][] {
          {(double) 0.0625, (double) 0.125, (double) 0.0625},
          {(double) 0.125, (double) 0.25, (double) 0.125},
          {(double) 0.0625, (double) 0.125, (double) 0.0625}
        };
    filterSize = this.filter.length;
    /*
    1st  2nd 3rd
    1/16 1/8 1/16
    1/8  1/4 1/8
    1/16 1/8 1/16
     */

  }
}
