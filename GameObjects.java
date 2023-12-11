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

  public void shiftDown(int amount){
    for(Thing obj : objects){
      obj.setY(obj.getY()+amount);
    }
  }
  
}