package prog04;

import java.util.Stack;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import prog02.UserInterface;
import prog02.GUI;
import prog02.ConsoleUI;

public class Calculator {
  static final String OPERATORS = "()+-*/u^";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4 };
  static boolean previousTokenWasNumberOrRightParenthesis = false;
  Stack<Character> operatorStack = new Stack<Character>();
  Stack<Double> numberStack = new Stack<Double>();
  UserInterface ui = new GUI("Calculator");


  Calculator (UserInterface ui) { this.ui = ui; }

  void emptyStacks () {
    while (!numberStack.empty())
      numberStack.pop();
    while (!operatorStack.empty())
      operatorStack.pop();
  }

  String numberStackToString () {
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    // EXERCISE
    // Put every element of numberStack into helperStack
    // You will need to use a loop.  What kind?

    while(!numberStack.empty()){ // What condition? When can you stop moving elements out of numberStack?
      double number = numberStack.pop();

      // What method do you use to take an element out of numberStack?
      helperStack.push(number);
    }

    while(!helperStack.empty()){

      // What method do you use to put that element into helperStack?
      double help = helperStack.pop();

      // Now put everything back, but also add each one to s:
      s = s + " " + numberStack.push(help);
    }

    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    // EXERCISE
    Stack<Character> stackOperatorHelper = new Stack<>();

    // You will need to use a loop.  What kind?

    while(!operatorStack.empty()){ // What condition? When can you stop moving elements out of numberStack?
      Character operator = operatorStack.pop();

      // What method do you use to take an element out of numberStack?
      stackOperatorHelper.push(operator);
    }

    while(!stackOperatorHelper.empty()){

      // What method do you use to put that element into helperStack?
      Character operatorHelp = stackOperatorHelper.pop();

      // Now put everything back, but also add each one to s:
      s = s + " " + operatorHelp;
      operatorStack.push(operatorHelp);
    }

    return s;
  }

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  void doNumber (double x) {
    numberStack.push(x);
    displayStacks();
    previousTokenWasNumberOrRightParenthesis = true;
  }

  void doOperator (char op) {
    //processOperator(op);
    //displayStacks();

    if(op == '-' && !previousTokenWasNumberOrRightParenthesis) {
      processOperator('u');
    }
    else {
      processOperator(op);
    }
    displayStacks();
    if(op == ')')
      previousTokenWasNumberOrRightParenthesis = true;
    else
      previousTokenWasNumberOrRightParenthesis = false;

  }

  double doEquals () {
    while (!operatorStack.empty())
      evaluateTopOperator();

    return numberStack.pop();
  }
    
  double evaluateOperator (double a, char op, double b) {
    switch (op) {
      case '+':
        return a + b;

      // EXERCISE
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        return a / b;
      case '^':
        return Math.pow(a , b);
      default:
        System.out.println("Unknown operator " + op);
        return 0;
    }

  }

  void evaluateTopOperator () {
    char op = operatorStack.pop();

    if(op == 'u'){
      double n = numberStack.pop();
      numberStack.push(-n);
    }else {
      // EXERCISE
      // pop an operator off the operator stack and two number off the number stack

      double firstNumber = numberStack.pop();
      double secondNumber = numberStack.pop();

      // call evaluateOperator and push the result on the number stack

      double result = evaluateOperator(secondNumber, op, firstNumber);
      numberStack.push(result);
    }
    // Call displayStacks() before you return

    displayStacks();

    // remove negative number --> things come out of stacks in reverse
  }

  void processOperator (char op) {

    //while the top of the stack is not '('
    //	  evaluate the top operator
    //	pop the '('.

    if(op == '('){
      operatorStack.push(op); //push it
      return;
    }
    if (op == ')') {
      while(operatorStack.peek()!='(') {
        evaluateTopOperator();
      }
      operatorStack.pop();
      return;
    }

    if(op == 'u'){
      operatorStack.push(op);
      return;
    }

      //While the top element (if there is one) of the operator stack
      int p = precedence(op);

      while (!operatorStack.empty() && precedence(operatorStack.peek()) >= p) { //precedence >= than the precedence of op,
        evaluateTopOperator(); //evaluate it (call evaluateTopOperator()),
      }
      operatorStack.push(op);
      //then push op on the stack.

  }

  int precedence (char op){
    // Return the precedence value of op.  For example,
    // precedence('/') returns 2.
    int o = OPERATORS.indexOf(op);

    return PRECEDENCE[o];
  }
  
  static boolean checkTokens (UserInterface ui, Object[] tokens) {
      for (Object token : tokens)
        if (token instanceof Character &&
            OPERATORS.indexOf((Character) token) == -1) {
          ui.sendMessage(token + " is not a valid operator.");
          return false;
        }
      return true;
  }

  static void processExpressions (UserInterface ui, Calculator calculator) {
    while (true) {
      previousTokenWasNumberOrRightParenthesis = false;
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;
      Object[] tokens = Tokenizer.tokenize(line);
      if (!checkTokens(ui, tokens))
        continue;
      try {
        for (Object token : tokens)
          if (token instanceof Double)
            calculator.doNumber((Double) token);
          else          
            calculator.doOperator((Character) token);
        double result = calculator.doEquals();
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        ui.sendMessage("Bad expression.");
        calculator.emptyStacks();
      }
    }
  }

  public static void main (String[] args) {
    UserInterface ui = new ConsoleUI();
    Calculator calculator = new Calculator(ui);
    processExpressions(ui, calculator);
  }
}
