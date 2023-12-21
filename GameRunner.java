//this is where the game will be ran from - most of the Graphics stuff will be here
//using StarFighter Graphics
import java.io.File;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Writer;
import java.io.FileWriter;
import java.io.File;

public class GameRunner extends Canvas implements KeyListener, Runnable, MouseListener
{
  private int screenWidth;
  private int screenHeight;
  private boolean isJumping;
  private int initialJumpPos;
  private boolean gameRunning;
  private boolean inventoryOpen;
  private int screenShiftCount;

  private int[] nextX = {210, 230, 210};
  private int[] nextY = {130, 145, 160};
  private int[] prevX = {90, 70, 90};
  private int[] prevY = {130, 145, 160};
  private boolean nextDog = false;
  private boolean prevDog = false;
  private int index;

  private ArrayList<String> dogs;
  private Dog randomDog;
  private int timer;
  private boolean showRandomDog;

  private Dog dog;
  private GameObjects objects;
  private Wallet wallet;
  private Inventory inventory;

  private int score = 0;

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner(int w,int h)
  {
    //only sets vars needed for menu, rest of vars for game are set when space is pressed in the menu
    screenWidth=w;
    screenHeight=h;
    gameRunning=false;
    inventoryOpen=false;
    showRandomDog=false;
    dog = new Dog();
    
    wallet= new Wallet(screenWidth-60,10, "coinData.txt");
    inventory = new Inventory("inventoryData.txt");
    
    setBackground(Color.black);

    keys = new boolean[2];

    this.addKeyListener(this);
    this.addMouseListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
    paint(window);
  }
  
  public void paint(Graphics window)
  {
    Graphics2D twoDGraph = (Graphics2D)window;
    if (back==null)
      back = (BufferedImage)(createImage(getWidth(),getHeight()));
    Graphics graphToBack = back.createGraphics();

    //if start has been pressed, run game
    if(gameRunning){
      game(graphToBack);
    }
    //if inventory has been opened, display inventory menu
    else if(inventoryOpen){
      try{
        graphToBack.drawImage(ImageIO.read(new File("InuJanpuInventory.png")),0,0, screenWidth, screenHeight, null);
        }
        catch(Exception e){}
      graphToBack.fillRect(90,screenHeight-270,120,40);
      graphToBack.fillRect(100,screenHeight-200,100,40);
      graphToBack.setColor(Color.WHITE);
      graphToBack.drawString("Money: "+wallet.getMoney()+" coins",95, screenHeight-50);
      graphToBack.drawString("ADOPT DOG (50c)",95,screenHeight-245);
      graphToBack.drawString("MENU",130,screenHeight-175);

      List<Dog> list = inventory.getList();
      for(int i=0, x=-50, y=0; i<list.size(); i++, x+=50){
        graphToBack.drawImage(list.get(i).getImage(),x,y,50,75,null);
        if(x+50>=300){
          x=-50;
          y+=75;
        }
      }
      //if random dog is adpoted, show dog and whether to keep or not
      if(showRandomDog){
        graphToBack.drawImage(randomDog.getImage(),25,25,250,400,null);
        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(screenWidth/2-100,screenHeight-175,75,35);
        graphToBack.fillRect(screenWidth/2+25,screenHeight-175,75,35);
        graphToBack.setColor(Color.WHITE);
        graphToBack.drawString("keep <3",screenWidth/2-90,screenHeight-150);
        graphToBack.drawString("return :\'(", screenWidth/2+35, screenHeight-150);
      }
      
    }
    //if it has not, display the main menu
    else{
      try{
      graphToBack.drawImage(ImageIO.read(new File("InuJanpuMainMenu.png")),0,0, screenWidth, screenHeight, null);
      }
      catch(Exception e){}
      //graphToBack.drawString("Money: "+wallet.getMoney()+" coins",95, screenHeight-100);
      graphToBack.drawImage(dog.getImage(),screenWidth/4,110,screenWidth/2,screenHeight/3,null);

      //pulls dogs from inventory
      ArrayList<String> dogs = new ArrayList<String>();
      for(Dog d : inventory.getList()){
        dogs.add(d.getImgUrl());
      }
      
      for(int i = 0; i < dogs.size(); i++){
        if(dogs.get(i).equals(dog.getImgUrl())){
          index = i;
          break;
        }
      }
      if(nextDog == true && dogs.size() > 1){
        if(dogs.get(index).equals(dogs.get(dogs.size()-1))){
           dog.setImgUrl(dogs.get(0));
        }
        else{
          dog.setImgUrl(dogs.get(index + 1));
        }
        nextDog = false;
      }
      if(prevDog == true && dogs.size() > 1){
        if(dogs.get(index).equals(dogs.get(0))){
          dog.setImgUrl(dogs.get(dogs.size()-1));
        }
        else{
          dog.setImgUrl(dogs.get(index - 1));
        }
        prevDog = false;
      }
      graphToBack.fillRect(100,screenHeight-270,100,40);
      graphToBack.fillRect(100,screenHeight-200,100,40);
      graphToBack.fillPolygon(nextX, nextY, 3);
      graphToBack.fillPolygon(prevX, prevY, 3);
      graphToBack.setColor(Color.WHITE);
      graphToBack.drawString("START",130,screenHeight-245);
      graphToBack.drawString("INVENTORY",120,screenHeight-175);
    }
    
    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void game( Graphics graphToBack )
  {
    try{
      graphToBack.drawImage(ImageIO.read(new File("InuJanpuBG.png")),0,0, screenWidth, screenHeight, null);
      }
      catch(Exception e){}
    graphToBack.setColor(Color.BLACK);
    graphToBack.drawString("Score: " + score,screenWidth-80,40);
    
  
    if (keys[0] && dog.getX()>0)
    {
      dog.move("LEFT");
    }
    if (keys[1] && dog.getX()+dog.getWidth()+10<screenWidth)
    {
      dog.move("RIGHT");
    }
    
    //make dog constantly falling if it is not jumping
    if(!isJumping){
      dog.move("DOWN");
    }

    if(objects.didCollide(dog, "coin")){
      wallet.collectCoins(1);
    }

    //game over
    if(dog.getY()+dog.getHeight()>=screenHeight){
      gameRunning=false;
      wallet.save();
      score = 0;
    }

    //make the dog jump if it is touching a platform and not currently jumping
    if(objects.didCollide(dog, "platform") && !isJumping)
    {
      isJumping=true;
      initialJumpPos=dog.getY();
    }
    else if(objects.didCollide(dog, "breakablePlatform") && !isJumping){
      isJumping = true;
      initialJumpPos = dog.getY();
    }
    
    if(initialJumpPos-dog.getY() >= dog.getJumpHeight()){
      isJumping=false;
    }

    if(isJumping){
      dog.move("UP");
    }

    //keeps dog in the bottom 1/3 of the screen and spawns level as it moves up
    if(dog.getY()<screenHeight-250){
      objects.shiftDown(5);
      dog.setY(dog.getY()+5);
      initialJumpPos+=5;
      screenShiftCount++;
      score++;
      if(screenShiftCount==10){
        spawnObjs(0);
        screenShiftCount=0;
      }
    }
    
      dog.draw(graphToBack);
      objects.draw(graphToBack);
      wallet.draw(graphToBack);
      objects.cleanUp(screenHeight);
      objects.moveSmallPlatforms();
  }

  //randomly spawns objs
  public void spawnObjs(int yPos){
      double rand = (Math.random()*10);
    int randX = (int)((Math.random()*5)+1)*50;
    
      //spawn platforms at a random xPos
    if(rand<=7)
      objects.add(new Platform(randX,yPos,50,10));
    else if(rand<9.2)
      objects.add(new Platform(randX,yPos,25,10));
    else
      objects.add(new BreakablePlatform(randX,yPos,50,10));
      
      //small chance of having either a coin or obstacle spawn in between platforms
    randX = (int)((Math.random()*11)+1)*25;
      if(rand<=2){
        objects.add(new Coin(randX,yPos+10,15,15));
      }
  
  }

  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_A)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_D)
    {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_A)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_D)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e)
  {
  }

  public void mouseClicked(MouseEvent e){
    
    //start game if START is pressed
    if(e.getX()>=100 && e.getX()<=200 && e.getY()>=screenHeight-270 && e.getY()<=screenHeight-240 && !gameRunning && !inventoryOpen){
      gameRunning=true;
      inventoryOpen=false;

      isJumping=false;
      objects = new GameObjects();
      objects.add(new Platform(screenWidth/3+25,dog.getY()+100,50,10));

      dog = new Dog(screenWidth/2,screenHeight-100, 40, 40, 2, 100, 2, 1, dog.getImgUrl());
      objects = new GameObjects();

      objects.add(new Platform(screenWidth/2,screenHeight-50,50,10));
      for(int i=screenHeight-50; i>-50; i-=50){
        spawnObjs(i);
      }
    }

    //calls code to adopt a random dog
    if(e.getX()>=90 && e.getX()<=210 && e.getY()>=screenHeight-270 && e.getY()<=screenHeight-240 && !gameRunning && inventoryOpen && wallet.getMoney()>=50 && !showRandomDog){
      showRandomDog=true;
      randomDog=inventory.adoptRandomDog();
      wallet.spendMoney(50);
      wallet.save();
    }
    
    //keep dog button
    if(e.getX()>=50 && e.getX()<=125 && e.getY()>=screenHeight-175 && e.getY()<=screenHeight-140 && showRandomDog){
      inventory.keepDog(randomDog);
      showRandomDog=false;
    }
    
    //return dog button
    if(e.getX()>=175 && e.getX()<=250 && e.getY()>=screenHeight-175 && e.getY()<=screenHeight-140 && showRandomDog){
      showRandomDog=false;
      wallet.spendMoney(-5);
    }

    if(e.getX() >= 210 && e.getX() <= 230 && e.getY() >= 130 && e.getY() <= 160 && !gameRunning){
      nextDog = true;
    }

    if(e.getX() >= 70 && e.getX() <= 90 && e.getY() >= 130 && e.getY() <= 160 && !gameRunning){
      prevDog = true;
    }

    //toggles between inventory and main menu
    if(e.getX()>=100 && e.getX()<=200 && e.getY()>=screenHeight-200 && e.getY()<=screenHeight-160 && !gameRunning && !showRandomDog){
      inventoryOpen=!inventoryOpen;
    }
      
  }
  public void mouseExited(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mousePressed(MouseEvent e){}
  public void mouseDragged(MouseEvent e){}
  public void mouseMoved(MouseEvent e){}

  public void run()
  {
    try
    {
      while(true)
      {
        Thread.currentThread().sleep(5);
        repaint();
      }
    }catch(Exception e)
    {
    }
  }
}

