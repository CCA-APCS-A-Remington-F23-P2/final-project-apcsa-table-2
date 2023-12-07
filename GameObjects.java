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
  
}