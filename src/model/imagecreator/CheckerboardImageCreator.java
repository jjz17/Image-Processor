package model.imagecreator;

import java.awt.Color;
import java.util.Objects;

import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;

/**
 * Represents a function to create square checkerboards when given the pixel size of each tile, the
 * number of tiles in a side, and the 2 colors that the titles are to be colored.
 */
public class CheckerboardImageCreator {

  /**
   * Creates an IImage of a specified tile size, dimensions, and color.
   *
   * @param tileSize the size of the tiles
   * @param numTilesWidth the number of the tiles for the width
   * @param numTilesHeight the number of the tiles for the height
   * @param color1 the first color to be used
   * @param color2 the second color to be used
   * @return the newly generated image
   * @throws IllegalArgumentException if any of the int arguments are not positive
   * @throws NullPointerException if either of the input colors are null
   */
  public static IImage create(
      int tileSize, int numTilesWidth, int numTilesHeight, Color color1, Color color2)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(color1);
    Objects.requireNonNull(color2);
    if (tileSize <= 0 || numTilesWidth <= 0 || numTilesHeight <= 0) {
      throw new IllegalArgumentException("Illegal size, must be positive.");
    }

    int boardSizeWidth = tileSize * numTilesWidth;
    int boardSizeHeight = tileSize * numTilesHeight;
    IPixel[][] image = new IPixel[boardSizeWidth][boardSizeHeight];
    boolean useVerticalColor1 = true;
    boolean useColor1 = true;
    int verticalPixelsColored = 0;
    int horizontalPixelsColored = 0;
    Color startingVerticalColor = color1;
    Color currentColor = color1;

    for (int i = 0; i < boardSizeWidth; i++) {

      if (horizontalPixelsColored >= tileSize) {
        useVerticalColor1 = !useVerticalColor1;
        horizontalPixelsColored = 0;
      }

      if (useColor1) {
        startingVerticalColor = color1;
      } else {
        currentColor = color2;
      }

      horizontalPixelsColored++;

      useColor1 = useVerticalColor1;
      verticalPixelsColored = 0;
      currentColor = startingVerticalColor;

      for (int j = 0; j < boardSizeHeight; j++) {
        if (verticalPixelsColored >= tileSize) {
          useColor1 = !useColor1;
          verticalPixelsColored = 0;
        }

        if (useColor1) {
          currentColor = color1;
        } else {
          currentColor = color2;
        }

        image[i][j] =
            new RGBPixel(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
        verticalPixelsColored++;
      }
    }

    return new RGBImage(image);
  }
}
