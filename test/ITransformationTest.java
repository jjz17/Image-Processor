import model.transformations.Mosaic;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;
import model.imagecreator.CheckerboardImageCreator;
import model.transformations.Blur;
import model.transformations.ITransformation;
import model.transformations.Monochrome;
import model.transformations.Sepia;
import model.transformations.Sharpen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Test class for all implementations of ITransformation.
 */
public class ITransformationTest {
  private final ITransformation blur = new Blur();
  private final ITransformation sharpen = new Sharpen();
  private final ITransformation monochrome = new Monochrome();
  private final ITransformation sepia = new Sepia();
  private final ITransformation mosaic = new Mosaic(30);
  private IImage check;

  @Before
  public void init() {
    this.check = CheckerboardImageCreator.create(1, 2, 2, Color.BLACK, Color.WHITE);
  }

  // tests the proper behavior of blur



  @Test
  public void testBlur() {
    IPixel[][] blurred =
        new IPixel[][] {
            {new RGBPixel(63, 63, 63), new RGBPixel(79, 79, 79)},
            {new RGBPixel(79, 79, 79), new RGBPixel(63, 63, 63)}
        };
    IImage blurCheck = new RGBImage(blurred);
    assertEquals(blurCheck, this.blur.apply(this.check));
  }

  // tests the proper behavior of sharpen
  @Test
  public void testSharpen() {
    IPixel[][] sharpened =
        new IPixel[][] {
            {new RGBPixel( 127, 127, 127), new RGBPixel( 255, 255, 255)},
            {new RGBPixel( 255, 255, 255), new RGBPixel( 127, 127, 127)}
        };
    IImage sharpenCheck = new RGBImage(sharpened);
    assertEquals(sharpenCheck, this.sharpen.apply(this.check));
  }

  // tests the proper behavior of monochrome
  @Test
  public void testMonochrome() {
    IPixel[][] monochromed =
        new IPixel[][] {
            {new RGBPixel( 0, 0, 0), new RGBPixel( 254, 254, 254)},
            {new RGBPixel( 254, 254, 254), new RGBPixel( 0, 0, 0)}
        };
    IImage monochromeCheck = new RGBImage(monochromed);
    assertEquals(monochromeCheck, this.monochrome.apply(this.check));
  }

  // tests the proper behavior of sepia
  @Test
  public void testSepia() {
    IPixel[][] sepiaed =
        new IPixel[][] {
            {new RGBPixel( 0, 0, 0), new RGBPixel( 255, 255, 238)},
            {new RGBPixel( 255, 255, 238), new RGBPixel( 0, 0, 0)}
        };
    IImage sepiaCheck = new RGBImage(sepiaed);
    assertEquals(sepiaCheck, this.sepia.apply(this.check));
  }

  @Test
  public void testMosaic() {
    IImage checkerboard = CheckerboardImageCreator.create(10, 50,
        50, Color.BLACK, Color.RED);
    IImage mosaiced = this.mosaic.apply(checkerboard);
    assertFalse(mosaiced.equals(checkerboard));
  }
}
