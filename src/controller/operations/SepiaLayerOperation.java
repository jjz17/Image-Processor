package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import model.transformations.Sepia;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to make use the sepia transformation on the
 * current layer.
 */
public class SepiaLayerOperation implements IProcessorOperation {

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.transformCurrentLayer(new Sepia());
    } catch (NullPointerException npe) {
      try {
        view.renderMessage(npe.getMessage() + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
