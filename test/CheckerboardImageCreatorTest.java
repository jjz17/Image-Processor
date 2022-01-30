import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;
import model.imagecreator.CheckerboardImageCreator;

import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * Test class for CheckerboardImageCreator: unit tests to ensure that the methods on
 * CheckerboardImageCreator behave correctly.
 */
public class CheckerboardImageCreatorTest {
  private final IPixel blackPixel = new RGBPixel(0, 0, 0);
  private final IPixel whitePixel = new RGBPixel(255, 255, 255);
  private final IPixel[][] image =
      new IPixel[][] {
        {this.blackPixel, this.blackPixel, this.whitePixel, this.whitePixel},
        {this.blackPixel, this.blackPixel, this.whitePixel, this.whitePixel},
        {this.whitePixel, this.whitePixel, this.blackPixel, this.blackPixel},
        {this.whitePixel, this.whitePixel, this.blackPixel, this.blackPixel}
      };
  private final IImage checkerboardImage = new RGBImage(this.image);

  private final IPixel redPixel = new RGBPixel(255, 0, 0);
  private final IPixel bluePixel = new RGBPixel(0, 0, 255);
  private final IPixel[][] image2 =
      new IPixel[][] {
        {this.redPixel, this.bluePixel, this.redPixel},
        {this.bluePixel, this.redPixel, this.bluePixel}
      };
  private final IImage rectCheckerboardImage = new RGBImage(this.image2);

  // tests proper behavior of the create method
  @Test
  public void testCreateSquare() {
    assertEquals(
        this.checkerboardImage, CheckerboardImageCreator.create(2, 2, 2, Color.BLACK, Color.WHITE));
  }

  // tests proper behavior of the create method
  @Test
  public void testCreateRect() {
    assertEquals(
        this.rectCheckerboardImage,
        CheckerboardImageCreator.create(1, 2, 3, Color.RED, Color.BLUE));
  }

  // tests invalid tileSize input
  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidInput() {
    CheckerboardImageCreator.create(0, 2, 2, Color.BLACK, Color.WHITE);
  }

  // tests invalid numTilesWidth input
  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidInput2() {
    CheckerboardImageCreator.create(2, -1, 2, Color.BLACK, Color.WHITE);
  }

  // tests invalid numTilesHeight input
  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidInput3() {
    CheckerboardImageCreator.create(2, 2, -10, Color.BLACK, Color.WHITE);
  }

  // tests null color input
  @Test(expected = NullPointerException.class)
  public void testCreateNullInput() {
    CheckerboardImageCreator.create(2, 2, 2, null, Color.WHITE);
  }

  // tests null color input
  @Test(expected = NullPointerException.class)
  public void testCreateNullInput2() {
    CheckerboardImageCreator.create(2, 2, 2, Color.BLACK, null);
  }
}
