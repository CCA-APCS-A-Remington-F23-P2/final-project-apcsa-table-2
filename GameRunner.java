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
import java.io.Writer;
import java.io.FileWriter;
import java.io.File;
//import java.io.IOException;

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
  
  private Dog dog;
  private GameObjects objects;
  private Wallet wallet;
<<<<<<< HEAD
  private Inventory inventory;
  private File coinFile;
  private File inventoryFile;
=======
  private File file;
  private int score = 0;
>>>>>>> 7a48c030a1532b07d13366f91e0822a125e79fa1

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner(int w,int h)
  {
    //only sets vars needed for menu, rest of vars for game are set when space is pressed in the menu
    screenWidth=w;
    screenHeight=h;
    gameRunning=false;
    inventoryOpen=false;
    dog = new Dog();
    
    coinFile = new File("coinData.txt");
    inventoryFile = new File("inventoryData.txt");
    wallet= new Wallet(screenWidth-60,10, coinFile);
    inventory = new Inventory(inventoryFile);
    
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
      graphToBack.setColor(Color.WHITE);
      graphToBack.fillRect(0,0,screenWidth,screenHeight);
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Welcome to the Inventory",screenWidth/4,20);
      graphToBack.fillRect(100,screenHeight-270,100,40);
      graphToBack.fillRect(100,screenHeight-200,100,40);
      graphToBack.setColor(Color.WHITE);
      graphToBack.drawString("START",130,screenHeight-245);
      graphToBack.drawString("MENU",130,screenHeight-175);
    }
    //if it has not, display the main menu
    else{
      graphToBack.setColor(Color.WHITE);
      graphToBack.fillRect(0,0,screenWidth,screenHeight);
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Welcome to Inu Janpu",screenWidth/4,20);
      graphToBack.drawImage(dog.getImage(),screenWidth/4,50,screenWidth/2,screenHeight/3,null);
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
    graphToBack.setColor(Color.CYAN);
    graphToBack.fillRect(0,0,screenWidth,screenHeight);
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
    
    //make dog constantly falling if it is not on a platform
    if(!isJumping){
      dog.move("DOWN");
    }

      if(objects.didCollide(dog, "coin")){
        wallet.collectCoins(1);
      }

    //game over
      if(objects.didCollide(dog, "obstacle") || dog.getY()+dog.getHeight()>=screenHeight){
        gameRunning=false;
        wallet.addCoins();
        score = 0;
      }

    //make the dog jump if it is touching a platform and not currently jumping
    if(objects.didCollide(dog, "platform") && !isJumping)
    {
      isJumping=true;
      initialJumpPos=dog.getY();
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
      int rand = (int)(Math.random()*10);
    int randX = (int)((Math.random()*5)+1)*50;
    
      //spawn platforms at a random xPos
      objects.add(new Platform(randX,yPos,50,10));
      
      //small chance of having either a coin or obstacle spawn in between platforms
    randX = (int)((Math.random()*11)+1)*25;
      if(rand<=2){
        objects.add(new Coin(randX,yPos+10,15,15));
      }
    else if(rand<=4){
      objects.add(new Platform(randX,yPos,26,10));
    }
    //obstacles need to be reworked
    //   else if(rand<=3){
    //     if(randX>=150)
    //       objects.add(new Obstacle(randX,yPos+25,10,10));
    // }
  
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
    //restart game if START is pressed
    if(e.getX()>=100 && e.getX()<=200 && e.getY()>=screenHeight-270 && e.getY()<=screenHeight-240 && !gameRunning){
      gameRunning=true;
      inventoryOpen=false;

      isJumping=false;
      objects = new GameObjects();
      objects.add(new Platform(screenWidth/3+25,dog.getY()+100,50,10));

      dog = new Dog(screenWidth/2,screenHeight-100);
      objects = new GameObjects();

      objects.add(new Platform(screenWidth/2,screenHeight-50,50,10));
      for(int i=screenHeight; i>-50; i-=50){
        spawnObjs(i);
      }
    }

    if(e.getX() >= 210 && e.getX() <= 230 && e.getY() >= 130 && e.getY() <= 160 && !gameRunning){
      //next dog
    }

    if(e.getX() >= 70 && e.getX() <= 90 && e.getY() >= 130 && e.getY() <= 160 && !gameRunning){
      //prev dog
    }
    
    if(e.getX()>=100 && e.getX()<=200 && e.getY()>=screenHeight-200 && e.getY()<=screenHeight-160 && !gameRunning){
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

