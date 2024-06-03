package ies.dam.columns;

import ies.dam.columns.interfaces.IDebuggable;
import ies.dam.columns.interfaces.IDrawable;
import ies.dam.columns.utils.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Jewel implements IDrawable, IDebuggable {

  private JewelType type;
  private boolean isdebug;
  private int row;
  private int column;

  public Jewel(JewelType type, int row, int column) {
    this.type = type;
    this.row = row;
    this.column = column;
  }

  public Jewel() {
    this.type = null;
    this.row = -1;
    this.column = -1;
  }

  public Jewel(JewelType type) {
    this.type = type;
    this.row = -1;
    this.column = -1;
  }

  @Override
  public void paint(GraphicsContext gc) {
    if (this.row >= 0) {
      gc.drawImage(
        Resources.getInstance().getImage("jewels"),
        this.type.getX(),
        this.type.getY(),
        this.type.getW(),
        this.type.getH(),
        (this.type.getW() * column + Board.OFFSETX) * Game.SCALE,
        (this.type.getH() * row + Board.OFFSETY) * Game.SCALE,
        this.type.getW() * Game.SCALE,
        this.type.getH() * Game.SCALE
      );
     this.debug(gc);
    }
  }

  public void moveLeft(Board b) {
    if (this.column > 0) this.column--;
  }

  public void moveRight(Board b) {
    if (this.column < b.COLUMNS - 1) this.column++;
  }

  public void moveDown(Board b) {
    if (this.row < b.ROWS - 1) this.row++;
  }

  public void moveUp(Board b) {
    if (this.row > 0) this.row--;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public JewelType getType() {
    return type;
  }

  public void setType(JewelType type) {
    this.type = type;
  }

  public String toString() {
    return this.type.name() + " F:" + this.row + " C:" + this.column;
  }

  @Override
  public void setDebug(boolean value) {
    this.isdebug = value;
  }

  @Override
  public boolean isDebug() {
    return this.isdebug;
  }

  @Override
  public void debug(GraphicsContext gc) {
    if (this.isdebug) {
        gc.setStroke(Color.WHITE);
        gc.strokeText(
          "F:" + this.row + ",C:" + this.column,
          (this.type.getW() * column + Board.OFFSETX) * Game.SCALE,
          (this.type.getH() * row + Board.OFFSETY) * Game.SCALE
        );
      // TODO Auto-generated method stub
      gc.strokeRect(
        (this.type.getW() * column + Board.OFFSETX) * Game.SCALE,
        (this.type.getH() * row + Board.OFFSETY) * Game.SCALE,
        this.type.getW() * Game.SCALE,
        this.type.getH() * Game.SCALE
      );
    }
  }
}
