import static controller.imageutil.ImageUtil.convertRGBImageToBufferedImage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.imageutil.ImageUtil;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import model.image.IImage;
import model.image.RGBImage;
import model.image.RGBPixel;
import model.imagecreator.CheckerboardImageCreator;
import org.junit.Test;

/**
 * Test class for ImageUtil.
 */

public class ImageUtilTest {
  private IImage ppm;
  private BufferedImage bi;

  @Test
  public void convertPPMtoRGBValid() {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator + "balloonPPM.ppm";
    ppm = ImageUtil.convertPPMToRGBImage(imagePath, null);
    assertTrue(ppm instanceof RGBImage);
    assertEquals(new RGBPixel(88, 80, 80), ppm.getPixelAt(20, 20));
    assertEquals(new RGBPixel(89, 81, 81), ppm.getPixelAt(50, 50));
  }

  @Test (expected = IllegalArgumentException.class)
  public void convertPPMtoRGBNotPPM() {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator + "balloonJPG.jpg";
    ppm = ImageUtil.convertPPMToRGBImage(imagePath, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void convertPPMtoRGBNonexistentFile() {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator + "asdfad.jpg";
    ppm = ImageUtil.convertPPMToRGBImage(imagePath, null);
  }

  @Test
  public void convertRGBtoBufferedImage() {
    ppm = CheckerboardImageCreator.create(1,
        5, 4, Color.RED, Color.BLUE);
    bi = convertRGBImageToBufferedImage(ppm);
    assertEquals(-65536, bi.getRGB(1, 1));
    assertEquals(-65536, bi.getRGB(3, 3));
  }

  @Test
  public void exportAsPPM() {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator + "ppm.PPM";
    ppm = CheckerboardImageCreator.create(1,
        5, 4, Color.RED, Color.BLUE);
    ImageUtil.exportRGBImageAsPPM(ppm, new File(imagePath));
    assertTrue(new File(imagePath).exists());
  }

  @Test
  public void convertBufferedImageToRGBImage() {
    ppm = CheckerboardImageCreator.create(1,
        50, 40, Color.RED, Color.BLUE);
    bi = convertRGBImageToBufferedImage(ppm);
    ppm = ImageUtil.convertBufferedImageToRGBImage(bi);
    assertTrue(ppm instanceof RGBImage);
    assertEquals(new RGBPixel(255, 0, 0), ppm.getPixelAt(20, 20));
    assertEquals(new RGBPixel(255, 0, 0), ppm.getPixelAt(30, 16));
    assertEquals(new RGBPixel(255, 0, 0), ppm.getPixelAt(40, 30));
  }



}
