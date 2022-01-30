package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This class contains the operation layer to remove a desired layer from this model.
 */
public class RemoveLayerOperation implements IProcessorOperation {
  private final String layerName;

  /**
   * Constructs a RemoveLayerOperation with the name of the layer to be removed.
   * @param layerName name of the target layer
   */
  public RemoveLayerOperation(String layerName) {
    this.layerName = layerName;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.removeLayer(this.layerName);
    } catch (IllegalArgumentException iae) {
      try {
        view.renderMessage(iae.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
