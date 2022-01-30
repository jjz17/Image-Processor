package model.transformations;

import model.image.IPixel;
import model.image.RGBPixel;

/**
 * Represents a color transformation which has the effect of converting images to a monochrome tone.
 */
public class Monochrome extends AColorTransformation {

  // applies the transformation to the given pixel
  @Override
  protected IPixel applyToPixel(IPixel pixel) {
    int red = pixel.getRedVal();
    int green = pixel.getGreenVal();
    int blue = pixel.getBlueVal();

    int newRGBVal = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
    return new RGBPixel(newRGBVal, newRGBVal, newRGBVal);
  }
}
