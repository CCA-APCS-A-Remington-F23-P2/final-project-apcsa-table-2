import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Dog implements Collideable{

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int speed;
    private int jumpHeight;
    private Image image;

    public Dog(int x, int y, int w, int h, int spd, int jH){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        speed = spd;
        jumpHeight = jH;
        try{
            URL url = getClass().getResource("DogPics/GoldenRetreiver.png");
            image = Image0.read(url);
        }
        catch (Exception e){
        }
    }

    public void setPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void setX(int x){
        xPos = x;
    }

    public void setY(int y){
        yPos = y;
    }

    public void setWidth(int w){
        width = w;
    }

    public void setHeight(int h){
        height = h;
    }

    public void setSpeed(int s){
        speed = s;
    }

    public void setJumpHeight(int j){
        jumpHeight = j;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
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