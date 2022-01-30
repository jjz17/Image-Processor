package model.transformations;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;

/** Represents a transformation which has the effect of converting images to a mosaic. */
public class Mosaic implements ITransformation {
  private final int numSeeds;

  public Mosaic(int numSeeds) {
    this.numSeeds = numSeeds;
  }

  @Override
  public IImage apply(IImage image) throws NullPointerException {
    Objects.requireNonNull(image, "Can't mosaic null image");
    HashMap<Integer, HashMap<Point, IPixel>> groups =
        new HashMap<Integer, HashMap<Point, IPixel>>();
    ArrayList<Point> seeds = new ArrayList<Point>();
    ArrayList<IPixel> averageColors = new ArrayList<IPixel>();

    // Initialize all seeds, initialize group keys
    for (int i = 0; i < numSeeds; i++) {
      seeds.add(generateRandomPoints(image.getHeight(), image.getWidth()));
      groups.put(i, new HashMap<Point, IPixel>());
    }

    // Places all Points and IPixels within their respect closest seed groups
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        int closestSeedIndex = findClosestPointIndex(new Point(i, j), seeds);
        HashMap<Point, IPixel> temp = groups.get(closestSeedIndex);
        temp.put(new Point(i, j), image.getPixelAt(i, j));
        groups.put(closestSeedIndex, temp);
      }
    }

    // Find average value for each group
    for (Map.Entry<Integer, HashMap<Point, IPixel>> entry : groups.entrySet()) {
      int totalRed = 0;
      int totalGreen = 0;
      int totalBlue = 0;
      for (Map.Entry<Point, IPixel> subEntry : entry.getValue().entrySet()) {
        totalRed = totalRed + subEntry.getValue().getRedVal();
        totalGreen = totalGreen + subEntry.getValue().getGreenVal();
        totalBlue = totalBlue + subEntry.getValue().getBlueVal();
      }

      averageColors.add(
          new RGBPixel(
              totalRed / ifZeroEqualsOne(entry.getValue().size()),
              totalGreen / ifZeroEqualsOne(entry.getValue().size()),
              totalBlue / ifZeroEqualsOne(entry.getValue().size())));
    }

    // Change IPixel of all points
    for (Map.Entry<Integer, HashMap<Point, IPixel>> entry : groups.entrySet()) {
      IPixel temp = averageColors.get(entry.getKey());
      for (Map.Entry<Point, IPixel> subEntry : entry.getValue().entrySet()) {
        subEntry.setValue(temp);
      }
    }

    // Create RGBImage
    IPixel[][] result = new IPixel[image.getWidth()][image.getHeight()];
    for (Map.Entry<Integer, HashMap<Point, IPixel>> entry : groups.entrySet()) {
      for (Map.Entry<Point, IPixel> subEntry : entry.getValue().entrySet()) {
        result[subEntry.getKey().x][subEntry.getKey().y] = subEntry.getValue();
      }
    }

    return new RGBImage(result);
  }

  protected Point generateRandomPoints(int height, int width) {
    Random random = new Random();
    int x = random.nextInt(width);
    int y = random.nextInt(height);
    return new Point(x, y);
  }

  protected int findClosestPointIndex(Point2D point, ArrayList<Point> seeds) {
    ArrayList<Integer> distances = new ArrayList<Integer>();
    for (int i = 0; i < seeds.size(); i++) {
      distances.add((int) point.distance(seeds.get(i)));
    }
    return distances.indexOf(Collections.min(distances));
  }

  protected int ifZeroEqualsOne(int num) {
    if (num == 0) {
      return 1;
    }
    return num;
  }
}
