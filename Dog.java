import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Dog extends Thing implements Collideable{

    private int speed;
  private int jumpSpeed;
    private int jumpHeight;
    private Image image;

    public Dog(){
    this(0,300,40,40,2,60,2);
    }

    public Dog(int x, int y, int w, int h, int spd, int jH, int jS){
        super(x,y,w,h);
        speed = spd;
        jumpHeight = jH;
      jumpSpeed = jS;
        try{
            URL url = getClass().getResource("DogPics/GermanShepherd.png");
            image = ImageIO.read(url);
        }
        catch (Exception e){
        }
    }

    public void setSpeed(int s){
        speed = s;
    }

    public void setJumpHeight(int j){
        jumpHeight = j;
    }

  public void setJumpSpeed(int jS){
    jumpSpeed = jS;
  }

    public int getSpeed(){
        return speed;
    }

    public int getJumpHeight(){
        return jumpHeight;
    }

  public int getJumpSpeed(){
    return jumpSpeed;
  }

    public boolean didCollide(Object other){
        Platform o = (Platform)other;
        if(this.getX()+this.getWidth() >= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY()+this.getHeight() == o.getY()){
            return true;
        }
        return false;
    }

  public void draw( Graphics window )
  {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public void move(String direction){
    if(direction.equals("UP"))
      setY(getY()-speed);

    else if(direction.equals("DOWN"))
      setY(getY()+speed);

    else if(direction.equals("RIGHT"))
      setX(getX()+speed);

    else if(direction.equals("LEFT"))
      setX(getX()-speed);
  }
}