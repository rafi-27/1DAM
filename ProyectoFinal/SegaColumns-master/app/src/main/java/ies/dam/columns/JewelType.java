package ies.dam.columns;

public enum JewelType{
    YELLOW(2,1,16,16),
    ORANGE(19,3,16,16),
    GREEN(36,3,16,16),
    VIOLET(54,3,16,16),
    RED(72,3,16,16),
    BLUE(89,3,16,16);

    private final int x,y,w,h;
    private static final int inc_y=18;
   

    private JewelType(int x,int y, int w, int h) {
        this.x = x;
        this.y=y;
        this.w=w;
        this.h=h;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
    /*/ private final double radius; // in meters
    private JewelType(int x,int y,int w,int h){
       /* this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    };*/
    
};