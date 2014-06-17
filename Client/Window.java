import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.NoSuchFieldException;
import java.lang.IllegalAccessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.imageio.ImageIO;

public final class Window
{
        /*
         * Constants
         */
        private static final String TITLE = "Go-Play-Smile!";
        private static final short WIDTH = 1024, HEIGHT = 768;
        
        /*
         * Immutable fields
         */
        private static final Frame frame = new Frame(TITLE);
        private static final Canvas canvas = new Canvas();
        private static final BufferStrategy bufferStrategy = canvas.getBufferStrategy(); // will be reassigned once later
        
        /*
         * Mutable fields
         */

        /**
         * Prepares the canvas and frame for the game's window.
         */
        public static void setup()
        {
                canvas.setSize(WIDTH, HEIGHT);
                
                frame.add(canvas);
                frame.setResizable(false);
                frame.pack();
                frame.addWindowListener(
                        new WindowAdapter()
                        {
                                @Override
                                public void windowClosing(final WindowEvent e)
                                {
                                        if (This.getUnloading() == true) return;
                                        System.out.println("Unloading...");
                                        This.setUnloading(true);
                                }
                        });
       
                canvas.addKeyListener(
                        new KeyAdapter()
                        {
                                @Override
                                public void keyPressed(final KeyEvent e)
                                {
                                        switch (e.getKeyCode())
                                        {
                                                case KeyEvent.VK_LEFT:
                                                {
                                                        //Input.left((byte)1);
                                                        break;
                                                }
                                                case KeyEvent.VK_UP:
                                                {
                                                        //Input.up((byte)1);
                                                        break;
                                                }
                                                case KeyEvent.VK_RIGHT:
                                                {
                                                        //Input.right((byte)1);
                                                        break;
                                                }
                                                case KeyEvent.VK_DOWN:
                                                {
                                                        //Input.down((byte)1);
                                                        break;
                                                }
                                                default: break;
                                        }
                                }
                                
                                @Override
                                public void keyReleased(final KeyEvent e)
                                {
                                        switch (e.getKeyCode())
                                        {
                                                case KeyEvent.VK_LEFT:
                                                {
                                                        //Input.left((byte)0);
                                                        break;
                                                }
                                                        case KeyEvent.VK_UP:
                                                {
                                                        //Input.up((byte)0);
                                                        break;
                                                }
                                                case KeyEvent.VK_RIGHT:
                                                {
                                                        //Input.right((byte)0);
                                                        break;
                                                }
                                                case KeyEvent.VK_DOWN:
                                                {
                                                        //Input.down((byte)0);
                                                        break;
                                                }
                                                default: break;
                                        }
                                }
                        });

                canvas.requestFocus(); // allow key events to come our way
                canvas.setIgnoreRepaint(true); // do the painting ourselves
                canvas.createBufferStrategy(2); // setup double-buffering
                
                /*
                 * Attempt to set our buffer strategy (we only need to once after setting it up)
                 */
                try
                {
                        final Field tempField = Window.class.getDeclaredField("bufferStrategy");
                        tempField.setAccessible(true);
                        
                        final Field modifiersField = Field.class.getDeclaredField("modifiers");
                        modifiersField.setAccessible(true);
                        modifiersField.setInt(tempField, tempField.getModifiers() & ~Modifier.FINAL);
                        
                        tempField.set(null, canvas.getBufferStrategy());
                }
                catch (IllegalAccessException e)
                {
                        System.out.println(e.getMessage());
                }
                catch (NoSuchFieldException e)
                {
                        System.out.println(e.getMessage());
                }
                
                frame.setVisible(true); // we're ready to show them our stuff (no pun intended)
        }

        /**
         * Draws the visuals of the game onto the window's canvas.
         */
        public static void paint()
        {
                final Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
                g.clearRect(0, 0, WIDTH, HEIGHT);
                
                
                
                g.dispose();
                bufferStrategy.show();
        }
        
        /**
         * Clears and disposes of the window's objects.
         */
        public static void unload()
        {
                bufferStrategy.dispose();
                frame.dispose();
        }
        
        /**
         * Sets the title of the window's frame.
         * 
         * @param title  the title to-be
         */
        public static void setTitle(final String title)
        {
                frame.setTitle(title);
        }
}