//class for generic platform
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Thing implements Collideable{

    public Platform(int x, int y, int w, int h){
        super(x,y,w,h);
    }

    public boolean didCollide(Thing o){
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() == o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

  public void draw(Graphics window){
    window.setColor(Color.BLACK);
    window.fillRect(getX(),getY(),getWidth(),getHeight());
  }

  public String toString(){
    return "platform";
  }
}