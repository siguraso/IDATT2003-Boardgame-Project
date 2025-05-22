package edu.ntnu.idi.idatt.boardgame.view.window.components.ladder;

import java.util.stream.IntStream;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;

/**
 * <h1>Final Class ~ LadderDrawer</h1>
 * This class is responsible for drawing the ladder on a canvas. It takes in start and end
 * positions, as well as dimensions to create the ladders. Extends the JavaFX Canvas class "Canvas"
 * to provide a drawing surface for ladders.
 *
 * @author MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public final class LadderDrawer extends Canvas {

  private final GraphicsContext gc = getGraphicsContext2D();

  /**
   * Constructor for LadderDrawer. Manually sets the width and height of the canvas. The default
   * width and height are set to 800 x 800 px.
   */
  public LadderDrawer() {
    this.setWidth(800);
    this.setHeight(800);
  }

  /**
   * Draws a ladder on the canvas, from a tile to another.
   *
   * @param startPos   the starting position of the ladder (double[] {x-position, y-position}). The
   *                   start-position from a tile on the board.
   * @param endPos     the ending position of the ladder (double[] {x-position, y-position}). The
   *                   end-position from a tile on the board.
   * @param dimensions an array (int[] {width, height}) with the width and height of a tile.
   */
  public void draw(double[] startPos, double[] endPos, int[] dimensions) {
    // This could involve rendering the board, players, and other game elements
    gc.beginPath();
    gc.setStroke(Paint.valueOf("Black"));
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setLineWidth(4);

    Point2D start = sceneToLocal(startPos[0], startPos[1]);
    Point2D end = sceneToLocal(endPos[0], endPos[1]);

    // Directional vector from start to end
    double dx = end.getX() - start.getX();
    double dy = end.getY() - start.getY();
    double length = Math.hypot(dx, dy);

    // perpendicular vector
    double nx = -dy / length;
    double ny = dx / length;

    // width of the ladder
    double widthLadder = (double) dimensions[0] / 4;

    // compute rail positions
    // Left rail
    Point2D leftStart = new Point2D(start.getX() + nx * widthLadder,
        start.getY() + ny * widthLadder);
    Point2D leftEnd = new Point2D(end.getX() + nx * widthLadder,
        end.getY() + ny * widthLadder);

    // Right rail
    Point2D rightStart = new Point2D(start.getX() - nx * widthLadder,
        start.getY() - ny * widthLadder);
    Point2D rightEnd = new Point2D(end.getX() - nx * widthLadder,
        end.getY() - ny * widthLadder);

    // Draw the side rails
    gc.moveTo(leftStart.getX(), leftStart.getY());
    gc.lineTo(leftEnd.getX(), leftEnd.getY());

    gc.moveTo(rightStart.getX(), rightStart.getY());
    gc.lineTo(rightEnd.getX(), rightEnd.getY());

    // Draw "steps" in the ladder.
    int stepCount = (int) (length / ((double) dimensions[1] / 3));
    IntStream.range(0, stepCount).forEach(i -> {
      //Skipping the first and last step
      if (i == 0 || i == stepCount) {
        return;
      }

      double t = (double) i / stepCount;
      double x = start.getX() + t * dx;
      double y = start.getY() + t * dy;

      Point2D stepLeft = new Point2D(x + nx * widthLadder,
          y + ny * widthLadder);
      Point2D stepRight = new Point2D(x - nx * widthLadder,
          y - ny * widthLadder);

      gc.moveTo(stepLeft.getX(), stepLeft.getY());
      gc.lineTo(stepRight.getX(), stepRight.getY());
    });

    // Stroke the lines of the ladder, and close the path
    gc.stroke();
    gc.closePath();
  }
}
