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
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.text.NumberFormat;
import java.util.Random;
import java.util.ArrayList;
import javax.imageio.ImageIO;

final class Window
{ /* Constants ************************/
  private static final byte    N_SIDED_DIE  = 6;
  private static final String  TITLE        = "Go-Play-Smile!";
  private static final int     WIDTH        = 1024;
  private static final int     HEIGHT       = 768;
  private static final int     CENTER_X     = WIDTH >> 1;
  private static final int     CENTER_Y     = HEIGHT >> 1;
  private static final Integer GPS_SCREEN_X = null; // reassigned later
  private static final Integer GPS_SCREEN_Y = null; // reassigned later
  private static final Font    FONT         = new Font("SansSerif", Font.PLAIN, 24);

  /* Immutable fields *****************/
  private static final Frame            frame          = new Frame(TITLE);
  private static final Canvas           canvas         = new Canvas();
  private static final BufferStrategy   bufferStrategy = null; // reassigned later
  private static final BufferedImage    gpsScreen      = loadBufferedImage("GPS_Screen.png");
  private static final BufferedImage    map            = loadBufferedImage("Map.png");
  private static final BufferedImage[]  die            = new BufferedImage[N_SIDED_DIE];
  private static final Random           random         = new Random();
  private static final ArrayList<Point> points         = new ArrayList();

  /* Mutable fields *******************/
  private static boolean showFpsCpsAndMouseCoords;
  private static int     instructionCard;
  private static int     dieSpinTimer;
  private static int     dieFrame;
  private static int     dieStopCounter;
  private static boolean showDieRoll;
  private static boolean dieThrown;
  private static int     dieSide = -1;
  private static boolean showMap = true;
  //````````````````````````````````````````````````````````````````````````````

  /**
   * Prepares the canvas and frame for the game's window.
   */
  static void setup() throws IOException
  { shuffleDeck(This.INSTRUCTION_CARDS);

    canvas.setSize(WIDTH, HEIGHT);
    frame.add(canvas);
    frame.setResizable(false);
    frame.pack();

    frame.addWindowListener(
        new WindowAdapter()
        { @Override
          public void windowClosing(final WindowEvent e)
          { if (This.unloading == true) return;
            System.out.println("Unloading...");
            This.unloading = true;
          }
        });

    canvas.addKeyListener(
        new KeyAdapter()
        { @Override
          public void keyPressed(final KeyEvent e)
          { switch (e.getKeyCode())
            { case KeyEvent.VK_F1:
                showFpsCpsAndMouseCoords = !showFpsCpsAndMouseCoords;
                break;
              case KeyEvent.VK_SPACE:
                if (showDieRoll) resetDieRoll();
                showDieRoll = !showDieRoll;
                break;
              default: break;
            }
          }
        
          @Override
          public void keyReleased(final KeyEvent e)
          { switch (e.getKeyCode())
            { default: break;
            }
          }
        });

    canvas.addMouseListener(
        new MouseAdapter()
        { @Override
          public void mouseClicked(final MouseEvent e)
          { if (showDieRoll)
            { dieThrown = true;
            }
          }
        });

    canvas.setIgnoreRepaint(true); // do the painting ourselves
    canvas.createBufferStrategy(2); // setup double-buffering
    
    // these are only ever set once (here)
    General.setPrivateStaticFinal(Window.class,
                                  "bufferStrategy",
                                  canvas.getBufferStrategy());
    General.setPrivateStaticFinal(Window.class,
                                  "GPS_SCREEN_X",
                                  CENTER_X - gpsScreen.getWidth() / 2);
    General.setPrivateStaticFinal(Window.class,
                                  "GPS_SCREEN_Y",
                                  CENTER_Y - gpsScreen.getHeight() / 2);

    load();
    findLocations(map);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    System.out.println("canvas.requestFocusInWindow() = " +
                       canvas.requestFocusInWindow());
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Load in mutable buffered images and other resources.
   */
  private static void load() throws IOException
  { for (int s = die.length; --s >= 0;)
    { die[s] = ImageIO.read(new File("Images/Die/" + (s+1) + ".png"));
    }
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Load in a buffered images from its respective location.
   */
  private static BufferedImage loadBufferedImage(final String imageFile)
  { try
    { return ImageIO.read(new File("Images/" + imageFile));
    }
    catch (final IOException e)
    { e.printStackTrace();
      return null;
    }
  }
  //````````````````````````````````````````````````````````````````````````````

  /**
   * Draws the visuals of the game onto the window's canvas.
   */
  static void paint()
  { final Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
    g.clearRect(0, 0, WIDTH, HEIGHT);
    g.setFont(FONT);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                       RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

    if (showMap) paintMap(g);
    if (showFpsCpsAndMouseCoords) paintFpsCpsAndMouseCoords(g);
    if (showDieRoll) paintDieRoll(g);

    g.dispose();
    bufferStrategy.show();
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Displays the FPS, CPS, and mouse coordinates onto the screen.
   * 
   * @param g  the handle to the graphical device
   */
  private static void paintFpsCpsAndMouseCoords(final Graphics2D g)
  { g.drawString("FPS: " +
                 This.fps +
                 " | CPS: " +
                 NumberFormat.getIntegerInstance().format(This.cps),
                 2,
                 21);

    g.drawString("(Mouse) X: " +
                 getMouseX() +
                 " | (Mouse) Y: " +
                 getMouseY(),
                 2,
                 45);
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Allows for a rethrow of the die.
   */
  private static void resetDieRoll()
  { dieStopCounter = dieFrame = dieSpinTimer = 0;
    dieThrown = false;
    dieSide = -1;
    
    /*
     * Since the deck is always reshuffled after all the cards have been used,
     * we can assume to set our instruction card to the next simply increasing
     * by one
     */
    instructionCard = (instructionCard + 1) % This.INSTRUCTION_CARDS.length;
    if (instructionCard == 0) shuffleDeck(This.INSTRUCTION_CARDS);
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Displays the entire die rolling scene.
   * 
   * @param g  the handle to the graphical device
   */
  private static void paintDieRoll(final Graphics2D g)
  { g.drawImage(gpsScreen, GPS_SCREEN_X, GPS_SCREEN_Y, null);
    g.setPaint(Color.WHITE);
    
    paintParagraph(g,
                   This.INSTRUCTION_CARDS[instructionCard][dieFrame],
                   new Point(GPS_SCREEN_X + 75, GPS_SCREEN_Y + 145),
                   360);
    
    g.setPaint(Color.BLACK);
    final int dieX, dieY;
    
    if (!dieThrown)
    { dieX = getMouseX() - die[dieFrame].getWidth() / 2;
      dieY = getMouseY() - die[dieFrame].getHeight() / 2;
    }
    else dieY = dieX = 0;
    
    g.drawImage(die[dieFrame], dieX, dieY, null);

    /*
     * Stop the die at some point eventually
     */
    if (dieStopCounter >= 100)
    { if (dieSide < 0 || dieSide > N_SIDED_DIE - 1)
      { dieSide = dieFrame;
        System.out.println("dieSide = " + (dieSide + 1));
      }

      return;
    }
    
    /*
     * Animate the spinning die once every 6 frames (minimum)
     */
    if (++dieSpinTimer >= 6 + (dieThrown ? dieStopCounter : 0))
    { final int nextFrame = random.nextInt(N_SIDED_DIE);
      dieFrame =
          dieFrame == nextFrame
              ? (nextFrame + 1) % N_SIDED_DIE
              : nextFrame;
          dieSpinTimer = 0;
      
      if (!dieThrown) return;
      dieStopCounter += (dieStopCounter >> 2) + random.nextInt(10) + 1;
    }
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Forces a string of text into a bounding paragraph.
   *
   * @param g     the handle to the graphical device
   * @param text  the text for the paragraph
   * @param p     the x and y starting location
   * @param width the width of the paragraph in screen coordinates
   */
  private static void paintParagraph(final Graphics2D g,
                                     final String text,
                                     final Point p,
                                     final int width)
  { final AttributedString as = new AttributedString(text);
    as.addAttribute(TextAttribute.FONT, FONT);
    
    final LineBreakMeasurer lbm
        = new LineBreakMeasurer(as.getIterator(), g.getFontRenderContext());

    int y = 0;
    
    while (lbm.getPosition() < text.length())
    { final TextLayout tl = lbm.nextLayout(width);
      y += tl.getAscent();

      tl.draw(g,
              tl.isLeftToRight()
                  ? p.x
                  : p.x + width - tl.getAdvance(),
              p.y + y);

      y += tl.getDescent() + tl.getLeading();
    }
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Displays the entire map screen.
   * 
   * @param g  the handle to the graphical device
   */
  private static void paintMap(final Graphics2D g)
  { final int mouseX = getMouseX(), mouseY = getMouseY();

    // with a huge map the difference could pass 32k
    final int mapScreenWidthDiff = WIDTH - map.getWidth(),
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
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Clears and disposes of the window's objects.
   */
  static void unload()
  { for (int s = 0; s < die.length; s++)
    { die[s].flush();
      die[s] = null;
    }
    
    bufferStrategy.dispose();
    frame.dispose();
  }
  //````````````````````````````````````````````````````````````````````````````
  
  private static int getMouseX()
  { return (int)(MouseInfo.getPointerInfo().getLocation().getX() -
           canvas.getLocationOnScreen().getX());
  }
  //````````````````````````````````````````````````````````````````````````````
  
  private static int getMouseY()
  { return (int)(MouseInfo.getPointerInfo().getLocation().getY() -
           canvas.getLocationOnScreen().getY());
  }
  //````````````````````````````````````````````````````````````````````````````
  
  /**
   * Goes through a deck of cards sequentially and swaps with another card at a
   * random position somewhere inside of the deck.
   * 
   * @param deck  the deck of 6-lined cards
   */
  private static void shuffleDeck(final String[][] deck)
  { for (int c = deck.length; --c >= 0;)
    { final int randomIndex = random.nextInt(c + 1);
      final String[] randomCard = deck[randomIndex];
      
      // swap
      deck[randomIndex] = deck[c];
      deck[c] = randomCard;
    }
  }
  //``````````````````````````````````````````````````````````````````````````````
  
  private static void findLocations(final BufferedImage image)
  { final int color = Color.RED.getRGB();

    for (int y = 0; y < map.getHeight(); ++y)
    { for (int x = 0; x < map.getWidth(); ++x)
      { boolean newLocation = true;

        if (image.getRGB(x, y) == color && !points.isEmpty())
        { for (Point p : points)
          { if (Math.abs(p.getX() - x) <= 7 &&
                Math.abs(p.getY() - y) <= 7) newLocation = false;
            }
    
          if (newLocation) points.add(new Point(x + 1, y + 6));
        }
        else if (image.getRGB(x, y) == color && points.isEmpty())
        { points.add(new Point(x + 1, y + 6));
        }
      }
    }

    for (int i = 0; i < points.size(); ++i)
    { System.out.println(points.get(i).toString());
    }
  }
}