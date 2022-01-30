import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

import model.IImageProcessorModel;
import model.SimpleImageProcessorModel;
import model.imagecreator.CheckerboardImageCreator;
import view.ImageProcessorModelTextView;

import static org.junit.Assert.assertEquals;

/** Unit tests to ensure that methods on ImageProcessorModelTextView behave as intended. */
public class ImageProcessorModelTextViewTest {
  private IImageProcessorModel model;
  private Appendable ap;
  private ImageProcessorModelTextView textView;

  @Before
  public void init() {
    this.model = new SimpleImageProcessorModel();
    this.ap = new StringBuilder();
    this.textView = new ImageProcessorModelTextView(this.model, this.ap);
  }

  // tests proper behavior of the toString method
  @Test
  public void testToString() {
    assertEquals("Image:\n", this.textView.toString());
    this.model.createLayer("first");
    this.model.createLayer("second");
    this.model.selectCurrentLayer("first");
    this.model.setCurrentLayer(CheckerboardImageCreator.create(1, 1, 1, Color.BLACK, Color.WHITE));
    assertEquals(
        "Image:\n" + "Top Layer: second (Null)\n" + "Current Layer: first (Visible)\n",
        this.textView.toString());
  }

  // tests proper behavior of the renderImageDiagram method
  @Test
  public void testRenderImageDiagram() {
    try {
      this.textView.renderImageDiagram();
    } catch (IOException ioe) {
      // nothing happens
    }
    assertEquals("Image:\n", this.ap.toString());
    this.model.createLayer("first");
    this.model.createLayer("second");
    this.model.selectCurrentLayer("first");
    this.model.setCurrentLayer(CheckerboardImageCreator.create(1, 1, 1, Color.BLACK, Color.WHITE));
    try {
      this.textView.renderImageDiagram();
    } catch (IOException ioe) {
      // nothing happens
    }
    assertEquals(
        "Image:\n" + "Image:\n" + "Top Layer: second (Null)\n" + "Current Layer: first (Visible)\n",
        this.ap.toString());
  }

  // tests proper behavior of the renderMessage method
  @Test
  public void testRenderMessage() {
    try {
      this.textView.renderMessage("");
    } catch (IOException ioe) {
      // nothing happens
    }
    try {
      this.textView.renderMessage(" HelloWorld ");
    } catch (IOException ioe) {
      // nothing happens
    }
    try {
      this.textView.renderMessage("123 Number Test");
    } catch (IOException ioe) {
      // nothing happens
    }
    try {
      this.textView.renderMessage(" Error Test");
    } catch (IOException ioe) {
      // nothing happens
    }
    try {
      this.textView.renderMessage("    ");
    } catch (IOException ioe) {
      // nothing happens
    }

    assertEquals(" HelloWorld 123 Number Test Error Test    ", this.ap.toString());
  }
}
