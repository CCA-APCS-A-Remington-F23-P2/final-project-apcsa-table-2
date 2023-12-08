import java.awt.Graphics;
import java.awt.Color;

public class Coin extends Thing implements Collideable{

  public Coin(int x, int y, int w, int h){
      super(x,y,w,h);
  }
  public boolean didCollide(Thing o){
    if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
        return true;
      }
      return false;
    }

  public void draw(Graphics window){
    window.setColor(Color.YELLOW);
    window.fillRect(getX(),getY(),getWidth(),getHeight());
  }

  public String toString(){
    return "coin";
  }

  public void move(String direction){
    if(direction.equals("UP"))
      setY(getY()-getSpeed());

    else if(direction.equals("DOWN"))
      setY(getY()+getSpeed());

    else if(direction.equals("RIGHT"))
      setX(getX()+getSpeed());

    else if(direction.equals("LEFT"))
      setX(getX()-getSpeed());
  } 
  
}