package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * Contains the operation necessary to set this layer as a copy.
 */
public class SetLayerAsCopyOperation implements IProcessorOperation {
  private final String layerID;

  /**
   * Constructs a SetLayerAsCopyOperation with the needed layerID.
   * @param layerID the ID of the target layer
   */
  public SetLayerAsCopyOperation(String layerID) {
    this.layerID = layerID;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.setCurrentLayer(model.getLayer(layerID).makeCopy());
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
    } catch (NullPointerException npe) {
      try {
        view.renderMessage("Cannot copy null image \n");
      } catch (IOException e) {
        // do nothing
      }
    }
  }
}
