public final class This
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
        
        /**
         * The main entry point for the client.
         * 
         * @param args  an array of commands entered at execution time
         */
        public static void main(final String[] args)
        {
                Window.setup();
                run(); // 2nd-to-last
                unload(); // last
        }
        
        /**
         * Launches the client into its real-time loop.
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
                                Window.paint();
                                
                                ++counterFps; // counterFps++ (post-increment) has to remember the old value
                                timerFps = CURRENT_TIME_MILLIS + 15; // adjusts so FPS is ~60
                        }
                        
                        /*
                         * The last thing calculated is the amount of cycles per second
                         */
                        ++counterCps;
                        if (timerCps < CURRENT_TIME_MILLIS)
                        {
                                System.out.println("FPS: " + counterFps + " | CPS: " + counterCps);
                                counterCps = counterFps = 0;
                                timerCps = CURRENT_TIME_MILLIS + 1000;
                        }
                }
        }
        
        /**
         * Properly unloads the client.
         */
        private static void unload()
        {
                Window.unload();
                System.out.println("Unloaded!");
        }
        
        /**
         * Obtain the client's main thread's unloading status.
         * 
         * @return the unloading state
         */
        public static boolean getUnloading()
        {
                synchronized (unloadingLock)
                {
                        return unloading;
                }
        }
        
        /**
         * Attempts to lock and set the client's main thread's unloading state safely.
         * 
         * @param state  the unloading state to-be
         */
        public static void setUnloading(final boolean state)
        {
                synchronized (unloadingLock)
                {
                        unloading = state;
                }
        }
}