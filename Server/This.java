final class This
{
        /*
         * Constants
         */
        
        /*
         * Immutable fields
         */
        private static final Object unloadingLock = new Object(); // for when other threads set the unloading field
        
        /*
         * Mutable fields
         */
        private static boolean unloading;
        private static byte fps;
        private static int cps;
        
        /**
         * The main entry point for the server.
         * 
         * @param args  an array of commands entered at execution time
         */
        public static void main(final String[] args)
        {
                System.out.println(
                        "_____/\\\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\___");
                System.out.println(" ___/\\\\\\//////////__\\/\\\\\\/////////\\\\\\_/\\\\\\/////////\\\\\\_");
                System.out.println("  __/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\\\//\\\\\\______\\///__");
                System.out.println(
                        "   _\\/\\\\\\____/\\\\\\\\\\\\\\_\\/\\\\\\\\\\\\\\\\\\\\\\\\\\/__\\////\\\\\\_________");
                System.out.println("    _\\/\\\\\\___\\/////\\\\\\_\\/\\\\\\/////////_______\\////\\\\\\______");
                System.out.println("     _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\___________________\\////\\\\\\___");
                System.out.println("      _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\____________/\\\\\\______\\//\\\\\\__");
                System.out.println(
                        "       _\\//\\\\\\\\\\\\\\\\\\\\\\\\/__\\/\\\\\\___________\\///\\\\\\\\\\\\\\\\\\\\\\/___");
                System.out.println("        __\\////////////____\\///______________\\///////////_____");
                System.out.println("\n                        (c) 2014 Richard Laughman\n");
                
                Console.thread.start();
                run(); // 2nd-to-last
                unload(); // last
        }
        
        /**
         * Launches the server into its real-time loop.
         */
        private static void run()
        {
                long timerLate = 0;
                long timerFps = 0;
                long timerCps = 0;
                byte counterFps = 0;
                int counterCps = 0;
                
                while (true)
                {
                        final long CURRENT_TIME_MILLIS = System.currentTimeMillis(); // log our current time for speed
                        
                        /*
                         * Late events are initiated here
                         */
                        if (timerLate < CURRENT_TIME_MILLIS)
                        {
                                if (unloading) break;
                                timerLate = CURRENT_TIME_MILLIS + 2500;
                        }
                        
                        /*
                         * The frame rate is processed and counted
                         */
                        if (timerFps < CURRENT_TIME_MILLIS)
                        {
                                ++counterFps; // counterFps++ (post-increment) has to remember the old value
                                timerFps = CURRENT_TIME_MILLIS + 15; // adjusts so FPS is ~60
                        }
                        
                        /*
                         * The last thing calculated is the amount of cycles per second
                         */
                        ++counterCps;
                        if (timerCps < CURRENT_TIME_MILLIS)
                        {
                                fps = counterFps;
                                cps = counterCps;
                                counterCps = counterFps = 0;
                                timerCps = CURRENT_TIME_MILLIS + 1000;
                        }
                }
        }
        
        /**
         * Properly unloads the server.
         */
        private static void unload()
        {
                System.out.println("Unloaded!");
        }

        /**
         * Obtain the server's main thread's unloading status.
         * 
         * @return the unloading state
         */
        static boolean getUnloading()
        {
                synchronized (unloadingLock)
                {
                        return unloading;
                }
        }
        
        /**
         * Attempts to lock and set the server's main thread's unloading state safely.
         * 
         * @param state  the unloading state to-be
         */
        static void setUnloading(final boolean state)
        {
                synchronized (unloadingLock)
                {
                        unloading = state;
                }
        }
        
        /**
         * Retrieves the server's frame rate on a per second basis.
         * 
         * @return amount of frames per second
         */
        static byte getFps()
        {
                return fps;
        }
        
        /**
         * Retrieves the server's cycle rate on a per second basis.
         * 
         * @return amount of cycle per second
         */
        static int getCps()
        {
                return cps;
        }
}