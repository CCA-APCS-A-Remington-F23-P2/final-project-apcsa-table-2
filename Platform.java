//class for generic platform

public class Platform extends Thing implements Collideable, Spawnable{

    

    public boolean didCollide(Object other){
        Dog o = (Dog)other;
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

}