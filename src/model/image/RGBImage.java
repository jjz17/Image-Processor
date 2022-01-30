package model.image;

/** Represents an image which is composed of {@code model.image.RGBPixel} objects. */
public class RGBImage implements IImage {
  private final IPixel[][] image;
  private final int maxValue;
  private boolean visible;

  /**
   * Constructs an image based off of the given 2-d array of IPixels.
   *
   * @param image the image to be created
   */
  public RGBImage(IPixel[][] image) {
    this.image = image;
    this.maxValue = image[0][0].getMaxVal();
    this.visible = true;
  }

  @Override
  public int getWidth() {
    return this.image.length;
  }

  @Override
  public int getHeight() {
    return this.image[0].length;
  }

  @Override
  public int getMaxVal() {
    return this.maxValue;
  }

  @Override
  public IPixel getPixelAt(int column, int row) throws IllegalArgumentException {
    if (column < 0 || column >= this.getWidth()) {
      throw new IllegalArgumentException("Column is out of bounds");
    }
    if (row < 0 || row >= this.getHeight()) {
      throw new IllegalArgumentException("Row is out of bounds.");
    }
    return this.image[column][row];
  }

  @Override
  public boolean isVisible() {
    return this.visible;
  }

  @Override
  public void makeVisible() {
    this.visible = true;
  }

  @Override
  public void makeInvisible() {
    this.visible = false;
  }

  @Override
  public IImage makeCopy() {
    IPixel[][] copy = new RGBPixel[this.getWidth()][this.getHeight()];
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        copy[i][j] = this.getPixelAt(i, j);
      }
    }
    return new RGBImage(copy);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        result.append(this.image[j][i].toString());
        result.append(" ");
      }
      result.append("\n");
    }
    return result.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RGBImage)) {
      return false;
    }
    RGBImage that = (RGBImage) other;

    if (this.getHeight() != that.getHeight() || this.getWidth() != that.getWidth()) {
      return false;
    }

    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        if (!this.image[i][j].equals(that.image[i][j])) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hashcode = 0;
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        hashcode += this.image[i][j].hashCode();
      }
    }
    return hashcode;
  }
}
