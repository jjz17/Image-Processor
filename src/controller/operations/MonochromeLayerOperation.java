package controller.operations;

import controller.IImageProcessorController;
import java.io.IOException;

import model.IImageProcessorModel;
import model.transformations.Monochrome;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to make the current layer monochrome.
 */
public class MonochromeLayerOperation implements IProcessorOperation {

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    try {
      model.transformCurrentLayer(new Monochrome());
    } catch (NullPointerException npe) {
      try {
        view.renderMessage(npe.getMessage()  + "\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }
  }
}
