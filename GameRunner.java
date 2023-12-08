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
  private boolean rising;
  
  private Dog dog;
  private GameObjects objects;
  private Wallet wallet;

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner(int w,int h)
  {
    screenWidth=w;
    screenHeight=h;
    isJumping=false;
    
    setBackground(Color.black);

    keys = new boolean[2];

    //instantiate other instance variables
    dog = new Dog();
    objects = new GameObjects();
    wallet= new Wallet(screenWidth-60,10);

    //spawn level
    for(int i=screenHeight-50; i>0; i-=50){
      spawnObjs(i);
    }

    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
    paint(window);
  }

  public void paint( Graphics window )
  {
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D)window;
  
    //take a snap shop of the current screen and same it as an image
    //that is the exact same width and height as the current screen
    if (back==null)
      back = (BufferedImage)(createImage(getWidth(),getHeight()));
  
    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();
  
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
    if(!objects.didCollide(dog, "platform")){
      dog.move("DOWN");

      }

      if(objects.didCollide(dog, "coin")){
        wallet.moneyCollect();
      }

      if(objects.didCollide(dog, "obstacle")){
        System.out.println("YOU DIED");
      }

    //make the dog jump if it is touching a platform
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

    if(dog.getY()>500){
      rising = true;
    }
    if(dog.getY()<350){
      rising = false;
    }
    if(rising){
      for(int i=0; i<objects.getList().size(); i++){
        objects.getList().get(i).move("DOWN");
      }
      dog.move("DOWN");
    }
    
    dog.draw(graphToBack);
    objects.draw(graphToBack);
    wallet.draw(graphToBack);
  
    twoDGraph.drawImage(back, null, 0, 0);
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
        objects.add(new Obstacle(randX,yPos+25,25,10));
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
    //no code needed here
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

