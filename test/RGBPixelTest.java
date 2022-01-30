import org.junit.Test;

import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;

import static org.junit.Assert.assertEquals;

/** Test class for RGBPixel: unit tests to ensure that the methods on RGBPixel behave correctly. */
public class RGBPixelTest {
  private IPixel pixel1 = new RGBPixel(0, 0, 0);
  private IPixel tooLowPixel = new RGBPixel(-10, -17, -30);
  private IPixel pixel2 = new RGBPixel(255, 255, 255);
  private IPixel tooHighPixel = new RGBPixel(1000, 567, 99999);
  private IPixel pixel3 = new RGBPixel(194, 58, 237);

  // tests proper behavior of the clamping mechanism of the constructor
  @Test
  public void testConstructorClamp() {
    assertEquals(true, this.pixel1.equals(this.tooLowPixel));
    assertEquals(true, this.pixel2.equals(this.tooHighPixel));
  }

  // tests proper behavior of the getRedVal method
  @Test
  public void testGetRedVal() {
    assertEquals(0, this.pixel1.getRedVal());
    assertEquals(0, this.tooLowPixel.getRedVal());
    assertEquals(255, this.pixel2.getRedVal());
    assertEquals(255, this.tooHighPixel.getRedVal());
    assertEquals(194, this.pixel3.getRedVal());
  }

  // tests proper behavior of the getRedVal method
  @Test
  public void testGetGreenVal() {
    assertEquals(0, this.pixel1.getGreenVal());
    assertEquals(0, this.tooLowPixel.getGreenVal());
    assertEquals(255, this.pixel2.getGreenVal());
    assertEquals(255, this.tooHighPixel.getGreenVal());
    assertEquals(58, this.pixel3.getGreenVal());
  }

  // tests proper behavior of the getRedVal method
  @Test
  public void testGetBlueVal() {
    assertEquals(0, this.pixel1.getBlueVal());
    assertEquals(0, this.tooLowPixel.getBlueVal());
    assertEquals(255, this.pixel2.getBlueVal());
    assertEquals(255, this.tooHighPixel.getBlueVal());
    assertEquals(237, this.pixel3.getBlueVal());
  }

  // tests the proper behavior of the getMaxVal method
  @Test
  public void testGetMaxVal() {
    assertEquals(255, this.pixel1.getMaxVal());
    assertEquals(255, this.tooLowPixel.getMaxVal());
    assertEquals(255, this.pixel2.getMaxVal());
    assertEquals(255, this.tooHighPixel.getMaxVal());
    assertEquals(255, this.pixel3.getMaxVal());
  }

  // tests proper behavior of the toString method
  @Test
  public void testToString() {
    assertEquals("(RGBPixel: 0, 0, 0)", this.pixel1.toString());
    assertEquals("(RGBPixel: 255, 255, 255)", this.tooHighPixel.toString());
    assertEquals("(RGBPixel: 194, 58, 237)", this.pixel3.toString());
  }

  // tests proper behavior of the equals method
  @Test
  public void testEquals() {
    assertEquals(true, this.pixel1.equals(this.tooLowPixel));
    assertEquals(true, this.pixel2.equals(this.tooHighPixel));
    assertEquals(false, this.pixel1.equals(this.pixel3));
    assertEquals(false, this.pixel1.equals(new RGBImage(new IPixel[][] {{this.pixel1}})));
  }

  // tests proper behavior of the hashcode method
  @Test
  public void testHashcode() {
    assertEquals(true, this.pixel1.hashCode() == this.tooLowPixel.hashCode());
    assertEquals(true, this.pixel2.hashCode() == this.tooHighPixel.hashCode());
    assertEquals(false, this.pixel3.hashCode() == this.pixel1.hashCode());
  }
}
