import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Dog extends Thing implements Collideable{

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int speed;
    private int jumpHeight;
    private Image image;

    public Dog(int x, int y, int w, int h, int spd, int jH){
        super(x,y,w,h);
        speed = spd;
        jumpHeight = jH;
        try{
            URL url = getClass().getResource("DogPics/GoldenRetreiver.png");
            image = Image0.read(url);
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

    public int getSpeed(){
        return speed;
    }

    public int getJumpHeight(){
        return jumpHeight;
    }

    public boolean didCollide(Object other){
        Platform o = (Platform)other;
        if(this.getX()+this.getWidth() >= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY()+this.getHeight() == o.getY()){
            return true;
        }
        return false;
    }

}