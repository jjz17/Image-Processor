package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import model.transformations.Blur;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to blur the current layer.
 */

public class BlurLayerOperation implements IProcessorOperation {

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.transformCurrentLayer(new Blur());
    } catch (NullPointerException npe) {
      try {
        view.renderMessage(npe.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
