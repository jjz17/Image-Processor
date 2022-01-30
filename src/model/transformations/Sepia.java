package model.transformations;

import model.image.IPixel;
import model.image.RGBPixel;

/** Represents a color transformation which has the effect of converting images to a sepia tone. */
public class Sepia extends AColorTransformation {

  // applies the transformation to the given pixel
  @Override
  protected IPixel applyToPixel(IPixel pixel) {
    int red = pixel.getRedVal();
    int green = pixel.getGreenVal();
    int blue = pixel.getBlueVal();

    int newRedVal = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
    int newGreenVal = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
    int newBlueVal = (int) (0.272 * red + 0.534 * green + 0.131 * blue);
    return new RGBPixel(newRedVal, newGreenVal, newBlueVal);
  }
}
