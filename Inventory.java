import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

public class Inventory
{
  private List<Dog> dogList;
  private String[] dogArr = {"DogPics/AustralianShepherd.png", "DogPics/Borzoi.png", "DogPics/Corgi.png", "DogPics/Dachshund.png", "DogPics/Dalmatian.png", "DogPics/GermanShepherd.png", "DogPics/Husky.png", "DogPics/Iggy.png", "DogPics/Pomeranian.png", "DogPics/Poodle.png", "DogPics/Pug.png", "DogPics/ShibaInu.png"};
  private double[] weightArr = {0.7, 0.08, 0.4, 0.4, 0.5, 0.9, 0.4, 0.01, 0.3, 0.7, 0.08, 0.08};
  private int cardPrice;
  
  private File file;
  private Scanner scanner;
  private Writer writer;

  public Inventory(File f){
    file = f;
    dogList= new ArrayList<Dog>();

    try{
    scanner = new Scanner(file);
    writer = new FileWriter(file);
    }
    catch(Exception e){}

    //initialize list w/ data from file
    while(scanner.hasNext()){
      dogList.add(new Dog(scanner.next()));
    }
  }

  public void saveInventory(){
    //make str representation of dogs
    String str="";
    for(Dog d : dogList){
      str+=d.getImgUrl()+" ";
    }

    //overwrite current data w/ new data
    try{
      file.delete();
      file = new File("inventoryData.txt");
      writer = new FileWriter(file);
      writer.write(str);
      writer.flush();
    }
    catch(Exception e){}
  }

  public void openCard(){

    //calculate total weight
    double totalWeight=0.0;
    for(double i : weightArr)
      totalWeight+=i;

    //choose random item
    int index=0;
    for(double r= Math.random()*totalWeight; index<dogArr.length-1; ++index){
      r-=weightArr[index];
      if(r<=0.0)
        break;
    }

    //check if dog is duplicate, if it is not, add to list and write
    boolean isDup=false;
    for(Dog d: dogList){
      if(d.getImgUrl().equals(dogArr[index]))
        isDup=true;
    }
    if(!isDup){
      dogList.add(new Dog(dogArr[index]));
      saveInventory();
    }
  }
  
}