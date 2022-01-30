package controller;

/**
 * Interface for the freecell game controller. An implementation will work with the IFreeCellModel
 * interface to provide a game of freecell
 */
public interface IImageProcessorController extends IController {
  /**
   * Changes the in field of the controller to the input readable.
   * @param newIn the input readable
   * @throws NullPointerException if the input is null
   */
  void changeIn(Readable newIn) throws NullPointerException;
}
