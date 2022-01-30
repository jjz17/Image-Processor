package controller.operations;

import controller.IImageProcessorController;
import controller.imageutil.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageProcessorModel;
import model.image.IImage;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to export a single layer to a destination of the
 * user's choice.
 */
public class ExportLayerOperation implements IProcessorOperation {
  private final String format;

  /**
   * Constructs an instance of ExportLayerOperation containing the information necessary to export
   * a single layer to the desktop.
   * @param format the desired format
   */
  public ExportLayerOperation(String format) {
    this.format = format;
  }

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    Objects.requireNonNull(this.format);

    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;

    IImage image = null;
    String id = null;

    try {
      id = model.getTopVisibleLayerID();
      image = model.getLayer(id);
    } catch (IllegalStateException ise) {
      try {
        view.renderMessage(ise.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
    catch (NullPointerException npe) {
      try {
        view.renderMessage("Can't export null layer\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }

    File outputfile = new File(imagePath + id + "." + this.format);

    if (this.format.equals("ppm")) {
      try {
        ImageUtil.exportRGBImageAsPPM(image, outputfile);
        try {
          view.renderMessage("PPM image exported\n");
        } catch (IOException ioe) {
          // do nothing
        }
      } catch (NullPointerException npe) {
        try {
          view.renderMessage("Can't export null image\n");
        } catch (IOException ioe) {
          // do nothing
        }
      }
    } else {
      try {
        BufferedImage bi = ImageUtil.convertRGBImageToBufferedImage(image);
        outputfile = new File(imagePath + model.getTopVisibleLayerID() + "." + this.format);
        ImageIO.write(bi, this.format, outputfile);
      } catch (NullPointerException npe) {
        try {
          view.renderMessage("Can't export null image\n");
        } catch (IOException ioe) {
          // do nothing
        }
      } catch (IOException e) {
        try {
          view.renderMessage("Failed to export image\n");
        } catch (IOException ioe) {
          // do nothing
        }
      }
    }
  }
}
