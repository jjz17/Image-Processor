package model;

import model.image.IImage;
import model.transformations.ITransformation;

/** Represents an image processor which imports, processes, and exports images. */
public interface IImageProcessorModel extends IViewImageProcessorModel {

  /**
   * Creates a new layer in the model.
   *
   * @param layerID the name of the newly created layer
   * @throws IllegalArgumentException if the layer name already exists
   * @throws NullPointerException if the input is null
   */
  void createLayer(String layerID) throws IllegalArgumentException, NullPointerException;

  /**
   * Change current layer to layer with matching given id.
   *
   * @param layerID id of layer to be changed to current
   * @throws NullPointerException if the input is null
   */
  void selectCurrentLayer(String layerID) throws NullPointerException;

  /**
   * Sets the current layer of the model to the IImage of choice.
   *
   * @param image image current is to be changed to
   * @throws NullPointerException if the input is null
   * @throws IllegalStateException if the image has no layers
   * @throws IllegalArgumentException if the image to be added is of invalid dimensions
   */
  void setCurrentLayer(IImage image)
      throws NullPointerException, IllegalStateException, IllegalArgumentException;

  /**
   * Removes the layer with the specified ID.
   *
   * @param layerID the ID of the layer to be removed
   * @throws IllegalArgumentException if no layer has the given ID
   * @throws NullPointerException if the input is null
   */
  void removeLayer(String layerID) throws IllegalArgumentException, NullPointerException;

  /**
   * Performs a transformation on the current layer.
   *
   * @param transformation the transformation to be performed.
   * @throws NullPointerException if the input is null
   */
  void transformCurrentLayer(ITransformation transformation) throws NullPointerException;

  /** Removes all images stored in the image processor. */
  void deleteImage();
}
