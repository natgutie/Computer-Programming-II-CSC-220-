package prog04;

import java.util.Arrays;
import java.util.EmptyStackException;

/** Implementation of the interface StackInterface<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInterface<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;

  /** Number of elements in stack. */
  int size = 0;

  private static final int INITIAL_CAPACITY = 4;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    // EXERCISE:  Check if array is full and do something about it.

    //int index = 0;

    if (size == theData.length)
          reallocate();
        //theData[size] = theData[index];
        theData[size] = obj;
        size++;
        return obj;

    // Look at ArrayBasedPD.add/prog02 arraybasedPD add --> need too double to size of array

    // Putting the ++ after size means use its current value and then
    // increment it afterwards.
    // theData[size++] = obj;
    // Same as:
    // theData[size] = obj;
    // size++;
    //return obj;
  }

  // add reallocate method
  // line 20 hint
    protected void reallocate() {
      //E[] newData = new E[2 * theData.length];
      // public ArrayStack () {
      //    theData = (E[])new Object[INITIAL_CAPACITY];
      //  }

      E[] newData = Arrays.copyOf(theData, 2 * theData.length);
      theData = newData;

    }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();

    //inverse of push
    E obj = theData[size - 1];
    size--;
    return obj;

    // EXERCISE
  }

  /** Returns the object at the top of the stack without removing it.
   post: The stack remains unchanged.
   @return The object at the top of the stack.
   @throws EmptyStackException if stack is empty.
   */
  public E peek(){
    // same as pop including throw exception but do not decrement the size

    if (empty())
      throw new EmptyStackException();

    return theData[size - 1];

  }

  // EXERCISE

  /** Returns true if the stack is empty; otherwise, returns false.
   @return true if the stack is empty.
   */
  public boolean empty(){

    if(size == 0){
      return true;
    }
    return false;
    // return true if it is empty, false if it is not
    // what variable tells you (size?) if you have an empty

  }
}
