//Using StarFighter Graphics
import javax.swing.JFrame;
import java.awt.Component;

public class StarFighter extends JFrame
{
  private static final int WIDTH = 200;
  private static final int HEIGHT = 400;

  public StarFighter()
  {
    super("INU JANPU");
    setSize(WIDTH,HEIGHT);

    GameRunner theGame = new GameRunner();
    ((Component)theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main( String args[] )
  {
    InuJanpu run = new InuJanpu();
  }
}