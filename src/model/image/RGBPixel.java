package model.image;

/** Represents a pixel which uses the RGB standard to specify its color. */
public class RGBPixel implements IPixel {
  private final int maxVal;
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a RGBPixel object with the provided RGB values, clamping if necessary.
   *
   * @param red the given red value
   * @param green the given green value
   * @param blue the given blue value
   */
  public RGBPixel(int red, int green, int blue) {
    this.maxVal = 255;
    this.red = clampCheck(red);
    this.green = clampCheck(green);
    this.blue = clampCheck(blue);
  }

  /**
   * Clamps the input value between 0 and 255.
   *
   * @param val the value to be processed
   * @return the value, clamped if necessary
   */
  protected static int clampCheck(int val) {
    if (val < 0) {
      return 0;
    } else if (val > 255) {
      return 255;
    }
    return val;
  }

  @Override
  public int getRedVal() {
    return this.red;
  }

  @Override
  public int getGreenVal() {
    return this.green;
  }

  @Override
  public int getBlueVal() {
    return this.blue;
  }

  @Override
  public int getMaxVal() {
    return this.maxVal;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("(RGBPixel: ");
    result.append(this.red);
    result.append(", ");
    result.append(this.green);
    result.append(", ");
    result.append(this.blue);
    result.append(")");
    return result.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RGBPixel)) {
      return false;
    }
    RGBPixel that = (RGBPixel) other;
    return this.red == that.red && this.green == that.green && this.blue == that.blue;
  }

  @Override
  public int hashCode() {
    return this.red * 101 + this.green * 29 + this.blue * 79;
  }
}
