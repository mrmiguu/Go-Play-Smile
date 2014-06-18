import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.NoSuchFieldException;
import java.lang.IllegalAccessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.imageio.ImageIO;

final class Window
{
        /*
         * Constants
         */
        private static final String TITLE = "Go-Play-Smile!";
        private static final short WIDTH = 1024, HEIGHT = 768;
        private static final Font FONT = new Font("SansSerif", Font.PLAIN, 24);
        static final byte N_SIDED_DIE = 6;
        
        /*
         * Immutable fields
         */
        private static final Frame frame = new Frame(TITLE);
        private static final Canvas canvas = new Canvas();
        private static final BufferStrategy bufferStrategy = canvas.getBufferStrategy(); // will be reassigned once later
        private static final BufferedImage[] die = new BufferedImage[N_SIDED_DIE];
        
        /*
         * Mutable fields
         */
        private static byte dieTimer, dieCounter;

        /**
         * Prepares the canvas and frame for the game's window.
         */
        static void setup()
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
                        
                canvas.addMouseListener(
                        new MouseAdapter()
                        {
                                @Override
                                public void mouseEntered(final MouseEvent e)
                                {
                                        
                                }
                                
                                @Override
                                public void mouseExited(final MouseEvent e)
                                {
                                        
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
                load();
        }
        
        private static void load()
        {
                try
                {
                        for (byte s = (byte)(die.length - 1); s >= 0; --s)
                        {
                                die[s] = ImageIO.read(new File("Images/Die/" + (s + 1) + ".png"));
                        }
                }
                catch (IOException e)
                {
                        System.out.println(e.getMessage());
                }
        }

        /**
         * Draws the visuals of the game onto the window's canvas.
         */
        static void paint()
        {
                final Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
                g.clearRect(0, 0, WIDTH, HEIGHT);
                g.setFont(FONT);
                g.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                
                //final Point m = canvas.getMousePosition();
                
                g.drawString("FPS: " + This.getFps() + " | CPS: " + This.getCps(), 2, 21); // top-left corner
                g.drawString("(Mouse) X: " + getMouseX() + " | (Mouse) Y: " + getMouseY(), 2, 45);
                
                g.drawImage(
                        die[dieCounter], getMouseX() - die[dieCounter].getWidth() / 2,
                        getMouseY() - die[dieCounter].getHeight() / 2, null);
                
                /*
                 * Animate the die once every 4 frames
                 */
                if (++dieTimer >= 4)
                {
                        dieCounter = (byte)((dieCounter + 1) % N_SIDED_DIE);
                        dieTimer = 0;
                }
                
                g.dispose();
                bufferStrategy.show();
        }
        
        /**
         * Clears and disposes of the window's objects.
         */
        static void unload()
        {
                for (byte s = 0; s < die.length; s++)
                {
                        die[s].flush();
                        die[s] = null;
                }
                
                bufferStrategy.dispose();
                frame.dispose();
        }
        
        private static short getMouseX()
        {
                return (short)(MouseInfo.getPointerInfo().getLocation().getX() - canvas.getLocationOnScreen().getX());
        }
        
        private static short getMouseY()
        {
                return (short)(MouseInfo.getPointerInfo().getLocation().getY() - canvas.getLocationOnScreen().getY());
        }
}