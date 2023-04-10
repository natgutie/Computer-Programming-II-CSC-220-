package prog07;

import prog02.GUI;
import prog02.UserInterface;
import prog05.ArrayQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;

public class NotWordle { // NotWordle class
    UserInterface ui; // class variable
    List<Node> wordEntries = new ArrayList<Node>();

    // Add a private internal Node class to NotWordle with a String word and an Node next.
    // Add a List<Node> wordEntries to NotWordle initialized to an ArrayList<Node>.
    protected class Node {
        private String word;
        protected Node next;

        protected Node(String word) {
            this.word = word;
            next = null;
        }
    }

    NotWordle(UserInterface ui) { // constructor that takes a UserInterface
        this.ui = ui; // and stores it in a class variable
    }

    public static void main(String[] args){
        GUI ui = new GUI("NotWordle Game");
        NotWordle game = new NotWordle(ui);

        String wordFile = null;
        do {
            //ask the user for the name of a word file and call game.loadWords(filename).
            wordFile = ui.getInfo("Enter name of a word file: ");
            //wordFile = "/Users/natnat/Downloads/CSC220/src/prog05/words.txt";
            if(wordFile == null){
                return;
            }
        }while(!game.loadWords(wordFile));

        //ASK start
        String start = ui.getInfo("Enter starting word: ");

        //ASK final
        String end = ui.getInfo("Enter target word: ");

        //ASK human Computer
        int choice;
        String[] commands = {"Human plays.", "Computer plays.", "Solve2 plays.", "Solve3 plays."};
        choice = ui.getCommand(commands);

        switch (choice) {
            case 0:
                //if Human
                game.play(start, end);
            case 1:
                //if Computer
                game.solve(start, end);
            case 2:
                //if Solve2
                game.solve2(start, end);
            case 3:
                //if Solve2
                game.solve3(start, end);
        }

    }

    void play(String start, String end) {
        //   In play, do the following forever (until the return occurs).  Tell
        while (true) {
            //   the user the current word (the start) and the target word.  Ask for
            //   the next word.
            ui.sendMessage("Current word: " + start);
            ui.sendMessage("Target word: " + end);
            String nextWord = ui.getInfo("What is your next word?");
            //   Set the start word variable to that next word.  If
            //   it equals the target, tell the user ``You win!'' and return.
            //   Otherwise keep looping.  Test.
            if (nextWord == null) {
                return;
            }
            if (find(nextWord) == null) {
                ui.sendMessage("Word not in the dictionary.");
            } else if (!oneLetterDifferent(start, nextWord)) {
                // It should warn the user and NOT change the current (start) word variable if the
                // word that the user suggests is not one letter away from the current start word
                ui.sendMessage("Sorry, " + start + " differs by more than one letter from " + end);
            }
            else if (nextWord.equals(end)) {
                ui.sendMessage("You win!");
                return;
            }
            else{
                start = nextWord;
            }
        }

    }

    void solve(String start, String end) {

        //Inside solve, create a Queue of Node.
        Queue<Node> queueNode = new ArrayQueue<Node>();

        // Find the node belonging to the start word and put it into the queue.
        Node startNode = find(start);
        // Also save it in a variable since you will need to refer to it again later.
        queueNode.offer(startNode);
        //   While the queue is not empty, remove a node, and call it theNode.

        int count = 0;
        while(!queueNode.isEmpty()){
            Node theNode = queueNode.poll();
            count ++;
            //   Go through the list wordEntries and look for entries which aren't

            for (Node wordEntry : wordEntries){
            //   the start node, have next equal to null, and are one letter
            //   different from theNode.  For each one you find, call it nextNode.
                if(wordEntry != startNode && wordEntry.next == null && oneLetterDifferent(wordEntry.word, theNode.word)){
                    //   Set nextNode next to theNode and add nextNode to the queue.
                    Node nextNode = wordEntry;
                    nextNode.next = theNode;
                    queueNode.offer(nextNode);
                    //   If the word in nextNode is the target, then report success.
                    if(nextNode.word.equalsIgnoreCase(end)){
                        String lastString = "";

                        while(nextNode.next != null){
                            lastString = nextNode.word + " " + lastString;
                            nextNode = nextNode.next;
                        }
                        lastString = nextNode.word + " " + lastString;
                        lastString.trim();

                        ui.sendMessage("Success! This was the path followed " + lastString);

                        // Add code to solve so it displays a message about how many times it polls the queue.
                        ui.sendMessage(start + " to " + end + " polled " + count + " times.");
                    }
                //    To get the solution in reverse order, follow next back to the start node.
                    //    Display the solution in forward order.
                }

            }

        }

    }

    void solve2(String start, String end) {

        //Inside solve, create a Queue of Node.
        Queue<Node> queueNode = new PriorityQueue<>(new NodeComparator(end));

        // Find the node belonging to the start word and put it into the queue.
        Node startNode = find(start);
        // Also save it in a variable since you will need to refer to it again later.
        queueNode.offer(startNode);
        //   While the queue is not empty, remove a node, and call it theNode.

        int count = 0;
        while(!queueNode.isEmpty()){
            Node theNode = queueNode.poll();
            count ++;
            //   Go through the list wordEntries and look for entries which aren't

            for (Node wordEntry : wordEntries){
                //   the start node, have next equal to null, and are one letter
                //   different from theNode.  For each one you find, call it nextNode.
                if(wordEntry != startNode && wordEntry.next == null && oneLetterDifferent(wordEntry.word, theNode.word)){
                    //   Set nextNode next to theNode and add nextNode to the queue.
                    Node nextNode = wordEntry;
                    nextNode.next = theNode;
                    queueNode.offer(nextNode);
                    //   If the word in nextNode is the target, then report success.
                    if(nextNode.word.equalsIgnoreCase(end)){
                        String lastString = "";

                        while(nextNode.next != null){
                            lastString = nextNode.word + " " + lastString;
                            nextNode = nextNode.next;
                        }
                        lastString = nextNode.word + " " + lastString;
                        lastString.trim();

                        ui.sendMessage("Success! This was the path followed " + lastString);

                        // Add code to solve so it displays a message about how many times it polls the queue.
                        ui.sendMessage(start + " to " + end + " polled " + count + " times.");
                    }
                    //    To get the solution in reverse order, follow next back to the start node.
                    //    Display the solution in forward order.
                }

            }

        }

    }

    void solve3(String start, String end) {

        //Inside solve, create a Queue of Node.
        Queue<Node> queueNode = new Heap<>(new NodeComparator(end));

        // Find the node belonging to the start word and put it into the queue.
        Node startNode = find(start);
        // Also save it in a variable since you will need to refer to it again later.
        queueNode.offer(startNode);
        //   While the queue is not empty, remove a node, and call it theNode.

        int count = 0;
        while(!queueNode.isEmpty()){
            Node theNode = queueNode.poll();
            count ++;
            //   Go through the list wordEntries and look for entries which aren't

            for (Node wordEntry : wordEntries){
                //   the start node, have next equal to null, and are one letter
                //   different from theNode.  For each one you find, call it nextNode.
                if(wordEntry != startNode && wordEntry.next == null && oneLetterDifferent(wordEntry.word, theNode.word)){
                    //   Set nextNode next to theNode and add nextNode to the queue.
                    Node nextNode = wordEntry;
                    nextNode.next = theNode;
                    queueNode.offer(nextNode);
                    //   If the word in nextNode is the target, then report success.
                    if(nextNode.word.equalsIgnoreCase(end)){
                        String lastString = "";

                        while(nextNode.next != null){
                            lastString = nextNode.word + " " + lastString;
                            nextNode = nextNode.next;
                        }
                        lastString = nextNode.word + " " + lastString;
                        lastString.trim();

                        ui.sendMessage("Success! This was the path followed " + lastString);

                        // Add code to solve so it displays a message about how many times it polls the queue.
                        ui.sendMessage(start + " to " + end + " polled " + count + " times.");
                    }
                    //    To get the solution in reverse order, follow next back to the start node.
                    //    Display the solution in forward order.
                }

                //Add an else if whose condition is nextNode is not the start node
                //   and is one letter different from theNode and its distance to start
                //   is greater than theNode's plus 1.  In other words, we HAVE seen it
                //   before but now we have found a shorter path to it.

                else if(wordEntry != startNode && oneLetterDifferent(wordEntry.word, theNode.word) && distanceToStart(wordEntry) > distanceToStart(theNode) + 1){

                    //If that condition is true, set the next of nextNode to theNode,
                    wordEntry.next = theNode;
                    // remove nextNode from the queue
                    queueNode.remove(wordEntry);
                    //and offer it to the queue again.
                    queueNode.offer(wordEntry);
                }

            }

        }

    }

    static boolean oneLetterDifferent(String start, String end) {
        //two String as input and returns true if they have the same length and differ in exactly one character
        if (start.length() != end.length())
            return false;

        int differLength = 0;
        for (int i = 0; i < start.length(); i++) {
            if (start.charAt(i) != end.charAt(i)) {
                differLength++;
            }
        }
        if (differLength == 1) {
            return true;
        } else
            return false;

    }

    public boolean loadWords(String wordFile){ //throws FileNotFoundException { //throw file now found exception
        // create file
        try{
            File fileInput = new File(wordFile);
            // create scanner to read file
            Scanner scannerWord = new Scanner(fileInput);
            String word;
            while (scannerWord.hasNext()) { //file scanner. has next
                //For each word in the word file, loadWords should read in the word,
                word = scannerWord.nextLine();
                Node nodeWord = new Node(word);
                wordEntries.add(nodeWord);

                //Node theNode = new Node(scannerWord.next());
                //                wordEntries.add(theNode);
                //                theNode.next = null;
                // create a Node from it, and add this Node to wordEntries.
            }
            scannerWord.close();
            return true;
        }
        catch (FileNotFoundException e){
            ui.sendMessage("Missing file");
            return false;
        }

        // variable to hold each word you're reading from file

    }

    private Node find(String word) {
        //  Write a find method that takes a String word and finds that word in wordEntries.
        for (Node foundWord : wordEntries) {
            if (foundWord.word.equals(word)) {
                return foundWord;
                // It should return the node for that word
            }
        }
        return null; //or null if not there.
    }

// new methods for prog07 begin here
    static int lettersDifferent (String word, String target) {
        //Add a method that returns the number of letters two words differ.
        //For example lettersDifferent("said", "rain") is 2.
        //Position matters so lettersDifferent("arin", "rain") is also 2.
        //You can assume the words have the same length.
        int count = 0;
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) != target.charAt(i)){
                count++;
            }
        }
        return count;
    }

    int distanceToStart (Node node) {
        //Add a method that determines the distance of a Node back to the
        //start word by counting how many times you can follow next
        //before reaching null.
        int count = 0;

        while(node != null){
            count ++;
            node = node.next;
        }

        return count;
    }

    protected class NodeComparator implements Comparator<Node> {
        String target;
        public NodeComparator(String target){
            this.target = target;
        }
        int priority (Node node) {
            return distanceToStart(node) + lettersDifferent(node.word, target);
        }

        @Override
        public int compare(Node o1, Node o2) {
            return priority(o1) - priority(o2);
        }

    }

}




