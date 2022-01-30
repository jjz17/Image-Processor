package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to select and change the current layer.
 */
public class SelectCurrentLayerOperation implements IProcessorOperation {
  private final String layerName;

  /**
   * This constructor creates a SelectCurrentLayerOperation with the desired new current layer.
   * @param layerName desired new current layer
   */
  public SelectCurrentLayerOperation(String layerName) {
    this.layerName = layerName;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.selectCurrentLayer(layerName);
    } catch (IllegalArgumentException iae) {
      try {
        view.renderMessage(iae.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
