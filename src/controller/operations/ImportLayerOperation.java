package controller.operations;

import controller.IImageProcessorController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import controller.imageutil.ImageUtil;
import model.IImageProcessorModel;
import model.image.IImage;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to import a single layer from the desktop.
 */
public class ImportLayerOperation implements IProcessorOperation {
  private final String fileName;

  /**
   * Constructs an ImportLayerOperation with the information necessary to locate the desired image.
   * @param fileName the name of the desired file on the desktop
   */
  public ImportLayerOperation(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    Objects.requireNonNull(this.fileName);

    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;

    BufferedImage img = null;
    IImage image = null;

    if (this.fileName.endsWith(".ppm")) {
      try {
        image = ImageUtil.convertPPMToRGBImage(imagePath + this.fileName, null);
        try {
          model.setCurrentLayer(image);
          try {
            view.renderMessage("PPM image uploaded\n");
          } catch (IOException ioe) {
            // do nothing
          }
        } catch (IllegalStateException ise) {
          try {
            view.renderMessage(ise.getMessage() + "\n");
          } catch (IOException ioe) {
            // do nothing
          }
        }
      } catch (IllegalArgumentException iae) {
        try {
          view.renderMessage(iae.getMessage() + "\n");
        } catch (IOException ioe) {
          // do nothing
        }
      }
    } else {
      try {
        img = ImageIO.read(new File(imagePath + this.fileName));
        image = ImageUtil.convertBufferedImageToRGBImage(img);
        try {
          model.setCurrentLayer(image);
          try {
            view.renderMessage("Image uploaded\n");
          } catch (IOException ioe) {
            // do nothing
          }
        } catch (IllegalArgumentException iae) {
          try {
            view.renderMessage(iae.getMessage() + "\n");
          } catch (IOException ioe) {
            // do nothing
          }
        } catch (IllegalStateException ise) {
          try {
            view.renderMessage(ise.getMessage() + "\n");
          } catch (IOException ioe) {
            // do nothing
          }
        }
      } catch (IOException ioe) {
        try {
          view.renderMessage("Failed to read image\n");
        } catch (IOException e) {
          // do nothing
        }
      }
    }
  }
}