package ies.dam.columns;

import ies.dam.columns.interfaces.IDrawable;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import org.checkerframework.checker.units.qual.C;

public class Board implements IDrawable {

  public static final int COLUMNS = 6;
  public static final int ROWS = 13;
  public static final int OFFSETX = 113;
  public static final int OFFSETY = 8;
  private Jewel[][] matrix;

  public Board() {
    this.matrix = new Jewel[ROWS][COLUMNS];
    this.matrix[12][1] = new Jewel(JewelType.YELLOW, 12, 1);
    this.matrix[12][2] = new Jewel(JewelType.YELLOW, 12, 2);
    this.matrix[12][3] = new Jewel(JewelType.YELLOW, 12, 3);
    this.matrix[11][2] = new Jewel(JewelType.YELLOW, 11, 2);
    this.matrix[11][1] = new Jewel(JewelType.ORANGE, 11, 1);
    this.matrix[10][1] = new Jewel(JewelType.YELLOW, 10, 1);
  }

  public Board(Jewel[][] matrix) {
    if (matrix.length != ROWS || matrix[0].length != COLUMNS) {
      throw new IllegalArgumentException("Error dimensions");
    } else {
      this.matrix = matrix;
    }
  }

  //se busca la primera fila libre en la columna
  private int getMinRow(int col) {
    int counter = ROWS - 1;
    while (this.matrix[counter][col] != null) {
      counter--;
    }

    return counter;
  }

  public boolean isEmpty(int row, int col) {
    if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) return (
      this.matrix[row][col] == null
    ); else return false;
  }

  public boolean collision(Column c) {
    boolean vuelta = false;
    //ha tocado fondo
    if (c.get(2).getRow() == this.ROWS - 1) {
      this.matrix[c.get(2).getRow()][c.get(2).getColumn()] = c.get(2);
      this.matrix[c.get(1).getRow()][c.get(1).getColumn()] = c.get(1);
      this.matrix[c.get(0).getRow()][c.get(0).getColumn()] = c.get(0);
      vuelta = true;
    }
    //toca con alguna
    else if (this.getMinRow(c.get(2).getColumn()) == c.get(2).getRow()) {
      this.matrix[c.get(2).getRow()][c.get(2).getColumn()] = c.get(2);
      this.matrix[c.get(1).getRow()][c.get(1).getColumn()] = c.get(1);
      this.matrix[c.get(0).getRow()][c.get(0).getColumn()] = c.get(0);
      vuelta = true;
    }
    return vuelta;
  }

  public void evaluate() {
    for (int i = this.matrix.length - 1; i >= 0; i--) {
      for (int j = 0; j < this.matrix[0].length; j++) {
        //se evalua horizontalmente
        ArrayList<Jewel> elements = new ArrayList<>();
         elements.addAll(this.eval_horizontal(i, j));
        elements.addAll(this.eval_vertical(i, j));
        elements.addAll (this.eval_diagonal_derecha(i, j));
        elements.addAll(this.eval_diagonal_izquierda(i, j));
        if (elements.size() > 2) {
          this.removeJewels(elements);
          this.recolocate();
        }
       
      }
    }
  }

  private boolean recolocate() {
    boolean recolocate = false;
    //la ultima fina no es necesario mirarla
    for (int c = 0; c < COLUMNS; c++) {
      for (int f = ROWS - 2; f >= 0; f--) {
        if (this.matrix[f][c] != null && this.matrix[f + 1][c] == null) {
          this.matrix[f + 1][c] = this.matrix[f][c];
          this.matrix[f][c] = null;
          this.matrix[f + 1][c].moveDown(this);
          //se vuele a empezar por si son mas de dos
          f = ROWS - 2;
          recolocate = true;
        }
      }
    }
    return recolocate;
  }

  private void removeJewels(ArrayList<Jewel> jewels) {
    //se borra
    for(int i=0;i<jewels.size();i++)
         this.matrix[jewels.get(i).getRow()][jewels.get(i).getColumn()]=null;
  }

  private ArrayList<Jewel> eval_horizontal(int row, int col) {
    Jewel j = this.matrix[row][col];
    ArrayList<Jewel> elements = new ArrayList<>();
    if (j != null) {
      elements.add(j);
      int index = j.getColumn() + 1;
      while (
        index < COLUMNS - 1 &&
        this.matrix[row][index] != null &&
        this.matrix[row][index].getType().equals(j.getType())
      ) {
        elements.add(this.matrix[row][index]);

        index++;
      }
    }
    if (elements.size() < 3) elements.clear();
    return elements;
  }

  private ArrayList<Jewel> eval_vertical(int row, int col) {
    Jewel j = this.matrix[row][col];
    ArrayList<Jewel> elements = new ArrayList<>();
    if (j != null) {
      elements.add(j);
      int index = j.getRow() - 1;
      //no se llega al final, no es nula y es del mismo tipo
      while (
        index < ROWS - 1 &&
        this.matrix[index][col] != null &&
        this.matrix[index][col].getType().equals(j.getType())
      ) {
        elements.add(this.matrix[index][col]);

        index--;
      }
    }

    if (elements.size() < 3) {
      elements.clear();
    }
    if (elements.size() >= 3) {
      System.out.println(elements.size());
    }
    return elements;
  }

  private ArrayList<Jewel> eval_diagonal_derecha(int row, int col) {
    Jewel j = this.matrix[row][col];
    ArrayList<Jewel> elements = new ArrayList<>();
    //derecha
    if (j != null) {
      elements.add(j);
      int index_r = j.getRow() - 1;
      int index_c = j.getColumn() + 1;
      //no se llega al final, no es nula y es del mismo tipo
      while (
        index_r < ROWS - 1 &&
        index_r >= 0 &&
        index_c < COLUMNS - 1 &&
        index_c >= 0 &&
        this.matrix[index_r][index_c] != null &&
        this.matrix[index_r][index_c].getType().equals(j.getType())
      ) {
        elements.add(this.matrix[index_r][index_c]);

        index_r--;
        index_c++;
      }
    }

    if (elements.size() < 3) {
      elements.clear();
    }
    if (elements.size() >= 3) {
      System.out.println(elements.size());
    }
    return elements;
  }

  private ArrayList<Jewel> eval_diagonal_izquierda(int row, int col) {
    Jewel j = this.matrix[row][col];
    ArrayList<Jewel> elements = new ArrayList<>();
    //izquierda
    if (j != null) {
      elements.add(j);
      int index_r = j.getRow() - 1;
      int index_c = j.getColumn() - 1;
      //no se llega al final, no es nula y es del mismo tipo
      while (
        index_r < ROWS - 1 &&
        index_r >= 0 &&
        index_c < COLUMNS - 1 &&
        index_c >= 0 &&
        this.matrix[index_r][index_c] != null &&
        this.matrix[index_r][index_c].getType().equals(j.getType())
      ) {
        elements.add(this.matrix[index_r][index_c]);

        index_r--;
        index_c--;
      }
    }

    if (elements.size() < 3) {
      elements.clear();
    }
    if (elements.size() >= 3) {
        for(Jewel je:elements){
            je.setDebug(true);
        }
      System.out.println(elements.size());
    }
    return elements;
  }

  @Override
  public void paint(GraphicsContext gc) {
    for (Jewel[] js : this.matrix) {
      for (Jewel j : js) {
        if (j != null) {
          j.paint(gc);
        }
      }
    }
  }
}
