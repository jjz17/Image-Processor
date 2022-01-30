package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.image.IImage;
import model.transformations.ITransformation;

/** Represents a simple image processor which imports, processes, and exports images. */
public class SimpleImageProcessorModel implements IImageProcessorModel {
  protected final Map<String, IImage> layers;
  protected String currentLayerID;
  protected final List<String> layerOrder;

  /** Default constructor that creates a SimpleImageProcessor that is empty. */
  public SimpleImageProcessorModel() {
    this.layers = new HashMap<>();
    this.layerOrder = new ArrayList<>();
  }

  @Override
  public void createLayer(String layerID) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(layerID);

    if (this.layers.containsKey(layerID)) {
      throw new IllegalArgumentException("Layer with this ID already exists.");
    }
    this.layers.put(layerID, null);
    this.currentLayerID = layerID;
    this.layerOrder.add(layerID);
  }

  @Override
  public void selectCurrentLayer(String layerID) throws NullPointerException {
    Objects.requireNonNull(layerID);

    if (!this.layers.containsKey(layerID)) {
      throw new IllegalArgumentException("Layer with this ID does not exist.");
    }
    this.currentLayerID = layerID;
  }

  @Override
  public void setCurrentLayer(IImage image)
      throws NullPointerException, IllegalStateException, IllegalArgumentException {
    Objects.requireNonNull(image);
    boolean validDimensions = true;

    if (this.layers.size() == 0) {
      throw new IllegalStateException("Image currently has no layers");
    }

    if (this.layers.size() != 0) {
      for (String layer : this.layerOrder) {
        if (this.getLayer(layer) != null) {
          IImage dimensionCheck = this.getLayer(layer);
          if (dimensionCheck.getWidth() != image.getWidth()
                  && dimensionCheck.getHeight() != image.getHeight()) {
            validDimensions = false;
          }
        }
      }
    }

    if (validDimensions) {
      this.layers.replace(this.currentLayerID, image);
    } else {
      throw new IllegalArgumentException("Attempted to add layer with invalid dimensions.");
    }
  }

  @Override
  public void removeLayer(String layerID) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(layerID);

    if (!this.layers.containsKey(layerID)) {
      throw new IllegalArgumentException("Layer with this ID does not exist.");
    }
    this.layers.remove(layerID);
    this.layerOrder.remove(layerID);

    if (this.currentLayerID.equals(layerID)) {
      if (this.layerOrder.size() == 0) {
        this.currentLayerID = null;
      } else {
        this.currentLayerID = this.layerOrder.get(0);
      }
    }
  }

  @Override
  public void transformCurrentLayer(ITransformation transformation) throws NullPointerException {
    Objects.requireNonNull(transformation);

    this.layers.replace(
        this.currentLayerID, transformation.apply(this.layers.get(this.currentLayerID)));
  }

  @Override
  public void deleteImage() {
    this.layers.clear();
    this.currentLayerID = null;
    this.layerOrder.clear();
  }

  @Override
  public IImage getLayer(String layerID) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(layerID);

    if (!this.layers.containsKey(layerID)) {
      throw new IllegalArgumentException("Layer with this ID does not exist.");
    }
    return this.layers.get(layerID);
  }

  @Override
  public List<IImage> getImage() {
    List<IImage> image = new ArrayList<>();
    for (int i = this.layerOrder.size() - 1; i >= 0; i--) {
      image.add(this.layers.get(this.layerOrder.get(i)));
    }

    return image;
  }

  @Override
  public int numLayers() {
    return this.layers.size();
  }

  @Override
  public List<String> getListLayerIDs() {
    List<String> orderedListIDs = new ArrayList<>();
    for (int i = this.layerOrder.size() - 1; i >= 0; i--) {
      orderedListIDs.add(this.layerOrder.get(i));
    }

    return orderedListIDs;
  }

  @Override
  public String getTopVisibleLayerID() throws IllegalStateException {
    for (int i = this.layerOrder.size() - 1; i >= 0; i--) {
      IImage tempLayer = this.layers.get(this.layerOrder.get(i));
      if (tempLayer.isVisible()) {
        return this.layerOrder.get(i);
      }
    }
    throw new IllegalStateException("There are no visible layers in this image.");
  }

  @Override
  public String getCurrentLayerID() throws IllegalStateException {
    if (this.currentLayerID == null) {
      throw new IllegalStateException("There are no layer images to process right now.");
    }
    return this.currentLayerID;
  }
}
