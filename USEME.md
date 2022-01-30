# How to Use Photoshop

Currently, the photoshop application utilizes a console-like system to manipulate images. Any script
files should be in same root folder as the jar file. All inputs should be separated by a space or
new line.

## Interactive

All functionality (import, export, layer creation/deletion, editing) is available through the menu

### Interaction through the command line:

Script Mode:
java -jar photoshop.jar -script path-of-script-file (the program will open the script file, execute
it and then shut down)

NOTE - The Run Script File function has the same limitations as the runScript command in the
text-based controller: all images must be stored in the same folder as the jar file to be accessed
by the script, the script text file can be stored anywhere

Text Mode:
java -jar photoshop.jar -text (the program will open in an interactive text mode, allowing the user
to type the script and execute it one line at a time)

Interactive Mode:
java -jar photoshop.jar -interactive (the program will open the graphical user interface)

## Text

Text enclosed within curly brackets ("{}") marks variable entries that the user chooses.

### Commands

| Command (Should be typed in this exact format) | Purpose | Example Usage |
|---------| ---------| ----|
| createLayer {name of image} | Creates a new layer | createLayer first
| blur | Blurs the current layer | blur
|deleteImage | Deletes the entire image | deleteImage
|exportImage {folder name} {image format} | Exports the image to a new folder with a chosen image format | exportImage NewImage jpg
|exportLayer {image format} |Exports the current layer to the images folder in the format of choice | exportLayer png
|importImage {folder name containing INFO.txt file} | Imports an image given the folder name the INFO.txt file is located in | importImage NewImage
|importLayer {image file name with extension} | Imports a single image to be the current layer given the file name | importLayer balloon.jpg
|makeInvisible | Makes the current layer invisible | makeInvisible
|makeMonochrome | Makes the current layer monochrome | makeMonochrome
|removeLayer {id of layer} | Removes the layer of choice | removeLayer first
|runScript {script file name with extension} | Runs a script given the name | runScript script.txt
|selectCurrent {id of layer} | Changes the current layer to the layer of choice | selectCurrent third
|makeSepia | Makes the current layer sepia | makeSepia
|setAsCopyOf {id of layer to be copied} | Makes the current layer a copy of the layer of choice | setAsCopyOf layer1
|makeCheckerboard {pixel side length of square tiles} {width of image in tiles} {length of image in tiles} {color 1} {color 2} | Generates a checkerboard image to set as the current layer (only supports colors red, blue, black, and white, and must be input in lowercase)| makeCheckerboard 2 4 3 red blue
|sharpen | Sharpens the current layer | sharpen
| makeVisible | Makes the current layer visible | makeVisible
| quit  | Exits the application | quit




