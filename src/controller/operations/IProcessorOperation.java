package controller.operations;

import controller.IImageProcessorController;
import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/**
 * This interface contains the methods necessary to effectively utilize the command pattern in the
 * operations needed to fully utilize the image processor.
 */
public interface IProcessorOperation {

  /**
   * Performs the desired operation. Varies per class.
   * @param model model to be changed
   * @param view output to be used
   * @param controller controller to be used
   */
  void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller);
}

