package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to add a layer to an image.
 */
public class CreateLayerOperation implements IProcessorOperation {

  private final String layerID;

  /**
   * Creates an instance of a CreateLayerOperation, giving the necessary
   * desired layerID.
   * @param layerID the id of the new layer
   */
  public CreateLayerOperation(String layerID) {
    this.layerID = layerID;
  }

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.createLayer(this.layerID);
    } catch (IllegalArgumentException iae) {
      try {
        view.renderMessage(iae.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
