package controller.operations;

import controller.IImageProcessorController;
import controller.imageutil.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.IImageProcessorModel;
import model.image.IImage;
import view.IImageProcessorModelView;

/**
 * This class contains the operation necessary to import an entire image given a txt file containing
 * the locations of all images.
 */
public class ImportImageOperation implements IProcessorOperation {

  private final String folderName;

  /**
   * Constructor for ImportImageOperation giving the file path of the target .txt file.
   * @param folderName file path of the folder containing the .txt file
   */
  public ImportImageOperation(String folderName) {
    this.folderName = folderName;
  }

  @Override
  public void operate(IImageProcessorModel model, IImageProcessorModelView view,
      IImageProcessorController controller) {
    model.deleteImage();

    String home = System.getProperty("user.dir");
    String imagePath = home + File.separator + "images" + File.separator;

    File textFile = new File(imagePath + folderName + File.separator + "INFO.txt");
    BufferedImage img = null;
    boolean run = true;

    Scanner input = null;
    try {
      input = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      try {
        view.renderMessage("File not found.\n");
        run = false;
      } catch (IOException ioException) {
        //do nothing
      }
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
            try {
              view.renderMessage("Failed to read file " + f.getName() + "\n");
            } catch (IOException ioException) {
              ioException.printStackTrace();
            }
          }
        }
      }
    }
  }
}
