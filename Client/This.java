final class This {
  /* Constants ************************/
  static final String[][]
    INSTRUCTION_CARDS
      = { { "Traffic jam on 405 by Airport. All cars on 405 lose one turn. If" +
              "you are not on 405, move one.",
            "Caught using cell phone. Get ticket and wait one.",
            "Shopping spree on RODEO Drive. Go To B. Hills.",
            "Freeway wide open today. Move forward two.",
            "Your birthday. Take friend to Hollywood Bowl.",
            "New Diamond Lane opens. Zip forward three."
          },
          { "Go directly to Hollywood to see movie stars.",
            "Police stops you and gives you good driving award. Move forward " +
              "three.",
            "JOKER. You MAY trade places with another car.",
            "You have won free Dodger tickets. Go to any of the three stadium" +
              "entrances.",
            "Go forward to next offramp, then left two.",
            "Rush hour. Cars on 5 lose a turn. Others go one."
          },
          { "Free tickets to Six Flags. Go to Santa Clarita.",
            "Go directly to see Snoopy at Knotts Berry Farm.",
            "Accident ahead. Go right one at next offramp.",
            "Invited to lunch with Mayor. Go directly to L.A.",
            "Blowout. Go slowly to next offramp for repairs.",
            "Go to Airport at N. Long Beach. Helicopter takes you and car to " +
              "Burbank. Then move two."
          },
          { "Go to L.A. Airport to drop off Grandma, then go two.",
            "Daughter enrolls. Take her to UCLA. Then go one.",
            "Take the day off. Go directly to any beach.",
            "Oops. Out of Gas. Nice man pushes you forward one to station.",
            "Go forward to next offramp, then right one.",
            "Take friend and use diamond lane. Zip ahead two."
          },
          { "School bus has flat tire. Stop to help, wait one then move forwa" +
              "rd one on next turn, plus the draw.",
            "Exchange with car closest to El Monte.",
            "Jump forward four. Even if you pass destination.",
            "Beautiful day in sunny So. Cal. Go forward two.",
            "President in town. Freeway closed. Wait one.",
            "Soaring gas prices--light traffic. Go forward two."
          },
          { "Dad riding in back seat. Move forward one.",
            "Today is your day. Move forward two.",
            "Turn signal malfunction. Wait one for repairs.",
            "Rainy today. Wipers are smearing, causing bad vision. Wait one w" +
              "hile wipers are replaced.",
            "Car falls off hauling trailer. Move around one.",
            "Snow storm. Wait one to put on chains."
          },
          { "Tunnel flooded. Use surface streets to go up one.",
            "Trade places with the blue car. Then forward one.",
            "Oil spill ahead. Slip and slide forward one.",
            "Insurance expires. Lose two turns.",
            "No money for gas. Wait one.",
            "Runaway zoo animals on freeway. Pull over and lock doors. Wait o" +
              "ne."
          },
          { "Mixup at ship dock for new imported vehicles. Auto truck takes c" +
              "ar within three of destination.",
            "L.A. river overflows, flooding Freeway. Wait one.",
            "Low tire indicator. Move slowly forward one.",
            "Freeway closed for repairs ahead. Wait one.",
            "Diamond lane ends. Slowly move forward one.",
            "Teenage driver ahead. Take cover & wait one."
          },
          { "Last payment on boat. Go to San Pedro harbor.",
            "You are honored as bank's best credit card customer. Go forward " +
              "two.",
            "Free tickets. Go to concert in Compton.",
            "Trade locations with any car.",
            "All the stars are aligned. Go forward two.",
            "Rain storm. Leaking sun roof. Wait one."
          },
          { "Blowout, radiator overheats, battery dies, and your clunker give" +
              "s up. Get new car and 'go to' card.",
            "Good karma. Jump within four of destination.",
            "GPS malfunction. Says move up 23. Stay put.",
            "Free stay at Beach condo. Go to Long Beach.",
            "Tsunami floods beaches. Move forward three inland.",
            "Mud slide blocks freeway. Stay put."
          },
          { "Blowout on freeway. Good Samaratin changes your tire and moves y" +
              "ou forward two.",
            "Boss gives you day off. Go to Knotts Berry Farm.",
            "Power failure. Move within five of destination.",
            "Move exactly six. Sorry if you pass your destination.",
            "You are feeling good today. Move forward two.",
            "Hershey truck spills load. Stop to help (and eat)."
          },
          { "Instant winner, (NOT) pick a new 'from-to' card and car, then st" +
              "art over and move forward three.",
            "If on 405, pick new 'to' card. Otherwise go up one.",
            "Go directly to Carson, then move forward two.",
            "Goodyear blimp takes you and your car to Pasadena.",
            "You win a new car. Pick one and move three.",
            "Free tickets to game show. Go to Hollywood."
          },
          { "Lady driver swerving badly. Call 911, then wait 1.",
            "18 wheeler in fast lane. Carefully pass forward 1.",
            "Go to Knotts Berry Farms. Enjoy.",
            "Trade locations with any car if to your advantage.",
            "Family in distress. Stop and help. Wait with them for help to co" +
              "me.",
            "Free tickets! Go directly to Disneyland."
          },
          { "Rush hour. Use car wings to fly forward three.",
            "Need bathroom badly. Go forward to next exit.",
            "Traffic jam at Disneyland. Move two on monorail.",
            "Error in GPS data. Choose another 'go to' card.",
            "Advance to nearest beach. Enjoy!",
            "Move forward four. If you pass your destination, too bad. Your m" +
              "ust go four."
          },
          { "Night construction, move forward slowly one.",
            "Car blocking diamond lane. Pass and go up one.",
            "Car cuts you off. Recover and go one. Whew!",
            "3 A.M. You and date use fast land and go three.",
            "Sirens blaring. Pull over at next ramp. All is ok.",
            "Storm. Move cautiously forward one."
          },
          { "Caught in chase situation. Two tires punctured on spike strip. T" +
              "ow truck takes you forward one.",
            "Carpooling today. Use diamond lane to go three.",
            "Limo with movie star goes by. Follow forward two.",
            "Two cars racing in fast lane. Move over and go up 1.",
            "CHP 'DUI' checkpoint. You check ok. Go up one.",
            "2 A.M. Crusin' fast and smoothly. Go forward three."
          },
          { "Freeway closed. Use car wings to fly ahead two.",
            "License check. You check out ok. Forward one.",
            "Rush pregnent wife to hospital. Go forward three.",
            "Car chase. Stop and wait one as CHP speeds by.",
            "CHP impounds car. Start over as new player.",
            "Paramedics blocking lanes, slide by slowly one."
          },
          { "Diamond lane ends. Massive jam. Wait one.",
            "CHP units slowing all traffic. Move slowly one.",
            "Man hanging from overpass. Wait one.",
            "Rush hour. Use surface streets, go forward two.",
            "Plywood in fast lane. Wait one for removal.",
            "Freeway closed for movie. Move one to offramp."
          },
          { "Train derails blocking freeway. Wait one.",
            "Need bathroom badly. Go forward to next exit.",
            "Accident at Disneyland. Use monorail and go up two.",
            "Confusing GPS data. Choose another 'to' card.",
            "Radiator leak. Go to next off, get water. Stay cool.",
            "Flashing lights behind. Pull over. Keep calm. Show ID and insura" +
              "nce. Cop says you're ok. Move 3."
          },
          { "Mud slide washes out two lanes. Move slowly 1.",
            "Go to L.A. Co. Fair in Pomona. Then move four.",
            "Go see Rose Bowl parade floats in Pasadena.",
            "Student driver ahead. Slowly move forward one.",
            "Rollover on right shoulder. Call 911. Stop and help. Then carefu" +
              "lly go forward one.",
            "Olympics in town. Freeway clear. Go forward two."
          },
          { "Caught in diamond lane with no passenger. Get ticket, then go ba" +
              "ck one.",
            "Joker. You may trade places with any car.",
            "Load car onto train car mover. Go to Pasadena.",
            "Engine light comes on. Move forward to next off.",
            "Truck of chickens loses load. Wait here this turn.",
            "Go to Magic Mountain."
          },
          { "Truck blocking all lanes. Wait here this turn.",
            "House movers across three lanes. Go slowly one.",
            "All cars except you move forward one.",
            "Diamond lane clogged. Move over and go up one.",
            "Following too closely. Stop to get warning from police, then wai" +
              "t here till next turn.",
            "Green car swap with brown car, if both are playing."
          },
          { "Watermelon spill. Take next right, then two.",
            "Go forward to next exit for potty stop.",
            "Swap places with any car on 5. If none, go two.",
            "Light traffic today. Go forward two.",
            "'Door ajar' light is on. Stop, check doors, then go forward one.",
            "You move up one. Orange car goes back one."
          },
          { "USC plays Mich. in Rose Bowl. Go to Pasadena.",
            "You wait here. All others move one.",
            "Blue car moves forward two.",
            "CHP stopping all traffic due to poor visibility.",
            "Hub cap flies off. Go backward one to retrieve.",
            "Feeling bad karma today. Move to next offramp to visit therapist."
          },
          { "Subway opens. Surface traffic light. Forward one.",
            "Major power outage. All signs dark. Wait here.",
            "High speed rail construction slows freeway traffic. Move slowly " +
              "forward one.",
            "Go to any beach. Enjoy, then move three.",
            "Holiday. Visit grandma in Torrance.",
            "Angles host World Series. Go to Anaheim."
          },
          { "Freeway clogged with trucks today. Wait one.",
            "Filming chase scene in Hollywood. Go ahead one.",
            "Police lay down tack strip. You get four flat tires. Wrecker tak" +
              "es your forward two for repairs.",
            "New freeway opens. Move forward three.",
            "You may trade places with any car.",
            "Motorcycle accident ahead. Wait this turn."
          },
          { "Light traffic today. Go forward two.",
            "Congested traffic near Dodger stadium. If on one of four stadium" +
              " exits, wait one. If not, go three.",
            "Move forward or back within six of destination.",
            "Start over. Get new car and 'to-from' card. Move 4.",
            "Turn right next exit, then move 3.",
            "Move over. Light traffic. Take fast lane ahead 2."
          },
          { "Car chase ahead. Pull over and wait this turn.",
            "Thirsty. Move forward to next offramp for soda.",
            "Border Patrol checkpoint. Wait this turn.",
            "105 degrees today. Go to next exit to buy ice.",
            "Cinco de Mayo parade blocks freeway around L.A. If on 5, wait on" +
              "e. Otherwise move ahead 2.",
            "Fire by freeway clogs traffic. Wait here this turn."
          },
          { "If on 210, move closest way to 5. If not on 210, go forward two.",
            "Truck of rabbits dumps load. Move slowly one.",
            "Send red car to Disneyland, then your car up one.",
            "Bus on fire. Stop to help, then forward one.",
            "New diamond lane opens. Go forward three.",
            "Move half the distance to your destination."
          },
          { "Electric cars only today on freeway. Sorry, your car uses gasoli" +
              "ne. Stay put.",
            "Funeral processional ahead/ Move slowly one.",
            "Go to 'Azusa Pacific' College in Azusa, then four.",
            "12 midnight. Very sleepy. Pull over for nap.",
            "Beautiful day in sunny Southern California. Move two.",
            "Dodgers win. All cars move forward one."
          },
          { "75 degrees in L.A. Go forward one.",
            "Need bathroom badly. Go forward to next exit.",
            "Free tickets to Queen Mary tour. Go to Long Beach.",
            "Earthquake! Rockin' and rollin' forward two.",
            "Earth day. Regular cars stay off freeway today.",
            "Mayor cuts ribbon for new diamond lane. Move forward three after" +
              " celebration."
          },
          { "Student visiting day at Cal Poly Pomona. Take car full of high s" +
              "chool seniors to visit campus.",
            "Accident ahead. Go two on alt. route or stay put.",
            "Pile up at Disneyland. Go up one if not on International 5.",
            "Car overheats. Wait this turn while radiator cools.",
            "Caught not wearing seat belt. Lose this turn.",
            "Flat tire. Call AAA, then move forward one after repair."
          },
          { "Man behind has road rage. Speed up, go to next exit, call 911, a" +
              "nd then go right one.",
            "Idiot shooting from bridge. Zigzag forward one.",
            "Go to Dodger stadium. Enjoy game. Use any exit.",
            "Trade locations with closest car.",
            "Lady in distress. Stop and help, then forward two.",
            "Go directly to L.A. County Fair in Pomona."
          },
          { "Out of gas. Turn on electric motor and go two.",
            "Circus in town. Wait one for elephant parade.",
            "Reduce speed ahead. School zone. Watch for kids. Go forward slow" +
              "ly one.",
            "Truck drops lumber on freeway. Wait one.",
            "All clear today. Go forward two.",
            "Debris in fast lane. Change lanes and go one."
          },
          { "If on 91, go 3 toward goal. If not, go one.",
            "Go directly to Commerce, then move three.",
            "Inspect citrus grove in Orange, then go two on 22.",
            "Go directly to wedding in Palos Verdes.",
            "Go to Glendale, then one either way to 210.",
            "Go to Garden Grove, then left one toward Westminster, then north" +
              " four."
          },
          { "If on 710, go forward 3. If not, go one.",
            "Boring day on freeway. Go forward one.",
            "Give speech at USC, from there go forward.",
            "Go directly to wedding in Palos Verdes.",
            "Go to Pasadena, then south one to 210.",
            "Border patrol stops traffic to chase illegal immigrants. Wait he" +
              "re one, but on next turn go up two, plus dice roll."
          },
          { "Rush pregnant wife to hospital. Use diamond lanes, break all spe" +
              "ed limits, and move forward four.",
            "Night crew painting new lane stripes. Wait one.",
            "Headlights go out. Go to station at next offramp.",
            "Go to L.A. zoo. Take Panorama City offramp.",
            "Business takes you to City of Industry.",
            "Amber alert. Pull over and watch for red Honda."
          },
          { "Work swing shift. Freeway clear at 4 A.M. Go forward to next off" +
              "ramp for breakfast, then forward one.",
            "Go to Santa Monica pier, ride ferris wheel.",
            "Visit sister in Cyrpus, then forward two.",
            "Pick up pal at Van Nuys Airport. Then move one.",
            "Business trip to Brea Oil Field. Then move two.",
            "Wrong way driver. Pull over and wait this turn."
          },
          { "Lane closed ahead, move forward slowly one.",
            "Fire truck ahead blocking freeway. Wait one.",
            "Wrong way driver, swerve forward one. Whew!",
            "3 A.M. Go 75 mph. Move forward two.",
            "Sirens behind. Move up one as you pull over.",
            "New freeway opens. Move forward two."
          },
          { "Student visiting day at Cal Poly Pomona. Take students to visit " +
              "campus.",
            "Accident ahead. Take alt. route or lose this turn.",
            "Traffic jam at Disneyland. Go up one if not on International 5.",
            "Car overheats. Wait this turn while radiator cools.",
            "Caught not wearing seat belt. Lose this turn.",
            "Flat tire. Call AAA, then wait this turn for their help."
          }
        };
        
  static final String[] LOCATIONS 
    = { "Santa Clarita", "San Fernando", "Lakeview Terrace", "Sunland", "Panorama City",
        "La Cañada", "Pasadena", "Van Nuys", "Burbank", "Studio City", " I ",
        "Glendale", "Azusa", "San Dimas", "Claremont", "Highland Park", " I ",
        "Los Angeles", " I ", "Rosemead", "West Covina", "Pomona", "El Monte", 
        " I ", "I", "Monterey Park", "Cal Poly", "USC", "Commerce", "South El Monte",
        "Culver City", " I ", "Chino Hills", " I ", "Santa Monica", "Diamond Bar",
        "Industry", "Sante Fe Springs", "LA Airport", "Watts", "Lynwood", "Brea",
        "Corona", "Bellflower", "Compton", "Hawthorne", "Gardena", "Dominguez Hills",
        "North Long Beach", "Torrance", "Buena Park", "Carson", "", "Fullerton",
        "Yorba Linda", " I ", "Cyprus", "Westminster", "Palos Verdes", " I ", "Gardeon Grove",
        "Orange", " I ", "Long Beach", "San Pedro", "Huntington Beach", "Santa Ana",
        "Fountain Valley"};

  /* Immutable fields *****************/

  /* Mutable fields *******************/
  static volatile boolean
    unloading;
  static int
    fps,
    cps;
  //````````````````````````````````````````````````````````````````````````````

  /**
   * The main entry point for the client.
   *
   * @param args  an array of commands entered at execution time
   */
  public static void main(final String[] args)
      throws Exception {

    Window.setup();
    run();    // 2nd-to-last
    unload(); // last
  }
  //````````````````````````````````````````````````````````````````````````````

  /**
   * Launches the client into its real-time loop.
   */
  private static void run() {
    long
      timer2500 = 0,
      timer30 = 0,
      timer15 = 0,
      timer1000 = 0;

    int
      frameCount = 0,
      cycleCount = 0;

    while (true) {
      final long
        NOW = System.currentTimeMillis(); // log our current time for speed

      /*
       * Late events are initiated here
       */
      if (timer2500 < NOW) {
        if (unloading) break;
        timer2500 = NOW + 2500;
      }

      /*
       * The physics of the game run at half the frame rate (~30 fps)
       */
      if (timer30 < NOW) {
        timer30 = NOW + 30;
      }

      /*
       * The frame rate is processed and counted
       */
      if (timer15 < NOW) {
        Window.paint();
        ++frameCount;
        timer15 = NOW + 15; // adjusts so FPS is ~60
      }

      /*
       * The last thing calculated is the amount of cycles per second
       */
      ++cycleCount;
      if (timer1000 < NOW) {
        fps = frameCount;
        cps = cycleCount;
        cycleCount = frameCount = 0;
        timer1000 = NOW + 1000;
      }
    }
  }
  //````````````````````````````````````````````````````````````````````````````

  /**
   * Properly unloads the client.
   */
  private static void unload() {
    Window.unload();
    System.out.println("Unloaded!");
  }
}