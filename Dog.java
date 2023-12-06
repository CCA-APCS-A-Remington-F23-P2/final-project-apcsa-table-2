public class Dog implements Collideable{
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int speed;
    private int jumpHeight;

    public Dog(int x, int y, int w, int h, int spd, int jH){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        speed = spd;
        jumpHeight = jH;
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

    public boolean didCollidePlatform(Object other){
        Platform o = (Platform)other;
        return false;
    }
    
}