# Photoshop

## About

Photoshop is a Java project that enables the user to utilize a simple image processor with text
commands.

## Design Choices

This project utilizes the CMV design pattern.

### Controller

Controller interface is defined within IImageProcessorController. One implementation of
the controller is SimpleImageProcessorController which allows the user to utilize the ImageProcessor
using a console. The other implementation is GUIController, which processes image operations made
from the GraphicalView class.

The imageutil class contains the method required to convert a PPM to an IImage, and convert an
IImage to a BufferedImage.

The operations package contains all operations to completely utilize our model.

### Model

Model interface is defined within IImageProcessorModel. The only current implementation of the model
is SimpleImageProcessorModel, which allows a user to modify one image with multiple layers at a
time.

The image package contains the implementation for the interpretation and temporary storage of image
files.

The imagecreator package contains the implementation for the creation of checkerboard images.

The transformations package contains the implementation for the transformation or filtering of
images.

### View

View interface is defined within IImageProcessorModelView. One implementation of the
model is ImageProcessorModelTextView, which produces a text view of an IImageProcessorModel.

GUI View interface is defined within IView. The one implementation IView is GraphicalView, 
which represents the graphical representation of the image processor. It utilizes the IViewListener
to send method calls to the controller.

## Design Changes Since Last Iteration

### 6/26/21

Created two new interfaces by the name of IView and IViewListener to display changes and
handle image operations made within the graphical image processor within the view package.

Created new class GraphicalView within the view package to represent GUI representation of 
the image processor. Is decoupled from any calls directly to the controller or model. 

Created new class GUIController within the controller package to handle operation calls from 
the GraphicalView class. Implements the IViewListener interface.

Created new class Mosaic within the transformations package over the model package to 
handle the mosaic image effect. No other changes were required for its implementation.

No changes were made to our past design; new features were only added.



### 6/18/21

Added a "visible" boolean field to the RGBImage class to denote whether or not the RGBImage (layer)
is visible in the model

Added methods to mutate the visibility of an RGBImage instance

Added method to return a shallow copy of the RGBImage (shallow copy because pixels are immutable)

Added a "layerOrder" ArrayList<String> field to the imageProcessor model to store the order of the
layers created in the multi-layer image

Added a "currentLayerID" String field to identify the current layer that is being operated on

Added observer methods for these two additional fields

Added mutator methods to select a new current layer and set current layers as new images

## Features

### 6/26/21
- The creation of a new GUI for our photoshop application. Is able to perform 
all actions that the previous text-based controller could.
- Capable of now applying a mosaic filter on an image, with up to 10,000 seeds.

### 6/18/21
- Import a layered image given the folder location of the relevant INFO.txt file. INFO.txt file must
  contain a formatted list of image locations.
- Export a layered image into a folder of images with an INFO.txt file containing the locations of
  all images and if they are visible or not. Also preserves order of images.
- Perform transformations and filters on individual layers within an image. Currently supported are:
    - Monochrome, Sepia, Sharpen, Blur
- Import a single image file as a layer.
- Export a single image file as a layer.
- Create, delete, copy, and make visible/invisible for layers.
- Run a script file given the name of the script file.
- Generate a checkboard image layer.

## How to Use the Program and Examples

See the USEME.md file.

## Limitations

### Text 
- Locations of relevant images must be within images folder of root folder.
- All script files must be within root folder.
- When importing a layered image, the user must place the INFO.txt file containing the information
  for the layers within a folder within the images folder of the root folder.
- Each line of INFO.txt file must be formatted like so:

> (File Path of Image 1) *(invis or vis)
>
>  (File Path of Image 2) *(invis or vis)
>
> (File Path of Image 3) *(invis or vis)...

### GUI
- The runScript feature requires that the script file follow the same requirements as 
required for the text version of photoshop.
- Limitations regarding the format of image information files remain the same, however 
now desired images for import can be located anywhere.

## Authors

Programmed by David Huh and Jason Zhang for CS 3500: Object-Oriented Design at Northeastern
University for the Summer 1 term of 2021. Go Huskies!

## Citation

"balloonPPM", "balloonPNG", "balloonJPG" image source:
https://pixabay.com/photos/hot-air-balloon-ballon-ballooning-5979187/

- Open source image - Selected the 640 x 445 option