import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import model.IImageProcessorModel;
import model.SimpleImageProcessorModel;
import model.image.IImage;
import model.imagecreator.CheckerboardImageCreator;
import model.transformations.ITransformation;
import model.transformations.Sharpen;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for SimpleImageProcessorModel.
 */
public class SimpleImageProcessorModelTest {

  private IImageProcessorModel model;
  private final IImage smallRedBlueCheck =
      CheckerboardImageCreator.create(2, 2, 2, Color.RED, Color.BLUE);
  private final IImage largeBlackWhiteCheck =
      CheckerboardImageCreator.create(3, 5, 5, Color.BLACK, Color.WHITE);

  private final ITransformation sharpen = new Sharpen();

  private final IImage sharpenSRBCheck = this.sharpen.apply(this.smallRedBlueCheck);

  @Before
  public void init() {
    this.model = new SimpleImageProcessorModel();
  }

  @Before
  public void initData() {
    model = new SimpleImageProcessorModel();
  }

  @Test
  public void testGetLayer() {
    this.model.createLayer("");
    assertEquals(null, this.model.getLayer(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerException() {
    this.model.getLayer("");
  }

  @Test(expected = NullPointerException.class)
  public void testGetLayerException2() {
    this.model.getLayer(null);
  }

  @Test
  public void testGetImage() {
    assertEquals(new ArrayList<IImage>(), this.model.getImage());
  }

  @Test
  public void testNumLayers() {
    assertEquals(0, this.model.numLayers());
  }

  @Test
  public void testGetListLayerIDs() {
    assertEquals(new ArrayList<String>(), this.model.getListLayerIDs());
  }

  @Test
  public void testGetTopVisibleLayerID() {
    this.model.createLayer("first");
    this.model.setCurrentLayer(this.largeBlackWhiteCheck);
    assertEquals("first", this.model.getTopVisibleLayerID());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetTopVisibleLayerIDException() {
    this.model.getTopVisibleLayerID();
  }

  @Test
  public void testGetCurrentLayerID() {
    this.model.createLayer("");
    assertEquals("", this.model.getCurrentLayerID());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCurrentLayerIDException() {
    this.model.getCurrentLayerID();
  }

  @Test
  public void testCreateLayer() {
    model.createLayer("first");
    model.createLayer("second");
    model.selectCurrentLayer("first");
    model.selectCurrentLayer("second");
    assertEquals(2, model.numLayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateLayerException() {
    model.createLayer("");
    model.createLayer("");
  }

  @Test(expected = NullPointerException.class)
  public void testCreateLayerException2() {
    model.createLayer(null);
  }

  @Test
  public void testSelectCurrentLayer() {
    model.createLayer("");
    model.createLayer("a");
    assertEquals("a", model.getCurrentLayerID());
    model.selectCurrentLayer("");
    assertEquals("", model.getCurrentLayerID());
  }

  @Test
  public void testSetCurrentLayer() {
    model.createLayer("");
    model.setCurrentLayer(this.largeBlackWhiteCheck);
    assertEquals("", model.getTopVisibleLayerID());
  }

  @Test
  public void testRemoveLayer() {
    model.createLayer("");
    model.createLayer("a");
    model.removeLayer("a");
    assertEquals("", model.getCurrentLayerID());
  }

  @Test
  public void testTransformCurrentLayer() {
    model.createLayer("first");
    model.setCurrentLayer(this.smallRedBlueCheck);
    model.transformCurrentLayer(this.sharpen);
    assertEquals(this.sharpenSRBCheck, model.getLayer("first"));
  }

  @Test
  public void testDeleteImage() {
    model.createLayer("first");
    model.setCurrentLayer(this.smallRedBlueCheck);
    model.transformCurrentLayer(this.sharpen);
    assertEquals(this.sharpenSRBCheck, model.getLayer("first"));
    model.deleteImage();
    assertEquals(0, model.numLayers());
  }
}
