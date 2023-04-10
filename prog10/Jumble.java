package prog10;

import prog02.GUI;
import prog02.UserInterface;
import prog09.BTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];
    for(int n = 0; n < word.length(); n++){
      char c = word.charAt(n);
      int i = n;

      while(i > 0 && c < sorted[i - 1]){
        sorted[i] = sorted[i-1];
        i--;
      }
      sorted[i] = c;
    }
    return new String(sorted, 0, word.length());
    }


  public static void main (String[] args) {
    UserInterface ui = new GUI("Jumble");
    // UserInterface ui = new ConsoleUI();
    //Map<String,String> map = new TreeMap<String,String>();
    //Map<String,String> map = new PDMap();
    //Map<String,String> map = new LinkedMap<String,String>();
    //Map<String,String> map = new SkipMap<String,String>();
    //Map<String,List<String>> map = new BTree<String,List<String>>();

    Map<String,List<String>> map = new HashMap<String,List<String>>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
        System.out.println(word + " sorted is " + sort(word));


      String sorted = sort(word);
      if (!map.containsKey(sorted)) {
        List<String> listWord = new ArrayList<>();
        listWord.add(word);
        map.put(sorted, listWord);
      } else {
        List<String> wordList = map.get(sorted);
        wordList.add(word);
      }

      //Add a test of remove to Jumble.java.

        //System.out.println("Test Put: " + map.put(word,sort(word)));
      //After a put, do a get to make sure the value is correct.
        //System.out.println("Test Get: " + map.get(word));
      //Do a remove and make sure it returns the right value.
        //System.out.println("Test Remove: " + map.remove(word));
      //Do a get and make sure you get null.
        //System.out.println("Test Get Null: " + map.get(word));
      //Do a second remove and make sure you get null.
        //System.out.println("Test Remove Null: " + map.remove(word));
      //Do the put again.
        //System.out.println("Send Put Test: " + map.put(word, sort(word)));

    }

    String jumble = ui.getInfo("Enter jumble.");
    while (jumble != null) {

      List<String> words = map.get(sort(jumble));

      if (words == null)
        ui.sendMessage("No match for " + jumble);
      else
        ui.sendMessage(jumble + " unjumbled is " + words);

      jumble = ui.getInfo("Enter jumble.");
    }

//Add a second loop that prompts the user for a set of clue letters
//   and the length of the first pun word.  This loop should activate
//   when the user clicks cancel when asked for a word to unjumble.
    while(true){
      String letters = ui.getInfo("Enter letters from clues.");

      if(letters == null){
        return;
      }

      String lettersSorted = sort(letters);
      int letter = 0;
      do{
        String word1Letters = ui.getInfo("How many letters in the first word?");
        try{
            letter = Integer.parseInt(word1Letters);
            if(letter <= 0) {
              ui.sendMessage(word1Letters + " is not a positive number.");
            }
          }catch(Exception e){
            ui.sendMessage(word1Letters + " is not a number.");
        }
      }while(letter <= 0);


      for(String key1: map.keySet()){
        if(key1.length() == letter){
          //Create an empty string key2.
          String key2 = "";
          //Declare key1index and initialize it to zero.  It is the index
          //of the current letter in key1.
          int indexKey1 = 0;

          for(int i = 0; i < lettersSorted.length(); i++) {

            if (indexKey1 < key1.length() && lettersSorted.charAt(i) == key1.charAt(indexKey1)) {
              indexKey1++;
            } else {
              key2 = key2 + lettersSorted.charAt(i);
            }
          }

          //If all the letters in key1 were found in letters and if key2 is
          //      a valid key, display the lists for key1 and key2.
            if(indexKey1 == key1.length()){
              if(map.containsKey(key2)){
              ui.sendMessage(map.get(key1) + " " + map.get(key2));
          }
        }
        }
      }
    }
  }
}

        
    

