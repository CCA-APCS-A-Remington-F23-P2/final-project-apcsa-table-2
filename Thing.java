public abstract class Thing{
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Thing(int x, int y, int w, int h){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
    }

    public void setPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void setX(int x){
        xPos = x;
    }

    public void setY(int y){
        yPos = y;
    }

    public void setWidth(int w){
        width = w;
    }

    public void setHeight(int h){
        height = h;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

  public abstract void draw(Graphics window);
}