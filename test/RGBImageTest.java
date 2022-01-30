import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;
import model.imagecreator.CheckerboardImageCreator;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

/** Test class for RGBImage: unit tests to ensure that the methods on RGBImage behave correctly. */
public class RGBImageTest {
  // pixel21 is pixel at row 2, column 1
  private final IPixel pixel11 = new RGBPixel(1, 1, 1);
  private final IPixel pixel21 = new RGBPixel(0, 0, 0);
  private final IPixel pixel12 = new RGBPixel(2, 17, 56);
  private final IPixel pixel22 = new RGBPixel(0, 0, 100);

  private final IPixel[][] imageArray1 = {
      {this.pixel11, this.pixel21}, {this.pixel12, this.pixel22}
  };

  private IImage image1;
  private final IImage checkerboardImage =
      CheckerboardImageCreator.create(3, 3, 3, Color.BLACK, Color.WHITE);

  @Before
  public void init() {
    this.image1 = new RGBImage(this.imageArray1);
  }

  // tests proper behavior of the getWidth method
  @Test
  public void testGetWidth() {
    assertEquals(2, this.image1.getWidth());
    assertEquals(9, this.checkerboardImage.getWidth());
  }

  // tests proper behavior of the getHeight method
  @Test
  public void testGetHeight() {
    assertEquals(2, this.image1.getHeight());
    assertEquals(9, this.checkerboardImage.getHeight());
  }

  // tests proper behavior of the getMaxVal method
  @Test
  public void testGetMaxVal() {
    assertEquals(255, this.image1.getMaxVal());
    assertEquals(255, this.checkerboardImage.getMaxVal());
  }

  // tests proper behavior of the getPixelAt method
  @Test
  public void testGetPixelAt() {
    assertEquals(new RGBPixel(1, 1, 1), this.image1.getPixelAt(0, 0));
    assertEquals(new RGBPixel(0, 0, 0), this.checkerboardImage.getPixelAt(0, 0));
    assertEquals(new RGBPixel(255, 255, 255), this.checkerboardImage.getPixelAt(4, 2));
  }

  // tests invalid column input
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelInvalidColumn() {
    this.image1.getPixelAt(-1, 1);
  }

  // tests invalid row input
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelInvalidRow() {
    this.image1.getPixelAt(0, -5);
  }

  // tests proper behavior of the isVisible method
  @Test
  public void testIsVisible() {
    assertEquals(true, this.image1.isVisible());
    this.image1.makeInvisible();
    assertEquals(false, this.image1.isVisible());
  }

  // tests proper behavior of the makeVisible method
  @Test
  public void testMakeVisible() {
    assertEquals(true, this.image1.isVisible());
    this.image1.makeInvisible();
    assertEquals(false, this.image1.isVisible());
    this.image1.makeVisible();
    assertEquals(true, this.image1.isVisible());
  }

  // tests proper behavior of the makeInvisible method
  @Test
  public void testMakeInvisible() {
    assertEquals(true, this.image1.isVisible());
    this.image1.makeInvisible();
    assertEquals(false, this.image1.isVisible());
  }

  // tests proper behavior of the toString method
  @Test
  public void testToString() {
    assertEquals(
        "(RGBPixel: 1, 1, 1) (RGBPixel: 2, 17, 56) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 100) \n",
        this.image1.toString());
    assertEquals(
        "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 0, 0, 0) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 0, 0, 0) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 0, 0, 0) \n"
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) \n"
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) \n"
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) \n"
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) "
            + "(RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) (RGBPixel: 255, 255, 255) "
            + "(RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) (RGBPixel: 0, 0, 0) \n",
        this.checkerboardImage.toString());
  }

  // tests proper behavior of the equals method
  @Test
  public void testEquals() {
    assertEquals(
        true,
        this.checkerboardImage.equals(
            CheckerboardImageCreator.create(3, 3, 3, Color.BLACK, Color.WHITE)));
    assertEquals(false, this.image1.equals(this.checkerboardImage));
  }

  // tests proper behavior of the hashcode method
  @Test
  public void testHashcode() {
    assertEquals(
        true,
        this.checkerboardImage.hashCode()
            == CheckerboardImageCreator.create(3, 3, 3, Color.BLACK, Color.WHITE).hashCode());
    assertEquals(false, this.image1.hashCode() == this.checkerboardImage.hashCode());
  }
}
