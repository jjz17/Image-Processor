import controller.GUIController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import java.io.StringReader;
import java.util.Scanner;

import controller.IController;
import controller.IImageProcessorController;
import controller.SimpleImageProcessorController;
import model.IImageProcessorModel;
import model.SimpleImageProcessorModel;
import view.GraphicalView;

/**
 * Class that utilizes and starts our SimpleImageProcessorController.
 */
public class Main {

  /**
   * Main method to run to start our SimpleImageProcessorController.
   *
   * @param args the command line arguments
   */

  public static void main(String[] args) {
//    switch (args[0]) {
//      case ("-script"):
//        String filePath = args[1];
//
//        StringBuilder stringBuilder = new StringBuilder();
//        File textFile = null;
//
//        try {
//          textFile = new File(filePath);
//        } catch (IllegalArgumentException iae) {
//          iae.printStackTrace();
//        }
//
//        Scanner input = null;
//
//        try {
//          input = new Scanner(textFile);
//        } catch (FileNotFoundException e) {
//          e.printStackTrace();
//        }
//
//        StringReader script = null;
//
//        if (input != null) {
//
//          while (input.hasNext()) {
//            stringBuilder.append(input.nextLine() + "\n");
//          }
//
//          script = new StringReader(stringBuilder.toString());
//        }
//
//        SimpleImageProcessorModel tempModel = new SimpleImageProcessorModel();
//        IImageProcessorController controller =
//            new SimpleImageProcessorController(
//                tempModel, script, System.out);
//        controller.useProcessor();
//
//        break;
//      case ("-text"):
//        IImageProcessorController controller2 =
//            new SimpleImageProcessorController(
//                new SimpleImageProcessorModel(), new InputStreamReader(System.in), System.out);
//        controller2.useProcessor();
//        break;
//      case ("-interactive"):
//        IImageProcessorModel model = new SimpleImageProcessorModel();
//        GraphicalView view = new GraphicalView();
//        view.setVisible(true);
//        IController controller1 = new GUIController(model, view);
//        controller1.useProcessor();
//        break;
//
//      default:
//        System.out.println("Invalid input");
//        return;
//    }


    IImageProcessorModel model = new SimpleImageProcessorModel();
    GraphicalView view = new GraphicalView();
    view.setVisible(true);
    IController controller1 = new GUIController(model, view);
    controller1.useProcessor();

  }
}
