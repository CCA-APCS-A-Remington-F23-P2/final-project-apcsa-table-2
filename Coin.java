import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Coin extends Thing implements Collideable{

  private Image image;

  public Coin(int x, int y, int w, int h){
      super(x,y,w,h,1);
    try{
      URL url = getClass().getResource("CoinPic.png");
      image = ImageIO.read(url);
    }
    catch (Exception e){
    }
  }
  public boolean didCollide(Thing o){
    if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
        return true;
      }
      return false;
    }

  public void draw(Graphics window){
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
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

  public boolean broken(){
    return false;
  }

  public void crack(){

  }
  
}