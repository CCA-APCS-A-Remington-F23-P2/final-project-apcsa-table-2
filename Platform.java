//class for generic platform
import java.awt.Graphics;

public class Platform extends Thing implements Collideable, Spawnable{

    public Platform(int x, int y, int w, int h){
        super(x,y,w,h);
    }

    public boolean didCollide(Object other){
        Dog o = (Dog)other;
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

  public void draw(Graphics window){
    window.fillRect(getX(),getY(),getWidth(),getHeight());
  }

}