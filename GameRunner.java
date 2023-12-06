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
  private Dog dog;

  private boolean[] keys;
  private BufferedImage back;

  public GameRunner()
  {
    setBackground(Color.black);

    keys = new boolean[2];

    //instantiate other instance variables
    dog = new Dog();

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
    graphToBack.fillRect(0,0,200,400);
  
    if (keys[0])
    {
      dog.move("LEFT");
    }
    if (keys[1])
    {
      dog.move("RIGHT");
    }
      
    //add code to move Ship, Alien, etc.
    dog.draw(graphToBack);
    
      
    //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
  
  
  
    twoDGraph.drawImage(back, null, 0, 0);
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

