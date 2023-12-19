import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

//arraylist of all Things on the screen
public class GameObjects
{
  private List<Thing> objects;

  public GameObjects(){
    objects = new ArrayList<Thing>();
  }

  public void add(Thing obj){
    objects.add(obj);
  }

  public void draw(Graphics window){
    for(Thing obj : objects){
      obj.draw(window);
    }
  }

  public List<Thing> getList(){
    return objects;
  }

  public boolean didCollide(Thing o, String objType){
    for(int i=0; i<objects.size(); i++){
      if(objects.get(i).didCollide(o) && objects.get(i).toString().equals(objType)){
        if(objType.equals("coin"))
          objects.remove(i);
        if(objType.equals("breakablePlatform")){
          objects.get(i).crack();
          if(objects.get(i).broken())
            objects.remove(i);
        }
        return true;
      }
    }
    return false;
  }

  public void move(String direction){
    for(Thing obj : objects){
      obj.move(direction);
    }
  }

  public void moveSmallPlatforms(){
    for(Thing obj : objects){
      if(obj.getWidth()==25){
        obj.move("LEFT");
        if(obj.getX()<0||obj.getX()>270)
          obj.setSpeed(-1*obj.getSpeed());
      }
    }
  }

  public void shiftDown(int amount){
    for(Thing obj : objects){
      obj.setY(obj.getY()+amount);
    }
  }

  public void cleanUp(int screenHeight){
    for(int i=0; i<objects.size(); i++){
      if(objects.get(i).getY()>=screenHeight-30)
        objects.remove(i);
    }
  }

  //for bugtesting
  public String toString(){
    String output="";
    for(Object o : objects){
      output+=o.toString()+" ";
    }
    return output;
  }
  
}