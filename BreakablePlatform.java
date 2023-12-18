//class for generic platform
import java.awt.Graphics;
import java.awt.Color;

public class BreakablePlatform extends Platform{

  public int hp=3;
    public BreakablePlatform(int x, int y, int w, int h){
        super(x,y,w,h);
    }
  public void crack(){
    hp--;
  }
  public boolean broken(){
    return (hp<1);
  }
  public void draw(Graphics window){
    if(hp==3)
      window.setColor(Color.yellow);
    else if(hp==2)
      window.setColor(Color.orange);
    else
      window.setColor(Color.red);
    window.fillRect(getX(),getY(),getWidth(),getHeight());
    window.fillOval(getX()-5, getY()-5, 11, 11);
    window.fillOval(getX()-5, getY()+4, 11, 11);
    window.fillOval(getX()+getWidth()-5, getY()-5, 11, 11);
    window.fillOval(getX()+getWidth()-5, getY()+4, 11, 11);
  }

  public String toString(){
    return "breakablePlatform";
  }
}