package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary ot make the current layer visible.
 */
public class VisibleLayerOperation implements IProcessorOperation {

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    String id = null;
    try {
      id = model.getCurrentLayerID();
    } catch (IllegalStateException ise) {
      try {
        view.renderMessage(ise.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
    try {
      model.getLayer(id).makeVisible();
    } catch (NullPointerException npe) {
      try {
        view.renderMessage("Can't make a null layer visible\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
