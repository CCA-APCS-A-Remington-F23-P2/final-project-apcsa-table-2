public class Coin implements Collideable, Spawnable{

    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Coin(int x, int y, int w, int h){
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

    public boolean didCollide(Object other){
        Dog o = (Dog)other;
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

}