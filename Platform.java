//class for generic platform
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Thing implements Collideable{

    public Platform(int x, int y, int w, int h){
        super(x,y,w,h,1);
    }

    public boolean didCollide(Thing o){
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() == o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

  public void draw(Graphics window){
    window.setColor(Color.WHITE);
    window.fillRect(getX(),getY(),getWidth(),getHeight());
    window.fillOval(getX()-5, getY()-5, 11, 11);
    window.fillOval(getX()-5, getY()+4, 11, 11);
    window.fillOval(getX()+getWidth()-5, getY()-5, 11, 11);
    window.fillOval(getX()+getWidth()-5, getY()+4, 11, 11);
  }

  public String toString(){
    return "platform";
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