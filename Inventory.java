import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;
import java.awt.Image;

public class Inventory implements Persistent
{
  private List<Dog> dogList;
  private String[] nameArr = {"DogPics/GoldenRetriever.png","DogPics/AustralianShepherd.png", "DogPics/Borzoi.png", "DogPics/Corgi.png", "DogPics/Dachshund.png", "DogPics/Dalmatian.png", "DogPics/GermanShepherd.png", "DogPics/Husky.png", "DogPics/Iggy.png", "DogPics/Pomeranian.png", "DogPics/Poodle.png", "DogPics/Pug.png", "DogPics/ShibaInu.png"};
  private double[] weightArr = {0.8,0.6, 0.08, 0.4, 0.4, 0.5, 0.8, 0.4, 0.01, 0.3, 0.7, 0.08, 0.08};
  private int cardPrice;
  
  private String fileName;
  private Scanner scanner;
  private Writer writer;

  public Inventory(String f){
    fileName = f;
    dogList= new ArrayList<Dog>();

    try{
    scanner = new Scanner(new File(fileName));
      
      //initialize list w/ data from file
      while(scanner.hasNext()){
        dogList.add(new Dog(scanner.next()));
      }
      
    //writer = new FileWriter(new File(fileName));
    }
    catch(Exception e){}
  }

  public List<Dog> getList(){
    return dogList;
  }

  public void save(){
    //make str representation of dogList
    String str="";
    for(Dog d : dogList){
      str+=" "+d.getImgUrl();
    }

    //overwrite current data w/ new data
    try{
      writer = new FileWriter(new File(fileName));
      writer.write(str);
      writer.flush();
    }
    catch(Exception e){}
  }

  public Dog adoptRandomDog(){
    List<String> weightedDogList = new ArrayList<String>();

    //populate list
    for(int i=0; i<weightArr.length;i++){
      int weight=(int)(weightArr[i]*100);
      for(int w=0; w<weight; w++){
        weightedDogList.add(nameArr[i]);
      }
    }
    //choose random index
    int index=(int)(Math.random()*weightedDogList.size());
    
    return new Dog(weightedDogList.get(index));
  }

  public void keepDog(Dog randDog){
    //check if dog is duplicate, if it is not, add to list and write
    boolean isDup=false;
    for(Dog d: dogList){
      if(d.getImgUrl().equals(randDog.getImgUrl()))
        isDup=true;
    }
    if(!isDup){
      dogList.add(randDog);
      save();
    }
  }
  
}