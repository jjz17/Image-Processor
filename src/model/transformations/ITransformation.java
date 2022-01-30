package model.transformations;

import model.image.IImage;

/**
 * Represents a transformation which can be applied to images to change their characteristics and
 * attributes.
 */
public interface ITransformation {

  /**
   * Applies the transformation to the given image.
   *
   * @param image the image to be transformed
   * @return the transformed version of the image
   * @throws NullPointerException if the input image is null
   */
  IImage apply(IImage image) throws NullPointerException;
}
