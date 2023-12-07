import java.awt.Graphics;
import java.awt.Color;

public class Wallet{
  private int money;
  private int xPos;
  private int yPos;

  public Wallet(int x, int y){
    xPos = x;
    yPos = y;
    money = 0;
  }

  public void setPos(int x, int y){
    xPos = x;
    yPos = y;
  }

  public void moneyCollect(){
    money++;
  }

  public void getMoney(){
    return money;
  }

  public void draw(Graphics window){
    window.setColor(Color.WHITE);
    window.drawRect(xPos-8, yPos-16, 20, 20);
    window.fillRect(xPos-8, yPos-16, 20, 20);
    window.setColor(Color.BLACK);
    window.drawString(money + " Coins", xPos, yPos);
  }
  
}