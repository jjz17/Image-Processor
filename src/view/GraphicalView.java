package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import controller.imageutil.ImageUtil;
import model.image.IImage;
import model.transformations.Blur;
import model.transformations.ITransformation;
import model.transformations.Monochrome;
import model.transformations.Mosaic;
import model.transformations.Sepia;
import model.transformations.Sharpen;

/**
 * Represents a GUI view of an IImageProcessorModel that displays the components of model (current
 * layer, layer diagram, error messages).
 */
public class GraphicalView extends JFrame implements IView, ActionListener {

  private JPanel mainPanel;
  private final List<IViewListener> viewListeners;
  private JTextArea textArea;
  private JLabel label;
  private DefaultListModel model = new DefaultListModel();

  /**
   * Constructs a {@code view.GraphicalView} object.
   */
  public GraphicalView() {
    super("Simple Image Processor");
    setSize(450, 300);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JScrollPane mainScrollPane;
    JMenuBar mb;
    JScrollPane textScroll;

    JMenu selectLayer;
    JMenuItem nextLayer;
    JMenuItem prevLayer;

    JMenu toggleVisibility;
    JMenuItem makeVis;
    JMenuItem makeInvis;

    JMenu build;
    JMenuItem deleteEntireImage;
    JMenuItem createLayer;
    JMenuItem deleteCurrent;
    JMenuItem makeCurrentCopyOf;
    JMenuItem makeCurrentCheckerboard;

    JMenu edit;
    JMenuItem blur;
    JMenuItem sharpen;
    JMenuItem sepia;
    JMenuItem monochrome;
    JMenuItem mosaic;

    JMenu importExportScript;
    JMenuItem importLayer;
    JMenuItem importImage;
    JMenuItem exportCurrentLayer;
    JMenuItem exportImage;
    JMenuItem runScript;

    JScrollPane previewScrollPane;
    JScrollPane scrollPane;
    JList<String> displayList;

    this.viewListeners = new ArrayList<>();

    mb = new JMenuBar();

    selectLayer = new JMenu("Select Layer");
    nextLayer = new JMenuItem("Next Layer");
    prevLayer = new JMenuItem(("Prev Layer"));
    selectLayer.add(prevLayer);
    selectLayer.add(nextLayer);
    nextLayer.addActionListener(this);
    nextLayer.setActionCommand("nextLayer");
    prevLayer.addActionListener(this);
    prevLayer.setActionCommand("prevLayer");

    toggleVisibility = new JMenu("Toggle Visibility");
    makeVis = new JMenuItem("Make Current Visible");
    makeInvis = new JMenuItem("Make Current Invisible");
    toggleVisibility.add(makeVis);
    toggleVisibility.add(makeInvis);
    makeVis.addActionListener(this);
    makeVis.setActionCommand("makeVis");
    makeInvis.addActionListener(this);
    makeInvis.setActionCommand("makeInvis");

    build = new JMenu("Build");
    createLayer = new JMenuItem("Create New Layer");
    deleteCurrent = new JMenuItem("Delete Current Layer");
    deleteEntireImage = new JMenuItem("Delete Entire Image");
    makeCurrentCopyOf = new JMenuItem("Make Current Layer Copy Of");
    makeCurrentCheckerboard = new JMenuItem("Make Current Layer Checkerboard Image");
    build.add(createLayer);
    build.add(deleteCurrent);
    build.add(deleteEntireImage);
    build.add(makeCurrentCopyOf);
    build.add(makeCurrentCheckerboard);
    createLayer.addActionListener(this);
    createLayer.setActionCommand("createLayer");
    deleteCurrent.addActionListener(this);
    deleteCurrent.setActionCommand("deleteCurrent");
    deleteEntireImage.addActionListener(this);
    deleteEntireImage.setActionCommand("deleteEntireImage");
    makeCurrentCopyOf.addActionListener(this);
    makeCurrentCopyOf.setActionCommand("makeCurrentCopyOf");
    makeCurrentCheckerboard.addActionListener(this);
    makeCurrentCheckerboard.setActionCommand("makeCurrentCheckerboard");

    edit = new JMenu("Edit");
    blur = new JMenuItem("Blur Current Layer");
    sharpen = new JMenuItem("Sharpen Current Layer");
    sepia = new JMenuItem("Make Sepia Current Layer");
    monochrome = new JMenuItem("Make Monochrome Current Layer");
    mosaic = new JMenuItem("Mosaic Current Layer");
    edit.add(blur);
    edit.add(sharpen);
    edit.add(sepia);
    edit.add(monochrome);
    edit.add(mosaic);
    blur.addActionListener(this);
    blur.setActionCommand("blur");
    sharpen.addActionListener(this);
    sharpen.setActionCommand("sharpen");
    sepia.addActionListener(this);
    sepia.setActionCommand("sepia");
    monochrome.addActionListener(this);
    monochrome.setActionCommand("monochrome");
    mosaic.addActionListener(this);
    mosaic.setActionCommand("mosaic");

    importExportScript = new JMenu("I/O & Script");
    importLayer = new JMenuItem("Import Layer");
    importImage = new JMenuItem("Import Image");
    exportCurrentLayer = new JMenuItem("Export Current Layer");
    exportImage = new JMenuItem("Export Image");
    runScript = new JMenuItem("Run Script File");
    importExportScript.add(importLayer);
    importExportScript.add(importImage);
    importExportScript.add(exportCurrentLayer);
    importExportScript.add(exportImage);
    importExportScript.add(runScript);
    importLayer.addActionListener(this);
    importLayer.setActionCommand("importLayer");
    importImage.addActionListener(this);
    importImage.setActionCommand("importImage");
    exportCurrentLayer.addActionListener(this);
    exportCurrentLayer.setActionCommand("exportLayer");
    exportImage.addActionListener(this);
    exportImage.setActionCommand("exportImage");
    runScript.addActionListener(this);
    runScript.setActionCommand("runScript");

    mb.add(selectLayer);
    mb.add(toggleVisibility);
    mb.add(build);
    mb.add(edit);
    mb.add(importExportScript);

    this.setJMenuBar(mb);

    this.mainPanel = new JPanel();
    // for elements to be arranged vertically within this panel
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.PAGE_AXIS));
    // scroll bars around this main panel
    mainScrollPane = new JScrollPane(this.mainPanel);
    add(mainScrollPane);

    JPanel panel = new JPanel(new BorderLayout());
    ImageIcon icon = new ImageIcon();
    this.label = new JLabel();
    previewScrollPane = new JScrollPane(this.label);
    this.label.setIcon(icon);
    this.label.setHorizontalAlignment(JLabel.CENTER);
    this.label.setVerticalAlignment(JLabel.CENTER);
    previewScrollPane.setPreferredSize(new Dimension(100, 100));
    previewScrollPane.setBorder(BorderFactory.createTitledBorder("Current Layer Preview"));
    panel.add(previewScrollPane);
    this.mainPanel.add(panel);

    displayList = new JList(this.model);
    scrollPane = new JScrollPane(displayList);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Layers Diagram"));
    scrollPane.setPreferredSize(new Dimension(100, 100));

    this.mainPanel.add(scrollPane);

    this.textArea = new JTextArea(5, 10);
    this.textArea.setBorder(BorderFactory.createTitledBorder("Error Message"));
    this.textArea.setEditable(false);
    this.textArea.setRows(1);
    textScroll = new JScrollPane(textArea);
    textScroll.setPreferredSize(new Dimension(100, 100));
    this.mainPanel.add(textScroll);
  }

  @Override
  public void registerViewEventListener(IViewListener listener) {
    this.viewListeners.add(Objects.requireNonNull(listener));
  }

  @Override
  public void displayError(String error) {
    this.textArea.setText(Objects.requireNonNull(error));
  }

  @Override
  public void displayImage(IImage image) {
    if (image == null) {
      label.setIcon(new ImageIcon());
    } else {
      label.setIcon(new ImageIcon(ImageUtil.convertRGBImageToBufferedImage(image)));
    }
  }

  @Override
  public void displayLayers(List<String> layers, List<Boolean> visibility, String currentLayer) {
    Objects.requireNonNull(layers);
    Objects.requireNonNull(visibility);
    this.model.clear();

    if (currentLayer != null) {
      if (layers.size() > 0) {
        for (int i = 0; i < layers.size(); i++) {
          String toDisplay = layers.get(i);
          if (toDisplay.equals(currentLayer)) {
            toDisplay += " current";
          }
          if (visibility.get(i) == null) {
            toDisplay += " (Null)";
          } else if (visibility.get(i)) {
            toDisplay += " (Visible)";
          } else {
            toDisplay += " (Invisible)";
          }
          this.model.addElement(toDisplay);
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "nextLayer":
        this.textArea.setText("");
        emitSelectCurrentEvent(true);
        break;
      case "prevLayer":
        this.textArea.setText("");
        emitSelectCurrentEvent(false);
        break;
      case "makeVis":
        this.textArea.setText("");
        emitToggleVisibilityEvent(true);
        break;
      case "makeInvis":
        this.textArea.setText("");
        emitToggleVisibilityEvent(false);
        break;
      case "createLayer":
        this.textArea.setText("");
        String layerName =
            (String)
                JOptionPane.showInputDialog(
                    this.mainPanel,
                    "Desired Layer Name",
                    "Layer Name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
        emitCreateLayerEvent(layerName);
        break;
      case "deleteCurrent":
        emitDeleteCurrentEvent();
        break;
      case "deleteEntireImage":
        emitDeleteEntireImageEvent();
        break;
      case "makeCurrentCopyOf":
        if (this.model.size() > 0) {
          ArrayList<String> temp = Collections.list(this.model.elements());
          Object[] choices = new Object[this.model.size()];
          for (int i = 0; i < temp.size(); i++) {
            String toUse = temp.get(i);
            if (toUse.contains("current")) {
              choices[i] = toUse.substring(0, toUse.indexOf("current"));
            } else {
              choices[i] = toUse.substring(0, toUse.indexOf(" ("));
            }
          }
          String copyLayerName =
              (String)
                  JOptionPane.showInputDialog(
                      this.mainPanel,
                      "Desired Name of Layer to Copy",
                      "Layer Name",
                      JOptionPane.PLAIN_MESSAGE,
                      null,
                      choices,
                      choices[0]);
          emitMakeCurrentCopyOfEvent(copyLayerName);
        }
        break;
      case "makeCurrentCheckerboard":
        int tileSize = createIntPrompt("Tile Size: ", 50);
        int numWidth = createIntPrompt("Width (# of Tiles): ", 200);
        int numHeight = createIntPrompt("Height (# of Tiles): ", 200);
        Color color1 = chooseColor(1);
        Color color2 = chooseColor(2);

        emitMakeCurrentCheckerboardEvent(tileSize, numWidth, numHeight, color1, color2);
        break;
      case "blur":
        this.textArea.setText("");
        emitTransformationEvent(new Blur());
        break;
      case "sharpen":
        this.textArea.setText("");
        emitTransformationEvent(new Sharpen());
        break;
      case "sepia":
        this.textArea.setText("");
        emitTransformationEvent(new Sepia());
        break;
      case "monochrome":
        this.textArea.setText("");
        emitTransformationEvent(new Monochrome());
        break;
      case "mosaic":
        this.textArea.setText("");
        int numSeeds = createIntPrompt("Number of Seeds:", 10000);
        emitTransformationEvent(new Mosaic(numSeeds));
        break;
      case "importLayer":
        this.textArea.setText("");
        emitImportLayerEvent(createFileChooser(false, "ppm", "jpg", "png"));
        break;
      case "importImage":
        this.textArea.setText("");
        emitImportImageEvent(createFileChooser(false, "txt"));
        break;
      case "exportLayer":
        this.textArea.setText("");
        String folderPath = createFileChooser(true);
        System.out.println(folderPath);
        String[] options = {"jpg", "png", "ppm"};
        String format =
            (String)
                JOptionPane.showInputDialog(
                    this.mainPanel,
                    "Desired Format",
                    "Format",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
        System.out.println(format);
        emitExportLayerEvent(folderPath, format);
        break;
      case "exportImage":
        this.textArea.setText("");
        String folderPath1 = createFileChooser(true);
        System.out.println(folderPath1);
        String folderName1 =
            (String)
                JOptionPane.showInputDialog(
                    this.mainPanel,
                    "Desired Folder Name",
                    "New Folder Name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
        System.out.println(folderName1);

        String[] options1 = {"jpg", "png", "ppm"};
        String format1 =
            (String)
                JOptionPane.showInputDialog(
                    this.mainPanel,
                    "Desired Format",
                    "Format",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options1,
                    options1[0]);
        System.out.println(format1);
        emitExportImageEvent(folderPath1, folderName1, format1);
        break;
      case "runScript":
        this.textArea.setText("");
        String name = createFileChooser(false, "txt");
        File textFile = new File(name);
        System.out.println(textFile.getName());
        emitRunScriptEvent(textFile.getName());
        break;
      default:
        // This should never be reached.
    }
  }

  protected String createFileChooser(boolean dirOnly, String... extensions) {
    String description = "";
    if (extensions.length > 0) {
      for (int i = 0; i < extensions.length - 1; i++) {
        description = description + extensions[i].toUpperCase() + ", ";
      }
      description = description + extensions[extensions.length - 1].toUpperCase() + " Files";
    }
    final JFileChooser chooser = new JFileChooser(".");
    if (dirOnly) {
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    if (!dirOnly) {
      FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
      chooser.setFileFilter(filter);
    }
    chooser.setAcceptAllFileFilterUsed(false);
    int returnVal = chooser.showOpenDialog(GraphicalView.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      System.out.println(file.getAbsolutePath());
      return file.getAbsolutePath();
    }
    return "Broken";
  }

  protected int createIntPrompt(String title, int maxVal) {
    NumberFormatter numFormatter = new NumberFormatter(NumberFormat.getInstance());
    numFormatter.setValueClass(Integer.class);
    numFormatter.setMinimum(0);
    numFormatter.setMaximum(maxVal);
    numFormatter.setAllowsInvalid(false);
    JFormattedTextField field = new JFormattedTextField(numFormatter);
    JOptionPane.showMessageDialog(
        this.mainPanel, field, title + " (Max. Value: " + maxVal + ")", JOptionPane.PLAIN_MESSAGE);
    return (Integer) field.getValue();
  }

  protected Color chooseColor(int number) {
    Color[] colorOptions = {Color.RED, Color.BLUE, Color.BLACK, Color.WHITE};
    String[] stringColorOptions1 = {"Red", "Blue", "Black", "White"};
    int colorInt =
        JOptionPane.showOptionDialog(
            this.mainPanel,
            "Desired Tile Color " + number + ":",
            "Color " + number,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            stringColorOptions1,
            stringColorOptions1[0]);
    Color color = colorOptions[colorInt];
    return color;
  }

  protected void emitSelectCurrentEvent(boolean next) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleSelectCurrentEvent(next);
    }
  }

  protected void emitToggleVisibilityEvent(boolean visible) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleToggleVisibilityEvent(visible);
    }
  }

  protected void emitDeleteCurrentEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleDeleteCurrentEvent();
    }
  }

  protected void emitDeleteEntireImageEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleDeleteEntireImage();
    }
  }

  protected void emitMakeCurrentCopyOfEvent(String layerName) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleMakeCopyEvent(layerName);
    }
  }

  protected void emitMakeCurrentCheckerboardEvent(
      int tileSize, int numTilesWidth, int numTilesHeight, Color color1, Color color2) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleMakeCheckerboardEvent(tileSize, numTilesWidth, numTilesHeight, color1, color2);
    }
  }

  protected void emitCreateLayerEvent(String layerName) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleCreateLayerEvent(layerName);
    }
  }

  protected void emitTransformationEvent(ITransformation transformation) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleTransformationEvent(transformation);
    }
  }

  protected void emitImportLayerEvent(String filePath) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleImportLayerEvent(filePath);
    }
  }

  protected void emitImportImageEvent(String textFilePath) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleImportImageEvent(textFilePath);
    }
  }

  protected void emitExportLayerEvent(String folderPath, String format) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleExportLayerEvent(folderPath, format);
    }
  }

  protected void emitExportImageEvent(String folderPath, String folderName, String format) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleExportImageEvent(folderPath, folderName, format);
    }
  }

  protected void emitRunScriptEvent(String textFilePath) {
    for (IViewListener listener : this.viewListeners) {
      listener.handleRunScriptEvent(textFilePath);
    }
  }

}
