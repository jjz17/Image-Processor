import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.IImageProcessorController;
import controller.SimpleImageProcessorController;
import java.io.File;
import java.io.StringReader;
import model.IImageProcessorModel;
import model.SimpleImageProcessorModel;
import model.image.RGBPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for all operation command objects and controller.
 */
public class ControllerAndOperationTest {
  private IImageProcessorModel model;
  private Appendable output;

  @Before
  public void initData() {
    model = new SimpleImageProcessorModel();
    output = new StringBuilder();
  }

  @Test
  public void blurBalloon() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "createLayer second "
        + "importLayer balloonJPG.jpg "
        + "selectCurrent first "
        + "blur "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: second (Visible)\n"
        + "Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: second (Visible)\n"
        + "Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertFalse(model.getLayer("first").equals(model.getLayer("second")));
  }

  @Test
  public void createMultipleLayers() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "createLayer third "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(model.getListLayerIDs().size() == 3);
  }

  @Test
  public void deleteImage() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "createLayer third "
        + "deleteImage "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(model.getListLayerIDs().size() == 0);


  }

  @Test
  public void importSingleLayerImage() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(445, model.getLayer("first").getHeight());
    assertEquals(640, model.getLayer("first").getWidth());
  }

  @Test
  public void importMultiLayeredImage() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "createLayer second "
        + "importLayer balloonJPG.jpg "
        + "exportImage NewImageTest jpg "
        + "delete "
        + "importImage NewImageTest "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Successfully wrote layer\n"
        + "Successfully wrote layer\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Unknown command\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(445, model.getLayer("first").getHeight());
    assertEquals(640, model.getLayer("first").getWidth());
    assertEquals(445, model.getLayer("second").getHeight());
    assertEquals(640, model.getLayer("second").getWidth());
    assertEquals(2, model.getListLayerIDs().size());

  }

  @Test
  public void exportSingleLayerImage() {
    Readable input = new StringReader("createLayer asdf "
        + "importLayer balloonJPG.jpg "
        + "exportLayer jpg "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: asdf (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: asdf (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: asdf (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(new File(imagePath + "asdf.jpg").exists());
  }

  @Test
  public void exportMultiLayerImage() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "createLayer second "
        + "importLayer balloonJPG.jpg "
        + "exportImage NewImageTest png "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Successfully wrote layer\n"
        + "Successfully wrote layer\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(new File(imagePath + "NewImageTest" + File.separator).exists());
    assertTrue(new File(imagePath + "NewImageTest" + File.separator + "first.png").exists());
    assertTrue(new File(imagePath + "NewImageTest" + File.separator + "second.png").exists());
  }

  @Test
  public void makeCurrentNullLayerInvis() {
    Readable input = new StringReader("createLayer first "
        + "makeInvisible "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Can't make a null layer invisible\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
  }

  @Test
  public void makeCurrentValidLayerInvis() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "makeInvisible "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Invisible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertFalse(model.getLayer("first").isVisible());
  }

  @Test
  public void makeCurrentLayerMonochrome() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "makeMonochrome "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(new RGBPixel(81, 81, 81),
        model.getLayer("first").getPixelAt(50, 50));
    assertEquals(new RGBPixel(106, 106, 106),
        model.getLayer("first").getPixelAt(100, 150));
  }

  @Test
  public void removeNullLayer() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "createLayer third "
        + "removeLayer first "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(model.getListLayerIDs().size() == 2);
  }

  @Test
  public void removeValidLayer() {
    Readable input = new StringReader("createLayer first "
        + "importLayer ballonPNG.png "
        + "removeLayer first "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Failed to read image\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertTrue(model.getListLayerIDs().size() == 0);
  }

  @Test
  public void runSimpleScript() {
    Readable input = new StringReader("runScript simpleScript.txt "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n", output.toString());
  }

  @Test
  public void changeCurrentLayer() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "createLayer third "
        + "selectCurrent second "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: third (Null)\n"
        + "Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());


  }

  @Test
  public void makeSepiaValidImage() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonPPM.ppm "
        + "makeSepia "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "PPM image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(new RGBPixel(137, 122, 95), model.getLayer("first").getPixelAt(100, 100));
    assertEquals(new RGBPixel(111, 99, 77), model.getLayer("first").getPixelAt(31, 31));

  }

  @Test
  public void makeSepiaNullImage() {
    Readable input = new StringReader("createLayer first "
        + "makeSepia "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Can't color transform null image\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());

  }

  @Test
  public void copyImageOver() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "setAsCopy first "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Unknown command\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Unknown command\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());

  }

  @Test
  public void copyNullImageOver() {
    Readable input = new StringReader("createLayer first "
        + "createLayer second "
        + "setAsCopyOf first "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Cannot copy null image \n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());

  }

  @Test
  public void generateCheckerboard() {
    Readable input = new StringReader("createLayer first "
        + "makeCheckerboard 2 44 30 black white "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(60, model.getLayer("first").getHeight());
    assertEquals(88, model.getLayer("first").getWidth());
  }

  @Test
  public void sharpenNullImage() {
    Readable input = new StringReader("createLayer first "
        + "sharpen "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Can't filter null image\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
  }

  @Test
  public void sharpenValidImage() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonPPM.ppm "
        + "sharpen "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "PPM image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertEquals(new RGBPixel(211, 200, 202), model.getLayer("first").getPixelAt(100, 100));
    assertEquals(new RGBPixel(175, 159, 159), model.getLayer("first").getPixelAt(31, 31));

  }

  @Test
  public void makeVisibleNull() {
    Readable input = new StringReader("createLayer first "
        + "makeVisible "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Can't make a null layer visible\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
  }

  @Test
  public void makeVisibleValid() {
    Readable input = new StringReader("createLayer first "
        + "importLayer balloonJPG.jpg "
        + "makeInvisible "
        + "createLayer second "
        + "importLayer balloonJPG.jpg "
        + "makeInvisible "
        + "createLayer third "
        + "importLayer balloonJPG.jpg "
        + "makeInvisible "
        + "selectCurrent second "
        + "makeVisible "
        + "quit ");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Null)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Visible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Null)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Visible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: second (Invisible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Null)\n"
        + "second (Invisible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image uploaded\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Visible)\n"
        + "second (Invisible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: Current Layer: third (Invisible)\n"
        + "second (Invisible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: third (Invisible)\n"
        + "Current Layer: second (Invisible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image:\n"
        + "Top Layer: third (Invisible)\n"
        + "Current Layer: second (Visible)\n"
        + "first (Invisible)\n"
        + "\n"
        + "Image processor has quit.", output.toString());
    assertFalse(model.getLayer("first").isVisible());
    assertTrue(model.getLayer("second").isVisible());
    assertFalse(model.getLayer("third").isVisible());
  }

  @Test
  public void quit() {
    Readable input = new StringReader("q");
    IImageProcessorController controller = new SimpleImageProcessorController(model, input, output);
    controller.useProcessor();
    assertEquals("Image:\n"
        + "\n"
        + "Image processor has quit.", output.toString());
  }


}
