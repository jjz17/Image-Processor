package view;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import model.IViewImageProcessorModel;

/**
 * Represents a textual view of an IImageProcessorModel. It returns a formatted String which
 * represents the state of the game.
 */
public class ImageProcessorModelTextView implements IImageProcessorModelView {

  private final IViewImageProcessorModel model;
  private final Appendable ap;

  /**
   * Constructs a {@code FreecellTextView} object.
   *
   * @param model the FreecellModel to be viewed
   * @throws NullPointerException if the model input is null
   */
  public ImageProcessorModelTextView(IViewImageProcessorModel model, Appendable ap)
      throws NullPointerException {
    this.model = Objects.requireNonNull(model, "Model can't be null");
    this.ap = Objects.requireNonNull(ap, "Appendable can't be null");
  }

  public ImageProcessorModelTextView(IViewImageProcessorModel model)
      throws IllegalArgumentException {
    this(model, System.out);
  }

  @Override
  public String toString() {
    StringBuilder view = new StringBuilder("Image:\n");
    if (this.model.numLayers() > 0) {
      List<String> orderedListLayerIDs = this.model.getListLayerIDs();
      for (int i = 0; i < this.model.numLayers(); i++) {
        if (i == 0) {
          view.append("Top Layer: ");
        }
        if (this.model.getCurrentLayerID().equals(orderedListLayerIDs.get(i))) {
          view.append("Current Layer: ");
        }
        view.append(orderedListLayerIDs.get(i));
        if (this.model.getLayer(orderedListLayerIDs.get(i)) == null) {
          view.append(" (Null)\n");
        } else if (this.model.getLayer(orderedListLayerIDs.get(i)).isVisible()) {
          view.append(" (Visible)\n");
        } else {
          view.append(" (Invisible)\n");
        }
      }
    }
    return view.toString();
  }

  @Override
  public void renderImageDiagram() throws IOException {
    try {
      this.ap.append(this.toString());
    } catch (IOException e) {
      throw new IOException("Could not write to appendable.");
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      message = "";
    }
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IOException("Could not write to appendable");
    }
  }
}
