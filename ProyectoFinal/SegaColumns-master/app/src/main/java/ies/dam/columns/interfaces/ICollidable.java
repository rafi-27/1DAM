package ies.dam.columns.interfaces;


import javafx.geometry.Rectangle2D;

public interface ICollidable {
    public Rectangle2D getRectangle2D();
    public default boolean collide (Object o){
        if (! (o instanceof ICollidable))
            return false;
        else{
            Boolean r=this.getRectangle2D().intersects(4, 4,50,250);//.intersects(getRectangle2D());
           // System.out.println(r);
           // System.out.println(this);
           // System.out.println(o);
          //  System.out.println("----------------------");
         return r;
        }

    }

    
}
