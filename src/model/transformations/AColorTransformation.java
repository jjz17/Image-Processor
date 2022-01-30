package model.transformations;

import java.util.Objects;
import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;

/**
 * Abstract base class for implementations of {@link ITransformation} dealing with color
 * transformations.
 */
public abstract class AColorTransformation implements ITransformation {

  @Override
  public IImage apply(IImage image) {
    Objects.requireNonNull(image, "Can't color transform null image");

    IPixel[][] result = new IPixel[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        result[i][j] = this.applyToPixel(image.getPixelAt(i, j));
      }
    }

    IImage transformed = new RGBImage(result);

    if (!image.isVisible()) {
      transformed.makeInvisible();
    }

    return transformed;
  }

  /**
   * Applies a color transformation to the given pixel.
   *
   * @param pixel the pixel to be color transformed
   * @return the color transformed version of the pixel
   */
  abstract IPixel applyToPixel(IPixel pixel);
}
