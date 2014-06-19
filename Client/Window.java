import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.font.LineBreakMeasurer;
import java.io.File;
import java.io.IOException;
import java.lang.NoSuchFieldException;
import java.lang.IllegalAccessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.Random;
import javax.imageio.ImageIO;

final class Window
{
        /*
         * Constants
         */
        static final byte N_SIDED_DIE = 6;
        private static final String TITLE = "Go-Play-Smile!";
        private static final short WIDTH = 1024, HEIGHT = 768;
        private static final short CENTER_X = WIDTH >> 1, CENTER_Y = HEIGHT >> 1;
        private static final Font FONT = new Font("SansSerif", Font.PLAIN, 24);
        
        /*
         * Immutable fields
         */
        private static final Frame frame = new Frame(TITLE);
        private static final Canvas canvas = new Canvas();
        private static final BufferStrategy bufferStrategy = null; // will be reassigned once later
        private static final BufferedImage[] die = new BufferedImage[N_SIDED_DIE];
        private static final BufferedImage gpsScreen = null; // will be reassigned once later
        private static final Random random = new Random();
        
        /*
         * Mutable fields
         */
        private static boolean showFpsCpsAndMouseCoordinates;
        private static byte instructionCard = (byte)random.nextInt(This.INSTRUCTION_CARDS.length);
        private static byte dieSpinTimer, dieFrame, dieStopCounter;
        private static boolean showDieRoll, dieThrown;
        private static byte dieSide = -1;

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
                                                case KeyEvent.VK_F1:
                                                {
                                                        showFpsCpsAndMouseCoordinates = true;
                                                        break;
                                                }
                                                case KeyEvent.VK_SPACE:
                                                {
                                                        showDieRoll = true;
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
                                                case KeyEvent.VK_F1:
                                                {
                                                        showFpsCpsAndMouseCoordinates = false;
                                                        break;
                                                }
                                                case KeyEvent.VK_SPACE:
                                                {
                                                        showDieRoll = false;
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
                                public void mouseClicked(MouseEvent e)
                                {
                                        if (showDieRoll) dieThrown = true;
                                }
                        });

                canvas.requestFocus(); // allow key events to come our way
                canvas.setIgnoreRepaint(true); // do the painting ourselves
                canvas.createBufferStrategy(2); // setup double-buffering
                
                // attempt to set our buffer strategy (we only need to once after setting it up)
                setPrivateStaticFinal("bufferStrategy", canvas.getBufferStrategy());
                
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
                        
                        setPrivateStaticFinal("gpsScreen", ImageIO.read(new File("Images/GPS_Screen.png")));
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

                if (showFpsCpsAndMouseCoordinates) paintFpsCpsAndMouseCoordinates(g);
                if (showDieRoll) paintDieRoll(g);

                g.dispose();
                bufferStrategy.show();
        }
        
        /**
         * Displays the FPS, CPS, and mouse coordinates onto the screen.
         * 
         * @param g  the handle to the graphical device
         */
        private static void paintFpsCpsAndMouseCoordinates(final Graphics2D g)
        {
                g.drawString(
                        "FPS: " + This.getFps() + " | CPS: " + NumberFormat.getIntegerInstance().format(This.getCps()),
                        2, 21);
                g.drawString("(Mouse) X: " + getMouseX() + " | (Mouse) Y: " + getMouseY(), 2, 45);
        }
        
        /**
         * Displays the entire die rolling scene.
         * 
         * @param g  the handle to the graphical device
         */
        private static void paintDieRoll(final Graphics2D g)
        {
                final short GPS_SCREEN_X = (short)(CENTER_X - gpsScreen.getWidth() / 2);
                final short GPS_SCREEN_Y = (short)(CENTER_Y - gpsScreen.getHeight() / 2);
                
                g.drawImage(gpsScreen, GPS_SCREEN_X, GPS_SCREEN_Y, null);
                g.setPaint(Color.WHITE);
                
                for (byte l = 0; l < This.INSTRUCTION_CARDS[instructionCard].length; ++l)
                {
                        g.drawString(
                                This.INSTRUCTION_CARDS[instructionCard][l], GPS_SCREEN_X + 75,
                                GPS_SCREEN_Y + 175 + l * 24);
                }
                
                g.setPaint(Color.BLACK);
                
                final short dieX, dieY;
                
                if (!dieThrown)
                {
                        dieX = (short)(getMouseX() - die[dieFrame].getWidth() / 2);
                        dieY = (short)(getMouseY() - die[dieFrame].getHeight() / 2);
                }
                else dieY = dieX = 0;
                
                g.drawImage(die[dieFrame], dieX, dieY, null);

                /*
                 * Stop the die at some point eventually
                 */
                if (dieStopCounter >= 100)
                {
                        if (dieSide < 0 || dieSide > N_SIDED_DIE - 1)
                        {
                                dieSide = dieFrame;
                                System.out.println("dieSide = " + (dieSide + 1));
                        }
                        return;
                }
                
                /*
                 * Animate the spinning die once every 6 frames (minimum)
                 */
                if (++dieSpinTimer >= 6 + (dieThrown ? dieStopCounter : 0))
                {
                        final byte nextFrame = (byte)random.nextInt(N_SIDED_DIE);
                        dieFrame = dieFrame == nextFrame ? (byte)((nextFrame + 1) % N_SIDED_DIE) : nextFrame;
                        dieSpinTimer = 0;
                        
                        if (!dieThrown) return;
                        dieStopCounter += (dieStopCounter >> 2) + random.nextInt(10) + 1;
                }
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
 
        /**
         * If possible, a private static final field's set value is changed.
         * 
         * @param field     the name of the field to change
         * @param newValue  the desired new value of the field
         */
        private static void setPrivateStaticFinal(final String field, final Object newValue)
        {
                try
                {
                        final Field f = Window.class.getDeclaredField(field);
                        f.setAccessible(true);
                        
                        final Field modifiersField = Field.class.getDeclaredField("modifiers");
                        modifiersField.setAccessible(true);
                        modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                        
                        f.set(null, newValue);
                }
                catch (IllegalAccessException e)
                {
                        System.out.println(e.getMessage());
                }
                catch (NoSuchFieldException e)
                {
                        System.out.println(e.getMessage());
                }
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