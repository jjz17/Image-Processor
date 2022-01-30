package model;

import java.util.List;

import model.image.IImage;

/**
 * Represents an image processor which views/observes its images.
 */
public interface IViewImageProcessorModel {

  /**
   * Gets the image with the provided ID.
   *
   * @param id the ID of the target image
   * @return the target image
   * @throws IllegalArgumentException if no image has the provided ID
   * @throws NullPointerException     if the given id is null
   */
  IImage getLayer(String id) throws IllegalArgumentException, NullPointerException;

  /**
   * Returns a list of all the layer images in the image.
   *
   * @return a list of all the layer images in the image
   */
  List<IImage> getImage();

  /**
   * Counts the number of layers in the image.
   *
   * @return the number of layers in the image
   */
  int numLayers();

  /**
   * Returns an ordered list of all the layer names in the image being processed.
   *
   * @return an ordered list of all the IDs of the images stored in the image processor
   */
  List<String> getListLayerIDs();

  /**
   * Returns the ID of the top visible layer.
   *
   * @return the ID of the top visible layer
   * @throws IllegalStateException if the image has no visible layers
   */
  String getTopVisibleLayerID() throws IllegalStateException;

  /**
   * Returns the ID of the current layer.
   *
   * @return the String ID of the current layer
   * @throws IllegalStateException if there are no layers in the image
   */
  String getCurrentLayerID() throws IllegalStateException;
}
