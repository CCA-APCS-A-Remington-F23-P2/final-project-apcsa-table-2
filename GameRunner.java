//this is where the game will be ran from - most of the Graphics stuff will be here
//using StarFighter Graphics
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameRunner extends Canvas implements KeyListener, Runnable
{
  private int screenWidth;
  private int screenHeight;
  private boolean isJumping;
  private int initialJumpPos;
  private boolean gameRunning;
  private boolean inventoryOpen;
  
  private Dog dog;
  private GameObjects objects;
  private Wallet wallet;

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner(int w,int h)
  {
    //only sets vars needed for menu, rest of vars for game are set when space is pressed in the menu
    screenWidth=w;
    screenHeight=h;
    gameRunning=false;
    inventoryOpen=false;
    
    setBackground(Color.black);

    keys = new boolean[2];

    this.addKeyListener(this);
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
      graphToBack.drawString("Inventory is WIP",0,30);
      graphToBack.drawString("Press Space to Play",0,60);
      graphToBack.drawString("Press M for Main Menu",0,90);
    }
    //if it has not, display the main menu
    else{
      graphToBack.setColor(Color.WHITE);
      graphToBack.fillRect(0,0,screenWidth,screenHeight);
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Welcome to Inu Janpu",0,30);
      graphToBack.drawString("Press Space to Play",0,60);
      graphToBack.drawString("Press M for Inventory",0,90);
    }
    
    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void game( Graphics graphToBack )
  {
    graphToBack.setColor(Color.CYAN);
    graphToBack.fillRect(0,0,screenWidth,screenHeight);
  
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
        wallet.moneyCollect();
      }

    //game over
      if(objects.didCollide(dog, "obstacle") || dog.getY()+dog.getHeight()>=screenHeight){
        gameRunning=false;
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
      objects.shiftDown(50);
      dog.setY(dog.getY()+50);
      initialJumpPos+=50;
      spawnObjs(50);
    }
    
      dog.draw(graphToBack);
      objects.draw(graphToBack);
      wallet.draw(graphToBack);
      objs.cleanUp(screenHeight);
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
        objects.add(new Coin(randX,yPos+10,10,10));
      }
      else if(rand<=3){
        if(randX>=150)
          objects.add(new Obstacle(randX,yPos+25,10,10));
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
    // if space is pressed in menu, make new game
    if(e.getKeyChar()==' ' && !gameRunning){
      gameRunning=true;

      isJumping=false;
      dog = new Dog(screenWidth/2,screenHeight/2);
      objects = new GameObjects();
      wallet= new Wallet(screenWidth-60,10);
      
      for(int i=screenHeight-50; i>0; i-=50){
        spawnObjs(i);
      }
    }

    //toggles between inventory and MM
    if(e.getKeyChar()=='m' && !gameRunning){
      inventoryOpen=!inventoryOpen;
    }
  }

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

