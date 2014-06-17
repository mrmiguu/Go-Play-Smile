import java.util.Scanner;
import java.text.NumberFormat;
import java.lang.InterruptedException;

/**
 * The server's console class.
 */
final class Console
{
        /*
         * Constants
         */
        private static final char PROMPT = '>';
        
        /*
         * Immutable fields
         */
        private static final Object readingLock = new Object();
        
        /*
         * Mutable fields
         */
        private static boolean reading;
        
        /**
         * Our console's thread object.
         */
        static final Thread thread = new Thread()
        {
                /**
                 * Initializes our console user prompt cycle.
                 */
                @Override
                public void run()
                {
                        reading = true;
                        final Scanner scanner = new Scanner(System.in);
                        System.out.print(PROMPT);
                        
                        while (true)
                        {
                                if (!reading) continue; // thread-safe
                                final String in = scanner.nextLine().trim().toLowerCase();
                                
                                if (
                                        in.equals("shutdown")
                                        || in.equals("exit")
                                        || in.equals("close")
                                        || in.equals("quit")
                                        || in.equals("goodbye")
                                        || in.equals("terminate")
                                        || in.equals("cya")
                                        || in.equals("adios")
                                        || in.equals("bye")
                                        || in.equals("chau"))
                                {
                                        System.out.println("Unloading...");
                                        This.setUnloading(true);
                                        break;
                                }
                                else if (
                                        in.equals("start")
                                        || in.equals("init")
                                        || in.equals("initialize")
                                        || in.equals("run")
                                        || in.equals("go")
                                        || in.equals("begin")
                                        || in.equals("execute")
                                        || in.equals("launch"))
                                {
                                        // Network thread starts here
                                }
                                else if (
                                        in.equals("fps")
                                        || in.equals("frames per second")
                                        || in.equals("frame rate")
                                        || in.equals("frames")
                                        || in.equals("frame count"))
                                {
                                        System.out.println(This.getFps());
                                }
                                else if (
                                        in.equals("cps")
                                        || in.equals("cycles per second")
                                        || in.equals("cycle rate")
                                        || in.equals("cycles")
                                        || in.equals("cycle count"))
                                {
                                        System.out.println(NumberFormat.getIntegerInstance().format(This.getCps()));
                                }
                                
                                System.out.print(PROMPT);
                                
                                try
                                {
                                        this.sleep(1000);
                                }
                                catch (InterruptedException e)
                                {
                                        this.interrupt();
                                        System.out.println(e.getMessage());
                                }
                        }
                }
        };
        
        /**
         * Prints a server message onto the command prompt overriding user input temporarily.
         * 
         * @param x  the message to be forcefully printed
         */
        static void printMessage(final String x)
        {
                synchronized (readingLock)
                {
                        reading = false;
                        System.out.println("...");
                        System.out.println(x);
                        System.out.print(PROMPT);
                        reading = true;
                }
        }
        
        /**
         * Obtain the console thread's reading status.
         * 
         * @return the reading state
         */
        static boolean getReading()
        {
                synchronized (readingLock)
                {
                        return reading;
                }
        }
        
        /**
         * Attempts to lock and set the console thread's reading state safely.
         * 
         * @param state  the reading state to-be
         */
        static void setReading(final boolean state)
        {
                synchronized (readingLock)
                {
                        reading = state;
                }
        }
}