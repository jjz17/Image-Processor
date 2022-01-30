package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import model.transformations.Sharpen;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to apply the sharpen filter to the current layer.
 */
public class SharpenLayerOperation implements IProcessorOperation {
  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.transformCurrentLayer(new Sharpen());
    } catch (NullPointerException npe) {
      try {
        view.renderMessage(npe.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
