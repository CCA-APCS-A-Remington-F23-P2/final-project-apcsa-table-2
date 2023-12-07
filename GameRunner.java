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
    spawnObjs();
    wallet= new Wallet(screenWidth-60,10);
    //test platform
    objects.add(new Platform(0,400,300,10));

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
    if(!objects.didCollide(dog, "platform"))
      dog.move("DOWN");

    //make the dog jump if it is touching a platform
    if(objects.didCollide(dog, "platform"))
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
    
    dog.draw(graphToBack);
    objects.draw(graphToBack);
    wallet.draw(graphToBack);
  
    twoDGraph.drawImage(back, null, 0, 0);
  }

  //randomly spawns objs, platforms are weighted more than coins or obstacle
  public void spawnObjs(){
    for(int i=0; i<3; i++){
      int rand = (int)(Math.random()*10);
      if(rand<=6){
        objects.add(new Platform(10,10,100,10));
      }
      else if(rand<=8){
        objects.add(new Coin(10,30,10,10));
      }
      else{
        objects.add(new Obstacle(10,50,30,10));
      }
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

