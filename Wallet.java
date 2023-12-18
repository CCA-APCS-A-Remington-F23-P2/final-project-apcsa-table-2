import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.Writer;
import java.io.FileWriter;
import java.util.Scanner;

public class Wallet{
  private int money;
  private int xPos;
  private int yPos;
  private int coins;
  Writer writer;
  Scanner scanner;
  String fileName;

  //coins is coins collected in current game, money is total coins (persistent)
  public Wallet(int x, int y, String f){
    fileName = f;
    try{
      scanner= new Scanner(new File(fileName));
    }
    catch(Exception e){}
    
    xPos = x;
    yPos = y;
    
    try{
      money = scanner.nextInt();
    }
    catch(Exception e){}
    
    coins=0;
  }

  public void setPos(int x, int y){
    xPos = x;
    yPos = y;
  }

  public void addCoins(){
    money+=coins;
    try{
      writer = new FileWriter(new File(fileName));
      writer.write(money+"");
      writer.flush();
    }
    catch(Exception e){}
    coins=0;
  }

  public int getMoney(){
    return money;
  }

  public void spendMoney(int m){
    money-=m;
  }

  public void collectCoins(int c){
    coins+=c;
  }

  public void draw(Graphics window){
    window.setColor(Color.WHITE);
    window.drawRect(xPos-8, yPos-16, 20, 20);
    window.fillRect(xPos-8, yPos-16, 20, 20);
    window.setColor(Color.BLACK);
    window.drawString(coins + " Coins", xPos, yPos);
  }
  
}