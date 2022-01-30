package view;

import java.util.List;

import model.image.IImage;

/** Represents a view of an Image Processor Model. */
public interface IView {
  /**
   * Registers the given listener as a listener of this view.
   *
   * @param listener the given listener
   */
  void registerViewEventListener(IViewListener listener);

  /**
   * Displays the given error.
   *
   * @param error the error to be displayed
   */
  void displayError(String error);

  /**
   * Displays the given image.
   *
   * @param image the image to be displayed
   */
  void displayImage(IImage image);

  /**
   * Displays the given layers with their visibility and current layer information.
   *
   * @param layers the layers to be displayed
   * @param visibility the visibility information of the layers
   * @param currentLayer the current layer
   */
  void displayLayers(List<String> layers, List<Boolean> visibility, String currentLayer);
}
