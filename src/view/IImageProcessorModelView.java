package view;

import java.io.IOException;

/**
 * Represents a view of an Image Processor Model.
 */
public interface IImageProcessorModelView {

  /**
   * Return the present state of the image processor as a string.
   *
   * @return the present state of the image processor as a string
   */
  String toString();

  /**
   * Writes the present state of the image processor to the appendable of choice.
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderImageDiagram() throws IOException;

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
