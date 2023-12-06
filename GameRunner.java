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

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
  private Dog dog;

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner()
  {
    setBackground(Color.black);

    keys = new boolean[2];

    //instantiate other instance variables
    //Ship, Alien
    dog = new Dog(250,250,50,50,5);

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
    graphToBack.drawString("StarFighter ", 25, 50 );
    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0,0,800,600);
  
    if (keys[0])
    {
      ship.move("LEFT");
    }
    if (keys[1])
    {
      ship.move("RIGHT");
    }
    if (keys[2])
    {
      ship.move("UP");
    }
      
    //add code to move Ship, Alien, etc.
    dog.draw(graphToBack);
    alienHorde.draw(graphToBack);
    alienHorde.move();
    ammo.draw(graphToBack);
    ammo.move();
    alienAmmo.draw(graphToBack);
    alienAmmo.move();
    graphToBack.setColor(Color.CYAN);
    graphToBack.drawString("Score: " + score, 10, 20);
    graphToBack.drawString("Lives: " + lives, 10, 35);
    
      
    //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
  
  
    for(int i=0; i<alienHorde.getList().size(); i++){
      if(alienHorde.getList().get(i).didCollide(ship)){
        alienHorde.getList().remove(i);
        lives--;
        livesLost = true;
      }
    }
  
    twoDGraph.drawImage(back, null, 0, 0);
  }


  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[4] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_R)
    {
      keys[5] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[4] = false;
      spacePressed = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_R)
    {
      keys[5] = false;
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

