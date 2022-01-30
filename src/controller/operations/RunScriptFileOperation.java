package controller.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import controller.IImageProcessorController;
import model.IImageProcessorModel;
import view.IImageProcessorModelView;

/** This class contains the operation necessary to run a series of commands given a script. */
public class RunScriptFileOperation implements IProcessorOperation {
  private final String scriptName;

  /**
   * Constructs an instance of RunScriptFileOperation with the needed file path of the desired
   * script file.
   *
   * @param scriptName the file path of the script file
   */
  public RunScriptFileOperation(String scriptName) {
    this.scriptName = scriptName;
  }

  @Override
  public void operate(
      IImageProcessorModel model,
      IImageProcessorModelView view,
      IImageProcessorController controller) {
    String home = System.getProperty("user.dir");
    String scriptPath = home + File.separator;

    StringBuilder stringBuilder = new StringBuilder();
    File textFile = null;

    try {
      textFile = new File(scriptPath + scriptName);
    } catch (IllegalArgumentException iae) {
      try {
        view.renderMessage("Text file not found\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }

    Scanner input = null;

    try {
      input = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      try {
        view.renderMessage("Text file not found\n");
      } catch (IOException ioe) {
        // do nothing
      }
    }

    StringReader script = null;

    if (input != null) {

      while (input.hasNext()) {
        stringBuilder.append(input.nextLine() + "\n");
      }

      script = new StringReader(stringBuilder.toString());
      model.deleteImage();
    }
    try {
      controller.changeIn(script);
    } catch (NullPointerException npe) {
      // nothing happens
    }
  }
}
