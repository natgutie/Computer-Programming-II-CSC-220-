package prog02;

import java.io.*;

/**
 * This is an implementation of PhoneDirectory that uses a sorted
 * array to store the entries.
 *
 * @author 
 */
public class SortedPD extends ArrayBasedPD {

    /**
     * Remove an entry from the directory.
     *
     * @param index The index in theDirectory of the entry to remove.
     * @return The DirectoryEntry that was just removed.
     */
    protected DirectoryEntry remove(int index) {
        DirectoryEntry entry = theDirectory[index];
        //theDirectory[index] = theDirectory[size - 1];

        // for loop for names to move index + 1 and decrement size
        for (int i = index; i < size - 1; i++) { //loop starting at index
            theDirectory[i] = theDirectory[i + 1]; //move entries over one
        }
        size--;
        return entry;
    }

    /**
     * Add an entry to the directory.
     *
     * @param index    The index at which to add the entry to theDirectory.
     * @param newEntry The new entry to add.
     * @return The DirectoryEntry that was just added.
     */
    protected DirectoryEntry add(int index, DirectoryEntry newEntry) {
        if (size == theDirectory.length)
            reallocate();

        // for loop at size minus one and works its way back to 0, opposite of remove
        for (int i = size - 1; i >= index; i--)
            theDirectory[i + 1] = theDirectory[i]; // move everything over to get a spot at index
        theDirectory[index] = newEntry;
        size++;
        return newEntry;
    }

    /**
     * Determine if name is located at index.
     *
     * @param index The index to be checked.
     * @param name  The name that might be located at that index.
     * @return true if a DirectoryEntry with that name is located at
     * that index.
     */
    protected boolean found(int index, String name) {
        // check value greater or lesser than the value compared too
        boolean found = index < size;
        if (found){
            // compare name you are trying to enter to the name in the index
            if((theDirectory[index].getName()).equals(name)){
                return true;
            }else{
                return false;
            }
        }
        return found;
    }

    /**
     * Find an entry in the directory.
     *
     * @param name The name to be found
     * @return The index of the entry with that name or, if it is not
     * there, the index where it should be added.
     */
    protected int find(String name) {
        int middleIndex;
        int high = size;
        int low = 0;

        // keeps looping until it finds the index where found and continues to split in middle till found
        while (low < high) {
            // find middle of the array
            middleIndex = (low + high) / 2;
            //compare name to the name in the index
            if (((theDirectory[middleIndex].getName()).compareTo(name))< 0){
                // low is now the index above where not found in the upper half
                    low = middleIndex + 1;
            } else {
                high = middleIndex;
            }
        }
        return high;
    }

}
