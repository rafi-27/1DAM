package ies.dam.columns;

import ies.dam.columns.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

public class Column implements IDrawable {

  private Jewel[] jewels;

  public Column() {
    this.jewels = new Jewel[3];
  }

  public static Column getRandomColumn() {
    Column c = new Column();
    c.jewels[0] =
      new Jewel(
        JewelType.values()[(int) (Math.random() * JewelType.values().length)],
        -3,
        3
      );
    c.jewels[1] =
      new Jewel(
        JewelType.values()[(int) (Math.random() * JewelType.values().length)],
        -2,
        3
      );
    c.jewels[2] =
      new Jewel(
        JewelType.values()[(int) (Math.random() * JewelType.values().length)],
        -1,
        3
      );
    System.out.println(c);
    return c;
  }

  public Column(Jewel[] jewels) {
    this.jewels = jewels;
  }

  public void rotate() {
    JewelType tempo1, tempo2;

    tempo1 = jewels[1].getType();
    jewels[1].setType(jewels[0].getType());
    tempo2 = jewels[2].getType();
    jewels[2].setType(tempo1);
    jewels[0].setType(tempo2);
  }

  public Jewel get(int index) {
    if (index > 2 || index < 0) throw new IndexOutOfBoundsException(
      "Index " + index + " is out of bounds!"
    );
    return this.jewels[index];
  }

  @Override
  public void paint(GraphicsContext gc) {
    for (int i = 0; i < this.jewels.length; i++) this.jewels[i].paint(gc);
  }

  public void moveLeft(Board b) {
    //si esta libre a la izquierda
    if (b.isEmpty(this.jewels[2].getRow(), this.jewels[2].getColumn() - 1)) {
      for (Jewel j : this.jewels) {
        j.moveLeft(b);
      }
    }
  }

  public void moveRight(Board b) {
    //si esta libre a la izquierda
    if (b.isEmpty(this.jewels[2].getRow(), this.jewels[2].getColumn() + 1)) {
      for (Jewel j : this.jewels) {
        j.moveRight(b);
      }
    }
  }

  public void moveDown(Board b) {
    if (this.jewels[2].getRow() < Board.ROWS - 1) for (Jewel j : this.jewels) {
      j.moveDown(b);
    }
  }

  public void moveUp(Board b) {
    for (Jewel j : this.jewels) {
      j.moveUp(b);
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.jewels[0]);
    sb.append("\n");
    sb.append(this.jewels[1]);
    sb.append("\n");
    sb.append(this.jewels[2]);
    sb.append("\n");
    return sb.toString();
  }
}
