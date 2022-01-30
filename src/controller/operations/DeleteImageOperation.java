package controller.operations;

import controller.IImageProcessorController;
import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to delete an image.
 */
public class DeleteImageOperation implements IProcessorOperation {

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    model.deleteImage();
  }
}
