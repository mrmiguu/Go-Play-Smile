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
                /*
                 * Timer fields (these won't change from the 'long' primitive type)
                 */
                long
                        timerLate = 0,
                        timerPhysics = 0,
                        timerFps = 0,
                        timerCps = 0;
                
                /*
                 * Counter fields (the primitive types here vary)
                 */
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
                         * The physics of the game run at half the frame rate (~30 fps)
                         */
                        if (timerPhysics < CURRENT_TIME_MILLIS)
                        {
                                timerPhysics = CURRENT_TIME_MILLIS + 30;
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
                                fps = counterFps;
                                cps = counterCps;
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
        static boolean getUnloading()
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
        static void setUnloading(final boolean state)
        {
                synchronized (unloadingLock)
                {
                        unloading = state;
                }
        }
        
        /**
         * Retrieves the client's frame rate on a per second basis.
         * 
         * @return amount of frames per second
         */
        static byte getFps()
        {
                return fps;
        }
        
        /**
         * Retrieves the client's cycle rate on a per second basis.
         * 
         * @return amount of cycle per second
         */
        static int getCps()
        {
                return cps;
        }
}