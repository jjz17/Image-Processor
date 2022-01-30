package controller.operations;

import controller.IImageProcessorController;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.IImageProcessorModel;
import model.image.IImage;
import model.imagecreator.CheckerboardImageCreator;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to generate a checkerboard image.
 */
public class SetLayerAsProgrammableOperation implements IProcessorOperation {
  private final String tileSize;
  private final String width;
  private final String height;
  private final String color1;
  private final String color2;

  private final Map<String, Color> colorMap = new HashMap<>();

  /**
   * Constructs a SetLayerAsProgrammableOperation with the variables necessary to generate a
   * checkboard image.
   * @param tileSize size of the tiles
   * @param width the width of the image
   * @param height the height of the image
   * @param color1 color 1 of the tile
   * @param color2 color 2 of the tile
   */
  public SetLayerAsProgrammableOperation(
      String tileSize, String width, String height, String color1, String color2) {
    colorMap.put("black", Color.BLACK);
    colorMap.put("white", Color.WHITE);
    colorMap.put("red", Color.RED);
    colorMap.put("blue", Color.BLUE);
    this.tileSize = tileSize;
    this.width = width;
    this.height = height;
    this.color1 = color1;
    this.color2 = color2;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    IImage check = null;
    try {
      check =
          CheckerboardImageCreator.create(
              Integer.valueOf(this.tileSize),
              Integer.valueOf(this.width),
              Integer.valueOf(this.height),
              this.colorMap.get(this.color1),
              this.colorMap.get(this.color2));
    } catch (NullPointerException npe) {
      try {
        view.renderMessage("Invalid inputs for program\n");
      } catch (IOException ioe) {
        // do nothing
      }
    } catch (NumberFormatException nfe) {
      try {
        view.renderMessage("Invalid inputs for program\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
    try {
      model.setCurrentLayer(check);
    } catch (NullPointerException ne) {
      try {
        view.renderMessage("Can't use null image\n");
      } catch (IOException ioe) {
        // do nothing
      }
    } catch (IllegalStateException ise) {
      try {
        view.renderMessage(ise.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    } catch (IllegalArgumentException iae) {
      try {
        view.renderMessage(iae.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
