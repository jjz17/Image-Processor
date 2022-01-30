package view;

import java.awt.Color;
import model.transformations.ITransformation;

/** Represents a listener of a view. */
public interface IViewListener {

  /**
   * Handles selecting the current layer.
   *
   * @param next true if selecting the next layer, false if previous
   */
  void handleSelectCurrentEvent(boolean next);

  /**
   * Handles toggling the visiblity of the current layer.
   *
   * @param visible true if making current layer visible, false if invisible
   */
  void handleToggleVisibilityEvent(boolean visible);

  /**
   * Handles creating a new layer.
   *
   * @param layerName the name to be given to the new layer
   */
  void handleCreateLayerEvent(String layerName);

  /** Handles deleting the current layer. */
  void handleDeleteCurrentEvent();

  /** Handles deleting the entire image. */
  void handleDeleteEntireImage();

  /**
   * Handles making a copy of the target layer.
   *
   * @param id the id of the layer to be copied
   */
  void handleMakeCopyEvent(String id);

  /**
   * Handles creating a checkerboard image.
   *
   * @param tileSize the tile size
   * @param numTilesWidth the width
   * @param numTilesHeight the height
   * @param color1 the first color
   * @param color2 the second color
   */
  void handleMakeCheckerboardEvent(
      int tileSize, int numTilesWidth, int numTilesHeight, Color color1, Color color2);

  /**
   * Handles transforming the current layer.
   *
   * @param transformation the transformation to be performed
   */
  void handleTransformationEvent(ITransformation transformation);

  /**
   * Handles importing a layer.
   *
   * @param imagePath the location of the layer to be imported
   */
  void handleImportLayerEvent(String imagePath);

  /**
   * Handles importing an image.
   *
   * @param textPath the location of the text file containing instructions for the image
   */
  void handleImportImageEvent(String textPath);

  /**
   * Handles exporting the current layer.
   *
   * @param folderPath the location to store the layer
   * @param format the format to be exported in
   */
  void handleExportLayerEvent(String folderPath, String format);

  /**
   * Handles exporting the image.
   *
   * @param folderPath the location to store the image
   * @param folderName the name of the folder to store the image
   * @param format the format to be exported in
   */
  void handleExportImageEvent(String folderPath, String folderName, String format);

  /**
   * Handles running a script.
   *
   * @param textFilePath the location of the script
   */
  void handleRunScriptEvent(String textFilePath);
}
