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
  private int fallSpeed;
  private String imgUrl;
  
  private Image image;

  public Dog(){
  this(0,300,40,40,2,100,2,1,"DogPics/GoldenRetriever.png");
  }

  public Dog(int x, int y){
    this(x,y,40,40,2,100,2,1,"DogPics/GoldenRetriever.png");
  }

  public Dog(String imgUrl){
    this(0,300,40,40,2,100,2,1,imgUrl);
  }

    public Dog(int x, int y, int w, int h, int spd, int jH, int jS, int fS, String imgUrl){
        super(x,y,w,h);
        speed = spd;
        jumpHeight = jH;
      jumpSpeed = jS;
      fallSpeed=fS;
      this.imgUrl=imgUrl;
        try{
            URL url = getClass().getResource(imgUrl);
            image = ImageIO.read(url);
        }
        catch (Exception e){
        }
    }

    public void setJumpHeight(int j){
        jumpHeight = j;
    }

  public void setJumpSpeed(int jS){
    jumpSpeed = jS;
  }

  public Image getImage(){
    return image;
  }

  public void setImage(Image i){
    image=i;
  }

  public int getJumpHeight(){
    return jumpHeight;
  }

  public int getJumpSpeed(){
    return jumpSpeed;
  }

  public String getImgUrl(){
    return imgUrl;
  }

  public void setImgUrl(String s){
    imgUrl = s;
    try{
      URL url = getClass().getResource(imgUrl);
      image = ImageIO.read(url);
    }
    catch (Exception e){
    }
  }

    public boolean didCollide(Thing o){
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
      setY(getY()-jumpSpeed);

    else if(direction.equals("DOWN"))
      setY(getY()+fallSpeed);

    else if(direction.equals("RIGHT"))
      setX(getX()+speed);

    else if(direction.equals("LEFT"))
      setX(getX()-speed);
  }

  public boolean broken(){
    return false;
  }

  public void crack(){

  }

  public String toString(){
    return "dog";
  }
}