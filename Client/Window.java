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
import java.util.Stack;
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
        private static final Short GPS_SCREEN_X = null, GPS_SCREEN_Y = null;
        private static final Font FONT = new Font("SansSerif", Font.PLAIN, 24);
        
        /*
         * Immutable fields
         */
        private static final Frame frame = new Frame(TITLE);
        private static final Canvas canvas = new Canvas();
        private static final BufferStrategy bufferStrategy = null; // reassigned later
        private static final BufferedImage[] die = new BufferedImage[N_SIDED_DIE];
        private static final BufferedImage gpsScreen = null; // reassigned later
        private static final BufferedImage map = null; // reassigned later
        private static final Random random = new Random();
        private static final Stack<Byte> newDeck = new Stack(), oldDeck = new Stack();
        
        /*
         * Mutable fields
         */
        private static boolean showFpsCpsAndMouseCoordinates;
        private static byte instructionCard =
                (byte)random.nextInt(This.INSTRUCTION_CARDS.length);
        private static byte dieSpinTimer, dieFrame, dieStopCounter;
        private static boolean showDieRoll, dieThrown;
        private static byte dieSide = -1;
        private static boolean showMap = true;

        /**
         * Prepares the canvas and frame for the game's window.
         */
        static void setup()
        {
            
                for (byte i = 0; i < This.INSTRUCTION_CARDS.length; ++i)
                        newDeck.push(i);
                        
                shuffleDeck(This.INSTRUCTION_CARDS);
                        
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
                                                        showFpsCpsAndMouseCoordinates =
                                                                true;
                                                        break;
                                                }
                                                case KeyEvent.VK_SPACE:
                                                {
                                                        if (showDieRoll) resetDieRoll();
                                                        showDieRoll = !showDieRoll;
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
                                                        showFpsCpsAndMouseCoordinates =
                                                                false;
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
                                public void mouseClicked(final MouseEvent e)
                                {
                                        if (showDieRoll)
                                        {
                                                dieThrown = true;
                                        }
                                }
                        });
                canvas.setIgnoreRepaint(true); // do the painting ourselves
                canvas.createBufferStrategy(2); // setup double-buffering
                
                // try re-setting our buffer strategy since we'll only ever need to once
                General.setPrivateStaticFinal(
                        Window.class, "bufferStrategy", canvas.getBufferStrategy());

                load();
                frame.setVisible(true);
                System.out.println(
                    "canvas.requestFocusInWindow() = "
                    + canvas.requestFocusInWindow());
        }
        
        /**
         * Load in all buffered images from their respective locations.
         */
        private static void load()
        {
                try
                {
                        for (byte s = (byte)(die.length - 1); s >= 0; --s)
                        {
                                die[s] =
                                        ImageIO.read(
                                                new File("Images/Die/" + (s + 1) + ".png"));
                        }
                        
                        General.setPrivateStaticFinal(
                                Window.class, "gpsScreen",
                                ImageIO.read(new File("Images/GPS_Screen.png")));
                        General.setPrivateStaticFinal(
                                Window.class, "GPS_SCREEN_X",
                                (short)(CENTER_X - gpsScreen.getWidth() / 2));
                        General.setPrivateStaticFinal(
                                Window.class, "GPS_SCREEN_Y",
                                (short)(CENTER_Y - gpsScreen.getHeight() / 2));
                        General.setPrivateStaticFinal(
                                Window.class, "map",
                                ImageIO.read(new File("Images/Map.png")));
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

                if (showMap) paintMap(g);
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
                        "FPS: " + This.getFps() + " | CPS: "
                        + NumberFormat.getIntegerInstance().format(This.getCps()),
                        2, 21);
                g.drawString(
                        "(Mouse) X: " + getMouseX() + " | (Mouse) Y: " + getMouseY(), 2, 45);
        }
        
        /**
         * Allows for a rethrow of the die.
         */
        private static void resetDieRoll()
        {
                dieStopCounter = dieFrame = dieSpinTimer = 0;
                dieThrown = false;
                dieSide = -1;
                
                /*
                 * For now this is just set to some other randome card. Eventually we want
                 * this to simulate a used-card stack where we're only picking random cards
                 * from the new-card stack and used ones are disposed of. Once the used-card
                 * stack contains all of the instruction cards available, we can shuffle the
                 * used-card stack so that it will become the new-card stack
                 */
                instructionCard = newDeck.peek();
                oldDeck.push(newDeck.pop());
                
                if (newDeck.size() == 0)
                {
                        while (!oldDeck.empty())
                                oldDeck.pop();
                    
                        for (byte i = 0; i < This.INSTRUCTION_CARDS.length; ++i)
                                newDeck.push(i);
                                
                        shuffleDeck(This.INSTRUCTION_CARDS);
                }
               
        }
        
        /**
         * Displays the entire die rolling scene.
         * 
         * @param g  the handle to the graphical device
         */
        private static void paintDieRoll(final Graphics2D g)
        {
                g.drawImage(gpsScreen, GPS_SCREEN_X, GPS_SCREEN_Y, null);
                g.setPaint(Color.WHITE);
                
                for (byte l = 0; l < This.INSTRUCTION_CARDS[instructionCard].length; ++l)
                {
                        g.drawString(
                                This.INSTRUCTION_CARDS[instructionCard][l],
                                GPS_SCREEN_X + 75, GPS_SCREEN_Y + 175 + l * 24);
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
                        dieFrame =
                                dieFrame == nextFrame
                                ? (byte)((nextFrame + 1) % N_SIDED_DIE)
                                : nextFrame;
                        dieSpinTimer = 0;
                        
                        if (!dieThrown) return;
                        dieStopCounter += (dieStopCounter >> 2) + random.nextInt(10) + 1;
                }
        }
        
        /**
         * Displays the entire map screen.
         * 
         * @param g  the handle to the graphical device
         */
        private static void paintMap(final Graphics2D g)
        {
                final short mouseX = getMouseX(), mouseY = getMouseY();
                final int // with a huge map the difference could pass 32k
                        mapScreenWidthDiff = WIDTH - map.getWidth(),
                        mapScreenHeightDiff = HEIGHT - map.getHeight();
                final int mapX, mapY;
                
                if (mouseX < 0) mapX = 0;
                else if (mouseX > WIDTH) mapX = mapScreenWidthDiff;
                else mapX = (int)(mouseX * ((float)mapScreenWidthDiff / WIDTH));
                if (mouseY < 0) mapY = 0;
                else if (mouseY > HEIGHT) mapY = mapScreenHeightDiff;
                else mapY = (int)(mouseY * ((float)mapScreenHeightDiff / HEIGHT));
                
                g.drawImage(map, mapX, mapY, null);
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
                return
                        (short)(MouseInfo.getPointerInfo().getLocation().getX()
                        - canvas.getLocationOnScreen().getX());
        }
        
        private static short getMouseY()
        {
                return
                        (short)(MouseInfo.getPointerInfo().getLocation().getY()
                        - canvas.getLocationOnScreen().getY());
        }
        
        /**
         * Goes through a deck of cards sequentially and swaps with another card at a random
         * position somewhere inside of the deck.
         * 
         * @param deck  the deck of 6-lined cards
         */
        private static void shuffleDeck(final String[][] deck)
        {
                for (byte c = (byte)(deck.length - 1) ;c >= 0; --c)
                {
                        final byte randomIndex = (byte)random.nextInt(c + 1);
                        final String[] randomCard = deck[randomIndex];
                        
                        // swap
                        deck[randomIndex] = deck[c];
                        deck[c] = randomCard;
                }
        }
}