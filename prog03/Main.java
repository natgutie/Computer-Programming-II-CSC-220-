package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /**
   * Use this variable to store the result of each call to fib.
   */
  public static double fibn;

  /**
   * Determine the time in microseconds it takes to calculate the
   * n'th Fibonacci number.
   *
   * @param fib an object that implements the Fib interface
   * @param n   the index of the Fibonacci number to calculate
   * @return the time for the call to fib(n)
   */
  public static double time(Fib fib, int n) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Calculate the n'th Fibonacci number.  Store the
    // result in fibn.
    fibn = fib.fib(n);

    // Get the current time in nanoseconds.
    long end = System.nanoTime() - start; // fix this

    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return end / 1000.0; // fix this
    //
  }

  /**
   * Determine the average time in microseconds it takes to calculate
   * the n'th Fibonacci number.
   *
   * @param fib    an object that implements the Fib interface
   * @param n      the index of the Fibonacci number to calculate
   * @param ncalls the number of calls to average over
   * @return the average time per call
   */
  public static double averageTime(Fib fib, int n, int ncalls) {
    // Copy the contents of Main.time here.
    long start = System.nanoTime();

    // Add a "for loop" line before the line with the call to fib.fib
    // to make that line run ncalls times.
    for (int i = 0; i < ncalls; i++) {
      // Calculate the n'th Fibonacci number.  Store the
      // result in fibn.
      fibn = fib.fib(n);
    }

    // Get the current time in nanoseconds.
    long end = System.nanoTime() - start; // fix this

    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return (end / ncalls) / 1000.0;


    // Modify the return value.

    //return 0; // remove this line
  }

  /**
   * Determine the time in microseconds it takes to to calculate the
   * n'th Fibonacci number.  Average over enough calls for a total
   * time of at least one second.
   *
   * @param fib an object that implements the Fib interface
   * @param n   the index of the Fibonacci number to calculate
   * @return the time it it takes to compute the n'th Fibonacci number
   */
  public static double accurateTime(Fib fib, int n) {
    // Get the time in microseconds for one call.
    double t = time(fib, n);

    // If the time is (equivalent to) more than a second, return it.
    if (t > 1000000) {
      return t;
    }

    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPRESSION)   so you can save it into an int variable.
    int numcalls = (int) (1 / (t * java.lang.Math.pow(10, -6))); // fix this


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  //private static UserInterface ui = new TestUI("Fibonacci experiments");
  private static UserInterface ui = new GUI("Fibonacci experiments");

  /**
   * Get a non-negative integer from the using using ui.
   * If the user enters a negative integer, like -2, say
   * "-2 is negative...invalid"
   * If the user enters a non-integer, like abc, say
   * "abc is not an integer"
   * If the user clicks cancel, return -1.
   *
   * @return the non-negative integer entered by the user or -1 for cancel.
   */
  static int getInteger() {

    String s = null;
    while (true) {

      try {
        s = ui.getInfo("Enter n");
        if (s == null) {
          return -1;
        }
        int n = Integer.parseInt(s);
        if (n >= 0) {
          return n;
        }
        ui.sendMessage(s + " is a negative integer please enter a positive integer");
        //try-catch block tries to return the parsed integer but if not returns a messages that says "abc is not an integer".
        // if n < 0
      } catch (NumberFormatException e) {
        ui.sendMessage(s + " is not an integer, please enter an integer.");
      }
    }
  }


  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);

    // make sure dont break too soon with asking yes or no --> prompt user to enter a new number
    // make sure cancel doesnt exit the program but goes to main menu

    boolean firstLoop = true;

    while (true) {
      int nInput = getInteger();

      if (nInput == -1) {
        return;
      }

      double estimateTime = fib.estimateTime(nInput);

      if (!firstLoop) {
        ui.sendMessage("Estimated time on input " + nInput + " is " + estimateTime + " microseconds.");

        if (estimateTime / 1000000.0 > 3600) {
          ui.sendMessage("This calculation takes an estimated one hour, are you sure you would like to continue?");
          String options[] = {"yes", "no"};
          int continueYesNo = ui.getCommand(options);
          if (continueYesNo == 1) {
            continue;
          }
        }
      }

          double timeAccurate = accurateTime(fib, nInput);
          fib.recordConstant(nInput, timeAccurate);

          double error = (estimateTime-timeAccurate)/timeAccurate*100;

          if (firstLoop) {
            ui.sendMessage("fib(" + nInput + ") = " + fibn + " in " + timeAccurate + " microseconds.");

          } else {
            ui.sendMessage(nInput + "th Fibonacci: " + fibn + " in " + timeAccurate + " microseconds. And % error estimated to accurate: " + error + "%");

          }
          firstLoop = false;

    } // end of while loop
  } // end of method

  public static void doExperiments () {
    // Give the user a choice instead, in a loop, with the option to exit.
    //doExperiments(new ExponentialFib());
    String[] menuChoices = new String[]{"ExponentialFib", "LinearFib", "LogFib", "ConstantFib", "MysteryFib", "EXIT"};

    while(true){
      int menu = ui.getCommand(menuChoices);

      switch(menu){
        case 0:
          doExperiments(new ExponentialFib());
          break;
        case 1:
          doExperiments(new LinearFib());
          break;
        case 2:
          doExperiments(new LogFib());
          break;
        case 3:
          doExperiments(new ConstantFib());
          break;
        case 4:
          doExperiments(new MysteryFib());
          break;
        default:
          return;
    }

    }
  }

  static void labExperiments () {
    //Fib efib = new ExponentialFib();
    //Fib efib = new LinearFib();
    //Fib efib = new LogFib();
    Fib efib = new ConstantFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = accurateTime(efib, n1);
    System.out.println("n1 " + n1 + " time1 " + time1 + " microseconds");
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est + " microseconds");
    
    // Calculate actual running time for n2=30.
    double time2 = accurateTime(efib, n2);
    System.out.println("n2 " + n2 + " actual time " + time2  + " microseconds");

    // Estimate how long ExponentialFib.fib(100) would take.
    int n3 = 100;
    double time3est = ((((((c * efib.O(n3)) * (1/1000000.0)) * (1/60.0)) * (1/60.0)) * (1/24.0)) * (1/365.25));
    System.out.println("n3 " + n3 + " estimated time " + time3est + " years");
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    labExperiments();
    doExperiments();
  }
}
// Estimate the running time.  Tell the user the estimate.
// Measure the actual running time.  Tell the user fib(n), the actual time, and the percentage error.
// Your percentages should not be bigger than 100% very often.  The percentages for ExponentialFib should be pretty small for fib(30) and fib(40).
// Make sure you spell correctly, or TestUI will fail.
// Don't forget to ask the user if they really want to run it if the estimated time is over an hour.
// Use TestUI to test your program and compare the output to perfect-Main.txt
