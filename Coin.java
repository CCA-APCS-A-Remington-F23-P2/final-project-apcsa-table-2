public class Coin implements Collideable, Spawnable{



    public Coin(){

    }

    public boolean didCollide(Object other){
        Dog o = (Dog)other;
        if(this.getX() + this.getWidth()>= o.getX() && this.getX() <= o.getX()+o.getWidth() && this.getY() + this.getHeight() >=o.getY() && this.getY()<=o.getY()+o.getHeight()){
            return true;
          }
        return false;
    }

}