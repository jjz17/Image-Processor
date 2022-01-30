package model.image;

/** Represents a pixel which is used to compose images. */
public interface IPixel {

  /**
   * Finds the red value of this pixel.
   *
   * @return the red value of this pixel
   */
  int getRedVal();

  /**
   * Finds the green value of this pixel.
   *
   * @return the green value of this pixel
   */
  int getGreenVal();

  /**
   * Finds the blue value of this pixel.
   *
   * @return the blue value of this pixel
   */
  int getBlueVal();

  /**
   * Returns the maximum value of a channel in this pixel.
   *
   * @return the maximum value of a channel in this pixel
   */
  int getMaxVal();

  @Override
  String toString();

  @Override
  boolean equals(Object other);

  @Override
  int hashCode();
}
