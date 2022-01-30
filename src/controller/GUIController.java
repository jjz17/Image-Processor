package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.imageutil.ImageUtil;
import model.SimpleImageProcessorModel;
import model.image.IImage;
import model.imagecreator.CheckerboardImageCreator;
import model.transformations.ITransformation;

import model.IImageProcessorModel;
import view.IView;
import view.IViewListener;

/**
 * A controller to interact with the ImageProcessorModel and a GUI view.
 */
public class GUIController implements IController, IViewListener {

  private final IImageProcessorModel model;
  private final IView view;

  /**
   * Constructs a {@code controller.GUIController} object to handle user interaction with a {@code
   * SimpleFreecellModel} game and display with a view.
   *
   * @param model the model to be linked to the controller
   * @param view  the view to be linked to the controller
   * @throws NullPointerException if either input is null
   */
  public GUIController(IImageProcessorModel model, IView view) throws NullPointerException {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.view.registerViewEventListener(this);
  }

  /**
   * Displays the image in the view.
   */
  protected void showImage() {
    this.view.displayImage(this.model.getLayer(this.model.getCurrentLayerID()));
  }

  /**
   * Displays the layer diagram in the view.
   */
  protected void showLayerDiagram() {
    this.view.displayLayers(
        this.model.getListLayerIDs(), this.getVisibility(), this.model.getCurrentLayerID());
  }

  @Override
  public void useProcessor() {
    // nothing happens
  }

  @Override
  public void handleSelectCurrentEvent(boolean next) {
    List<String> order = model.getListLayerIDs();
    try {
      int ind = order.indexOf(model.getCurrentLayerID());
      if (next) {
        ind -= 1;
      } else {
        ind += 1;
      }
      model.selectCurrentLayer(order.get(ind));
      this.showImage();
      this.showLayerDiagram();
    } catch (IndexOutOfBoundsException e) {
      if (next) {
        view.displayError("No next layer");
      } else {
        view.displayError("No prev layer");
      }
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalStateException e) {
      view.displayError(e.getMessage());
    }
  }

  @Override
  public void handleToggleVisibilityEvent(boolean visible) {
    try {
      IImage target = this.model.getLayer(this.model.getCurrentLayerID());
      if (visible) {
        target.makeVisible();
      } else {
        target.makeInvisible();
      }
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalStateException e) {
      this.view.displayError(e.getMessage());
    } catch (NullPointerException e) {
      this.view.displayError("Can't toggle visibility on null image");
    }
  }

  /**
   * Returns the visibility information of the layers in the image.
   *
   * @return an ArrayList containing visibility information
   */
  protected List<Boolean> getVisibility() {
    List<Boolean> visibility = new ArrayList<>();
    for (String layer : this.model.getListLayerIDs()) {
      try {
        if (this.model.getLayer(layer).isVisible()) {
          visibility.add(true);
        } else {
          visibility.add(false);
        }
      } catch (NullPointerException e) {
        visibility.add(null);
      }
    }
    return visibility;
  }

  @Override
  public void handleCreateLayerEvent(String layerName) {
    try {
      this.model.createLayer(layerName);
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalArgumentException e) {
      this.view.displayError(e.getMessage());
    } catch (NullPointerException e) {
      // nothing happens
    }
  }

  @Override
  public void handleMakeCheckerboardEvent(
      int tileSize, int numTilesWidth, int numTilesHeight, Color color1, Color color2) {
    try {
      model.setCurrentLayer(
          CheckerboardImageCreator.create(tileSize, numTilesWidth, numTilesHeight, color1, color2));
      this.view.displayImage(this.model.getLayer(this.model.getCurrentLayerID()));
    } catch (IllegalStateException ise) {
      this.view.displayError(ise.getMessage());
    }
  }

  @Override
  public void handleDeleteCurrentEvent() {
    try {
      this.model.removeLayer(this.model.getCurrentLayerID());
    } catch (IllegalStateException e) {
      this.view.displayError(e.getMessage());
    }
    try {
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalStateException e) {
      this.view.displayLayers(this.model.getListLayerIDs(), this.getVisibility(), null);
      this.view.displayImage(null);
    }
  }

  @Override
  public void handleDeleteEntireImage() {
    this.model.deleteImage();
    try {
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalStateException e) {
      this.view.displayLayers(this.model.getListLayerIDs(), this.getVisibility(), null);
      this.view.displayImage(null);
    }
  }

  @Override
  public void handleMakeCopyEvent(String id) {
    try {
      model.setCurrentLayer(model.getLayer(id).makeCopy());
      this.showImage();
      this.showLayerDiagram();
    } catch (NullPointerException e) {
      // nothing happens
    } catch (IllegalArgumentException e) {
      this.view.displayError(e.getMessage());
    }
  }

  @Override
  public void handleTransformationEvent(ITransformation transformation) {
    try {
      this.model.transformCurrentLayer(transformation);
      this.showImage();
      this.showLayerDiagram();
    } catch (NullPointerException e) {
      this.view.displayError(e.getMessage());
    }
  }

  @Override
  public void handleImportLayerEvent(String imagePath) {
    BufferedImage img = null;
    IImage image = null;

    if (imagePath.endsWith(".ppm")) {
      try {
        image = ImageUtil.convertPPMToRGBImage(imagePath, null);
        try {
          model.setCurrentLayer(image);
          this.showImage();
          this.showLayerDiagram();
        } catch (IllegalStateException ise) {
          view.displayError(ise.getMessage() + "\n");
        }
      } catch (IllegalArgumentException iae) {
        view.displayError(iae.getMessage() + "\n");
      }
    } else {
      try {
        img = ImageIO.read(new File(imagePath));
        image = ImageUtil.convertBufferedImageToRGBImage(img);
        try {
          model.setCurrentLayer(image);
          this.showImage();
          this.showLayerDiagram();
        } catch (IllegalArgumentException iae) {
          view.displayError(iae.getMessage() + "\n");
        } catch (IllegalStateException ise) {
          view.displayError(ise.getMessage() + "\n");
        }
      } catch (IOException ioe) {
        view.displayError("Failed to read image\n");
      }
    }
  }

  @Override
  public void handleImportImageEvent(String textPath) {
    model.deleteImage();

    File textFile = new File(textPath);
    BufferedImage img = null;
    boolean run = true;

    Scanner input = null;
    try {
      input = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      this.view.displayError("File not found.\n");
      run = false;
    }

    if (run) {
      List<String> los = new ArrayList<String>();
      while (input.hasNextLine()) {
        String temp = input.nextLine();
        los.add(temp);
      }
      input.close();

      Map<String, String> filesMap = new LinkedHashMap<String, String>();

      for (String s : los) {
        String newFileName = s.split("\\*")[0].trim();
        String visibility = s.replaceAll(".*\\*", "");
        filesMap.put(newFileName, visibility);
      }

      for (Map.Entry<String, String> item : filesMap.entrySet()) {
        File f = new File(item.getKey());
        if (f.exists() && !f.isDirectory()) {

          try {
            img = ImageIO.read(f);
            IImage image = ImageUtil.convertBufferedImageToRGBImage(img);
            String fileName = f.getName();
            String newFileName = fileName.split("\\.")[0];

            model.createLayer(newFileName);
            model.selectCurrentLayer(newFileName);
            model.setCurrentLayer(image);

            if (item.getValue().equals("vis")) {
              model.getLayer(newFileName).makeVisible();
            } else if (item.getValue().equals("invis")) {
              model.getLayer(newFileName).makeInvisible();
            }

          } catch (IOException e) {
            this.view.displayError("Failed to read file " + f.getName() + "\n");
          }
        }
      }
    }

    try {
      this.showImage();
      this.showLayerDiagram();
    } catch (IllegalStateException ise) {
      this.view.displayError(ise.getMessage());
    }
  }

  @Override
  public void handleExportLayerEvent(String folderPath, String format) {
    IImage image = null;
    String id = null;

    try {
      id = model.getTopVisibleLayerID();
      image = model.getLayer(id);
    } catch (IllegalStateException ise) {
      view.displayError(ise.getMessage());
    } catch (NullPointerException npe) {
      view.displayError("Can't export null layer");
    }

    File outputfile = new File(folderPath + File.separator + id + "." + format);

    if (format.equals("ppm")) {
      try {
        ImageUtil.exportRGBImageAsPPM(image, outputfile);
      } catch (NullPointerException ne) {
        view.displayError("Can't export null layer");
      }
    } else {
      try {
        BufferedImage bi = ImageUtil.convertRGBImageToBufferedImage(image);
        ImageIO.write(bi, format, outputfile);
      } catch (NullPointerException npe) {
        view.displayError("Can't export null layer");
      } catch (IOException e) {
        view.displayError("Failed to export image");
      }
    }
  }

  @Override
  public void handleExportImageEvent(String folderPath, String folderName, String format) {
    String home = System.getProperty("user.dir");
    String newFolderPath = folderPath + File.separator;

    List<String> idList = model.getListLayerIDs();
    if (idList.size() == 0) {
      view.displayError("Can't export empty image\n");
    } else {

      File newDir = new File(newFolderPath + folderName);
      newDir.mkdirs();

      try {
        File folderInfo = new File(newDir, "INFO.txt");
        folderInfo.createNewFile();
      } catch (IOException e) {
        view.displayError("Failed to create new info file\n");
      }

      try {
        FileWriter infoWriter =
            new FileWriter(newFolderPath + folderName + File.separator + "INFO.txt");
        Collections.reverse(idList);
        for (String s : idList) {
          String visibility = null;
          try {
            if (model.getLayer(s).isVisible()) {
              visibility = "vis";
            } else {
              visibility = "invis";
            }
            infoWriter.write(
                newFolderPath
                    + folderName
                    + File.separator
                    + s
                    + "."
                    + format
                    + " *"
                    + visibility
                    + "\n");
          } catch (NullPointerException npe) {
            view.displayError("Null layers are not exported\n");
          }
        }
        infoWriter.close();
      } catch (IOException e) {
        view.displayError("Failed to write to layered image info file\n");
      }

      List<IImage> imagesList = new ArrayList<>();
      for (String name : idList) {
        imagesList.add(model.getLayer(name));
      }

      for (int i = 0; i < imagesList.size(); i++) {
        try {
          BufferedImage bi = ImageUtil.convertRGBImageToBufferedImage(imagesList.get(i));
          File outputFile =
              new File(newFolderPath + folderName + File.separator + idList.get(i) + "." + format);
          ImageIO.write(bi, format, outputFile);
        } catch (IOException e) {
          view.displayError("Could not export image\n");
        } catch (NullPointerException npe) {
          view.displayError("File does not exist\n");
        }
      }
    }
  }

  @Override
  public void handleRunScriptEvent(String scriptPath) {
    model.deleteImage();
    StringBuilder stringBuilder = new StringBuilder();
    File textFile = null;

    try {
      textFile = new File(scriptPath);
    } catch (IllegalArgumentException iae) {
      view.displayError("Text file not found\n");
    }

    Scanner input = null;

    try {
      input = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      view.displayError("Text file not found\n");
    }

    StringReader script = null;

    if (input != null) {

      while (input.hasNext()) {
        stringBuilder.append(input.nextLine() + "\n");
      }

      script = new StringReader(stringBuilder.toString());
      model.deleteImage();
    }

    SimpleImageProcessorModel tempModel = new SimpleImageProcessorModel();
    IImageProcessorController controller =
        new SimpleImageProcessorController(
            tempModel, script, System.out);
    controller.useProcessor();

    List<String> idNames = tempModel.getListLayerIDs();
    Collections.reverse(idNames);
    for (String id : idNames) {
      model.createLayer(id);
      model.setCurrentLayer(tempModel.getLayer(id));
      this.showImage();
      this.showLayerDiagram();
    }
  }
}
