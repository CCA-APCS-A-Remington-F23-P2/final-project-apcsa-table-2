public class Coin extends Thing implements Collideable, Spawnable{

    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Coin(int x, int y, int w, int h){
        super(x,y,w,h);
    }

    public boolean didCollide(Object other){
        Dog o = (Dog)other;
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

}