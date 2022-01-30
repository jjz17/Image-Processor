package model.transformations;

import java.util.Objects;
import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;

/** Abstract base class for implementations of {@link ITransformation} dealing with filters. */
abstract class AFilter implements ITransformation {
  protected double[][] filter;
  protected int filterSize;

  protected AFilter() {
    this.filter = new double[][]{};
    this.filterSize = 0;
  }

  @Override
  public IImage apply(IImage image) {
    Objects.requireNonNull(image, "Can't filter null image");

    IPixel[][] result = new IPixel[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        result[i][j] = this.applyToPixel(image, i, j);
      }
    }

    IImage transformed = new RGBImage(result);

    if (!image.isVisible()) {
      transformed.makeInvisible();
    }

    return transformed;
  }

  protected IPixel applyToPixel(IImage image, int column, int row) {
    IPixel temp;
    double newRed = 0;
    double newGreen = 0;
    double newBlue = 0;

    int deficit = (filterSize - 1) / 2;
    for (int i = 0; i < filterSize; i++) {
      for (int j = 0; j < filterSize; j++) {
        try {
          temp = image.getPixelAt(column - deficit + i, row - deficit + j);
          newRed += filter[i][j] * (double) temp.getRedVal();
          newGreen += filter[i][j] * (double) temp.getGreenVal();
          newBlue += filter[i][j] * (double) temp.getBlueVal();
        } catch (IllegalArgumentException e) {
          // Nothing occurs
          newRed += 0;
          newGreen += 0;
          newBlue += 0;
        }
      }
    }
    return new RGBPixel((int) newRed, (int) newGreen, (int) newBlue);
  }
}
