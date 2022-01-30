package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import controller.operations.BlurLayerOperation;
import controller.operations.CreateLayerOperation;
import controller.operations.DeleteImageOperation;
import controller.operations.ExportImageOperation;
import controller.operations.ExportLayerOperation;
import controller.operations.IProcessorOperation;
import controller.operations.ImportImageOperation;
import controller.operations.ImportLayerOperation;
import controller.operations.InvisibleLayerOperation;
import controller.operations.MonochromeLayerOperation;
import controller.operations.RemoveLayerOperation;
import controller.operations.RunScriptFileOperation;
import controller.operations.SelectCurrentLayerOperation;
import controller.operations.SepiaLayerOperation;
import controller.operations.SetLayerAsCopyOperation;
import controller.operations.SetLayerAsProgrammableOperation;
import controller.operations.SharpenLayerOperation;
import controller.operations.VisibleLayerOperation;
import model.IImageProcessorModel;
import view.IImageProcessorModelView;
import view.ImageProcessorModelTextView;

/**
 * A simple text controller to utilize the ImageProcessorModel.
 */
public class SimpleImageProcessorController implements IImageProcessorController {

  private final IImageProcessorModel model;
  private Readable in;
  private final IImageProcessorModelView view;
  private final Map<String, Function<Scanner, IProcessorOperation>> knownOps;

  /**
   * Constructs a {@code SimpleFreecellController} object to handle user interaction with a {@code
   * SimpleFreecellModel} game.
   *
   * @throws NullPointerException if the Readable or Appendable are null
   */
  public SimpleImageProcessorController(IImageProcessorModel model, Readable rd, Appendable ap)
      throws NullPointerException {
    this.model = Objects.requireNonNull(model, "Invalid model");
    this.in = Objects.requireNonNull(rd, "Invalid readable");
    if (ap == null) {
      throw new IllegalArgumentException("Invalid appendable");
    }
    this.view = new ImageProcessorModelTextView(this.model, ap);

    knownOps = new HashMap<>();
    knownOps.put("runScript", s -> new RunScriptFileOperation(s.next()));
    knownOps.put("selectCurrent", s -> new SelectCurrentLayerOperation(s.next()));
    knownOps.put("importImage", s -> new ImportImageOperation(s.next()));
    knownOps.put("importLayer", s -> new ImportLayerOperation(s.next()));
    knownOps.put("exportImage", s -> new ExportImageOperation(s.next(), s.next()));
    knownOps.put("exportLayer", s -> new ExportLayerOperation(s.next()));
    knownOps.put("createLayer", s -> new CreateLayerOperation(s.next()));
    knownOps.put("blur", s -> new BlurLayerOperation());
    knownOps.put("sharpen", s -> new SharpenLayerOperation());
    knownOps.put("makeMonochrome", s -> new MonochromeLayerOperation());
    knownOps.put("makeSepia", s -> new SepiaLayerOperation());
    knownOps.put("makeInvisible", s -> new InvisibleLayerOperation());
    knownOps.put("makeVisible", s -> new VisibleLayerOperation());
    knownOps.put("removeLayer", s -> new RemoveLayerOperation(s.next()));
    knownOps.put("deleteImage", s -> new DeleteImageOperation());
    knownOps.put("setAsCopyOf", s -> new SetLayerAsCopyOperation(s.next()));
    knownOps.put(
        "makeCheckerboard",
        s -> new SetLayerAsProgrammableOperation(s.next(), s.next(), s.next(), s.next(), s.next()));
  }

  /**
   * Transmits the input command to this controller's Appendable field and handles IOExceptions when
   * necessary.
   *
   * @param command A String command
   * @throws IllegalStateException if it fails to write to Appendable
   */
  protected void write(String command) throws IllegalStateException {
    try {
      this.view.renderMessage(command);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to aopendable.");
    }
  }

  /**
   * Transmits the current board state to this controller's Appendable field and handles
   * IOExceptions when necessary.
   *
   * @throws IllegalStateException if it fails to write to Appendable
   */
  protected void showImageDiagram() throws IllegalStateException {
    try {
      this.view.renderImageDiagram();
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable.");
    }
    try {
      this.view.renderMessage("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable.");
    }
  }

  @Override
  public void useProcessor() {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;
    File imageFolder = new File(imagePath);
    if (!imageFolder.exists()) {
      imageFolder.mkdir();
    }

    this.showImageDiagram();

    Scanner scanner = new Scanner(this.in);

    String command = null;

    while (scanner.hasNext()) {

      command = scanner.next();

      IProcessorOperation operation;

      if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
        try {
          view.renderMessage("Image processor has quit.");
        } catch (IOException e) {
          // do nothing
        }
        return;
      }
      Function<Scanner, IProcessorOperation> cmd = knownOps.getOrDefault(command, null);
      if (cmd == null) {
        this.write("Unknown command\n");
      } else {
        try {
          operation = cmd.apply(scanner);
          operation.operate(this.model, this.view, this);
          if (command.equals("runScript")) {
            scanner = new Scanner(this.in);
          }
        } catch (NoSuchElementException nsee) {
          this.write("Not enough inputs to run command\n");
        }
      }

      this.showImageDiagram();
    }

    this.showImageDiagram();

    return;
  }

  @Override
  public void changeIn(Readable newIn) throws NullPointerException {
    Objects.requireNonNull(newIn);

    this.in = newIn;
  }
}
