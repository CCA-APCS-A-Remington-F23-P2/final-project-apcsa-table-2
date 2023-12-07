//Using StarFighter Graphics
import javax.swing.JFrame;
import java.awt.Component;

public class InuJanpu extends JFrame
{
  private static final int WIDTH = 300;
  private static final int HEIGHT = 550;

  public InuJanpu()
  {
    super("INU JANPU");
    setSize(WIDTH,HEIGHT);

    GameRunner theGame = new GameRunner(WIDTH,HEIGHT);
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