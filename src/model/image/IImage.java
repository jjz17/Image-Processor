package model.image;

/**
 * Represents an image which is composed of {@code model.image.IPixel} objects. It can have filters
 * and color transformations applied to change its pixel colors.
 */
public interface IImage {

  /**
   * Finds the width of this image.
   *
   * @return the width of this image.
   */
  int getWidth();

  /**
   * Finds the height of this image.
   *
   * @return the height of this image.
   */
  int getHeight();

  /**
   * Returns the maximum value of a pixel channel in this image.
   *
   * @return the maximum value of a pixel channel in this image
   */
  int getMaxVal();

  /**
   * Gets the pixel at the provided location in the image.
   *
   * @param column the column location
   * @param row the row location
   * @return the pixel at the provided location
   * @throws IllegalArgumentException if either argument is out of bounds (less than 0 or greater
   *     than the image dimensions)
   */
  IPixel getPixelAt(int column, int row) throws IllegalArgumentException;

  /**
   * Checks if the image is visible.
   *
   * @return true if the image is visible, false otherwise
   */
  boolean isVisible();

  /**
   * Toggles the image visibility to true.
   */
  void makeVisible();

  /**
   * Toggles the image visibility to false.
   */
  void makeInvisible();

  /**
   * Returns a copy of this image.
   *
   * @return a copy of this image
   */
  IImage makeCopy();

  @Override
  String toString();

  @Override
  boolean equals(Object other);

  @Override
  int hashCode();
}
