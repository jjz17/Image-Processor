package controller.imageutil;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import model.image.IImage;
import model.image.IPixel;
import model.image.RGBImage;
import model.image.RGBPixel;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Converts a PPM to an RGB image.
   *
   * @param filename the PPM image to be converted
   * @param readable the readable that contains the PPM information
   * @return the converted RGBImage
   * @throws IllegalArgumentException if the ppm file cannot be found
   * @throws NullPointerException     if the file name is null
   */
  public static IImage convertPPMToRGBImage(String filename, Readable readable)
      throws IllegalArgumentException, NullPointerException {
    Scanner sc;

    if (readable != null) {
      sc = new Scanner(readable);
    } else {
      Objects.requireNonNull(filename);
      if (!filename.endsWith(".ppm")) {
        throw new IllegalArgumentException("Not a PPM file.");
      }
      try {
        sc = new Scanner(new FileInputStream(filename));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + filename + " not found!");
      }
    }

    StringBuilder builder = new StringBuilder();
    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();

    int height = sc.nextInt();


    IPixel[][] image = new IPixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        image[j][i] = new RGBPixel(r, g, b);
      }
    }

    return new RGBImage(image);
  }

  /**
   * Converts a RGBImage to a Buffered Image.
   *
   * @param img the RGBImage to be converted
   * @return the BufferedImage version of the input image
   */
  public static BufferedImage convertRGBImageToBufferedImage(IImage img) {
    Objects.requireNonNull(img);

    int width = img.getWidth();
    int height = img.getHeight();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel pixel = img.getPixelAt(j, i);
        Color pixelColor = new Color(pixel.getRedVal(), pixel.getGreenVal(), pixel.getBlueVal());
        int newPixel = pixelColor.getRGB();
        image.setRGB(j, i, newPixel);
      }
    }

    return image;
  }

  /**
   * Exports the given RGBImage as a PPM to the given file.
   *
   * @param image the image to be converted
   * @param file the export location of the converted PPM
   * @throws NullPointerException if either input is null
   */
  public static void exportRGBImageAsPPM(IImage image, File file) throws NullPointerException {
    Objects.requireNonNull(image);

    BufferedWriter bw = null;
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("P3 ");
    stringBuilder.append(image.getWidth());
    stringBuilder.append(" ");
    stringBuilder.append(image.getHeight());
    stringBuilder.append(" ");
    stringBuilder.append(image.getMaxVal());
    stringBuilder.append(" \n ");

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel pixel = image.getPixelAt(j, i);
        stringBuilder.append(pixel.getRedVal());
        stringBuilder.append(" ");
        stringBuilder.append(pixel.getGreenVal());
        stringBuilder.append(" ");
        stringBuilder.append(pixel.getBlueVal());
        stringBuilder.append(" ");

        if ((j + 1) % 5 == 0) {
          stringBuilder.append("\n");
        }
      }
    }

    if (file != null) {
      try {
        FileWriter fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        bw.write(stringBuilder.toString());


      } catch (IOException ioe) {
        ioe.printStackTrace();
      } finally {
        try {
          if (bw != null) {
            bw.close();
          }
        } catch (Exception ex) {
          // do nothing
        }
      }
    }
  }

  /**
   * Converts the given buffered image to an RGBImage.
   *
   * @param img the image to be converted
   * @return the converted RGBImage
   */
  public static IImage convertBufferedImageToRGBImage(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    IPixel[][] image = new IPixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = img.getRGB(j, i);
        int r = (pixel >> 16) & 0xFF;
        int g = (pixel >> 8) & 0xFF;
        int b = (pixel >> 0) & 0xFF;
        image[j][i] = new RGBPixel(r, g, b);
      }
    }

    return new RGBImage(image);
  }
}
