package ies.dam.columns;

import ies.dam.columns.interfaces.IDebuggable;
import ies.dam.columns.interfaces.IKeyListener;
import ies.dam.columns.interfaces.IWarnClock;
import ies.dam.columns.keyboard.KeyBoard;
import ies.dam.columns.utils.Clock;
import ies.dam.columns.utils.Resources;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Game implements IWarnClock, IKeyListener, IDebuggable {

  public static final int SCALE = 3;

  public static Clock clock = new Clock(1);
  private Dimension2D original_size;
  private Board board;

  private GraphicsContext ctx;
  private GraphicsContext ctxbackground;
  private KeyBoard kb;
  private boolean debug;

  private Column column;
  private Column next;

  //private Ball pelota;
  // private ArrayList<Ball> pelotas;

  public Game(
    GraphicsContext context,
    GraphicsContext background,
    Dimension2D original
  ) {
    this.ctx = context;
    this.ctxbackground = background;
    this.kb = new KeyBoard();
    this.original_size = original;
    this.board = new Board();
    this.column = Column.getRandomColumn();
    this.next = Column.getRandomColumn();
    Game.clock.addIWarClock(this);
  }

  public void start() {
    Game.clock.start();
    this.paintbackground();
    Resources r = Resources.getInstance();

    r.getSound("background")
      .setOnEndOfMedia(
        new Runnable() {
          public void run() {
            r.getSound("background").seek(Duration.ZERO);
          }
        }
      );
    r.getSound("background").play();
  }

  public void stop() {
    Game.clock.stop();
  }

  @Override
  public synchronized void TicTac() {
    // procesar la entrada
    this.process_input();
    // actualizar
    this.update();
    // pintar
    this.render();
    this.kb.resetOnChange();
  }

  private void process_input() {}

  private void update() {
    if (!this.board.collision(column)) {
      this.column.moveDown(this.board);
    } else {
      Resources.getInstance().getSound("jump").stop();
      
      Resources.getInstance().getSound("jump").play();
      this.board.evaluate();
      this.column = this.next;
      this.next = Column.getRandomColumn();

    }
  }

  private void paint_next(GraphicsContext gc) {
    gc.drawImage(
      Resources.getInstance().getImage("jewels"),
      //imagen original
      this.next.get(0).getType().getX(),
      this.next.get(0).getType().getY(),
      this.next.get(0).getType().getW(),
      this.next.get(0).getType().getH(),
      //posicion en el tablero
      89 * Game.SCALE,
      8 * Game.SCALE,
      this.next.get(0).getType().getW() * Game.SCALE,
      this.next.get(0).getType().getH() * Game.SCALE
    );
    gc.drawImage(
      Resources.getInstance().getImage("jewels"),
      //imagen original
      this.next.get(1).getType().getX(),
      this.next.get(1).getType().getY(),
      this.next.get(1).getType().getW(),
      this.next.get(1).getType().getH(),
      //posicion en el tablero
      89 * Game.SCALE,
      (8 + 16) * Game.SCALE,
      this.next.get(0).getType().getW() * Game.SCALE,
      this.next.get(0).getType().getH() * Game.SCALE
    );
    gc.drawImage(
      Resources.getInstance().getImage("jewels"),
      //imagen original
      this.next.get(2).getType().getX(),
      this.next.get(2).getType().getY(),
      this.next.get(2).getType().getW(),
      this.next.get(2).getType().getH(),
      //posicion en el tablero
      89 * Game.SCALE,
      (8 + 32) * Game.SCALE,
      this.next.get(0).getType().getW() * Game.SCALE,
      this.next.get(0).getType().getH() * Game.SCALE
    );
  }

  private void render() {
    this.clear(ctx);
    this.column.paint(ctx);
    this.paint_next(ctx);
    this.board.paint(ctx);
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  private void clear(GraphicsContext gc) {
    gc.restore();
    gc.clearRect(
      0,
      0,
      this.original_size.getWidth() * Game.SCALE,
      this.original_size.getHeight() * Game.SCALE
    );
  }

  private void paintbackground() {
    this.clear(ctxbackground);

    if (this.ctxbackground != null) this.ctxbackground.drawImage( // las dos imágenes
        Resources.getInstance().getImage("backgrounds"),
        326,
        4,
        this.original_size.getWidth(),
        this.original_size.getHeight(),
        0,
        0,
        this.original_size.getWidth() * Game.SCALE,
        this.original_size.getHeight() * Game.SCALE
      );
  }

  @Override
  public void onKeyPressed(KeyCode code) {
    this.kb.onKeyPressed(code);
    if (code == KeyCode.ADD) {
      Game.clock.incFrecuency();
    }
    if (code == KeyCode.SUBTRACT) {
      Game.clock.decFrencuecy();
    }
    if (code == KeyCode.D) {
      this.setDebug(!this.isDebug());
    }
    if (code == KeyCode.LEFT) {
      this.column.moveLeft(this.board);
    }
    if (code == KeyCode.RIGHT) {
      this.column.moveRight(this.board);
    }
    if (code == KeyCode.DOWN) {
      this.column.moveDown(this.board);
    }
    if (code == KeyCode.UP) {
      this.column.rotate();
    }
    if (code == KeyCode.E) {
      this.board.evaluate();
    }
    if (code == KeyCode.C) {
      this.column = Column.getRandomColumn();
    }
    if (code == KeyCode.S) {
      this.start();
    }
  }

  @Override
  public void onKeyReleased(KeyCode code) {
    this.kb.onKeyReleased(code);
  }

  @Override
  public void debug(GraphicsContext gc) {}
}
