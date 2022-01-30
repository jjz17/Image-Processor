package controller.operations;

import controller.IImageProcessorController;
import controller.imageutil.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import model.IImageProcessorModel;
import model.image.IImage;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to export an entire image to a new folder on the
 * user's desktop.
 */
public class ExportImageOperation implements IProcessorOperation {

  private final String folderName;
  private final String format;

  /**
   * Constructs an ExportImageOperation with the information necessary to perform the operation.
   * @param folderName desired name of the new folder
   * @param format desired format
   */
  public ExportImageOperation(String folderName, String format) {
    this.folderName = folderName;
    this.format = format;
  }

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;

    List<String> idList = model.getListLayerIDs();
    if (idList.size() == 0) {
      try {
        view.renderMessage("Can't export empty image\n");
      } catch (IOException ioe) {
        // do nothing
      }
    } else {

      File newDir = new File(imagePath + folderName);
      newDir.mkdirs();

      try {
        File folderInfo = new File(newDir, "INFO.txt");
        folderInfo.createNewFile();
      } catch (IOException e) {
        try {
          view.renderMessage("Failed to create new info file\n");
        } catch (IOException e1) {
          // Nothing happens
        }
      }

      try {
        FileWriter infoWriter =
            new FileWriter(imagePath + folderName + File.separator + "INFO.txt");
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
                imagePath
                    + folderName
                    + File.separator
                    + s
                    + "."
                    + format
                    + " *"
                    + visibility
                    + "\n");
            try {
              view.renderMessage("Successfully wrote layer\n");
            } catch (IOException ioException) {
              // Nothing happens
            }
          } catch (NullPointerException npe) {
            try {
              view.renderMessage("Null layers are not exported\n");
            } catch (IOException ioException) {
              // Nothing happens
            }
          }
        }
        infoWriter.close();
      } catch (IOException e) {
        try {
          view.renderMessage("Failed to write to layered image info file\n");
        } catch (IOException ioException) {
          // Nothing happens
        }
      }

      List<IImage> imagesList = new ArrayList<>();
      for (String name : idList) {
        imagesList.add(model.getLayer(name));
      }

      for (int i = 0; i < imagesList.size(); i++) {
        try {
          BufferedImage bi = ImageUtil.convertRGBImageToBufferedImage(imagesList.get(i));
          File outputFile =
              new File(imagePath + folderName + File.separator + idList.get(i) + "." + format);
          ImageIO.write(bi, format, outputFile);
        } catch (IOException e) {
          try {
            view.renderMessage("Could not export image\n");
          } catch (IOException ioException) {
            // do nothing
          }
        } catch (NullPointerException npe) {
          // do nothing
        }
      }
    }
  }
}