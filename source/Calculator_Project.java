import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.lang.Math; 
import java.util.*; 
import java.util.Scanner; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Calculator_Project extends PApplet {

//Imports





//Set Width and Height --declared outside of Declarations for convinience--
int boardWidth = 900;
int boardHeight = 900;

//Setup
public void setup()
{
  
  //font
  //PFont font;
  // font = loadFont("");
  //textFont(font, 32);
  
  //board
   //Must match boardWidth and boardHeight
  background(0, 0, 0);
  fill(0xffFFFFFF);
  surface.setVisible(true);
  
  /*
  Current issues with:
  1) Nested parentses
  2) Rounding
  
  Does not allow:
  1) Negative number notation
  */
  
  
  //GUI Layout Initialization
  for(int i = 0; i < rectNum; i++)
  {
    for(int j = 0; j < rectNum; j++)
    {
      if (Arrays.asList(emptyRows).contains(i))
      {
        board[j][i] = "BLK";
      }
      else
      {
        board[j][i] = "NUL";
      }
    }
  }
  
  //opr
  board[0][0] = "+";
  board[1][0] = "-";
  board[2][0] = "*";
  board[3][0] = "/";
  board[4][0] = "^";
  
  //paren
  board[2][2] = "(";
  board[3][2] = ")";
  
  //num
  board[7][0] = "7";
  board[8][0] = "8";
  board[9][0] = "9";
  board[7][1] = "4";
  board[8][1] = "5";
  board[9][1] = "6";
  board[7][2] = "1";
  board[8][2] = "2";
  board[9][2] = "3";
  board[8][3] = ".";
  board[9][3] = "0";

  //del + sol
  board[8][5] = "DEL";
  board[9][5] = "SOL";
  
  for (int j = rectNum-1; j >= 0; j--)
    {
      board[j][textRow] = "TXT";
    }
}

//Draw
public void draw()
{
  fill(255, 255, 255);
  drawGUI(xLoc, yLoc);
}

//Button Pressed
public void mousePressed()
{
  int xPos = 0;
  int yPos = 0;
  int rectWidth = boardWidth/rectNum;
  int rectHeight = boardHeight/rectNum;
  
  //in board
  if(mousePressed == true && mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
  {
   
   //find y index
   for (int i = rectHeight; i < mouseY+rectHeight; i+=rectHeight)
   {
     //find x index
     for (int j = rectWidth; j < mouseX+rectWidth; j+= rectWidth)
      {
        if (j >= mouseX && i >= mouseY)
        {
          xPos = (j/rectWidth)-1;
          yPos = (i/rectHeight)-1;
      
        }
      } 
    }
    
    if(board[xPos][yPos] != null)
    {
      
      //add symbol to expression
      if (symbol.contains(board[xPos][yPos]))
      {
        input.append(board[xPos][yPos]);
        solved = false;
      }
      
      //
      else if (board[xPos][yPos].equals("SOL") && input.length() > 0)
      {
        System.out.println("\n\n__SOLVING__");
        System.out.println("EQUATION: " + input);
        
        //Parse
        finalExpression = parse(input.toString());;
        System.out.println("PARSED: " + finalExpression);
        
        //Solve 
        solution = solveRPN(finalExpression);
        solved = true;
        System.out.println("SOLUTION: " + solution);

        //Reset
        input = new StringBuilder();
      }
      else if (board[xPos][yPos].equals("DEL") && input.length() > 0)
      {
        input.deleteCharAt(input.length()-1);
      }
      
    }
  }
}
//Takes two numbers and an operator and performs the relevant operation
//Numbers can be negative
public double arith(String firstNumStr, String secondNumStr, String opr)
{
  //TODO Null Values
  double answer = 0;
  
  //Typecast chars to double
  double firstNum = PApplet.parseFloat(firstNumStr);
  double secondNum = PApplet.parseFloat(secondNumStr);
  
  //Addition
  if (opr.equals("+")) {
    return firstNum + secondNum;
  }
  
  //Subtraction
  else if (opr.equals("-")) {
    return firstNum - secondNum;
  }
  
  //Multiplication
  else if (opr.equals("*")) {
      return firstNum * secondNum;
  }
  
  //Division
  else if (opr.equals("/")) {
    try {
      return firstNum / secondNum;
    }
    catch(Exception e) {
      //Exception for division by zero
      throw new ArithmeticException("Dividing By Zero");
    }
  }
  
  //Exponent
  else if (opr.equals("^")) {
    return Math.pow(firstNum, secondNum);
  }
  
  else
  {
    System.out.println("Num1: "+firstNum);
    System.out.println("Num2: "+secondNum);
    System.out.println("Opr: "+opr);
    System.out.println(Double.valueOf(firstNum)-Double.valueOf(secondNum));
    throw new IllegalArgumentException("Invalid Expression");
  }
}
//I generally create these pages so that I can handle my variables

//!!boardWidth and boardHeight declared in Calculator_Project for convinience!!

String equation;
Scanner read = new Scanner(System.in);

//
String numeric = "0123456789.";
String operator = "+-/*^";
String orderStart = "([{";
String orderEnd = ")]}";
String other = "";
String symbol = numeric+operator+orderStart+orderEnd+other;
String operNum = numeric + operator;

int rLevel = 0;

//interface coordinates
int rectNum = 10; //Number of rectangles per row/column
float[][] xLoc = initX();
float[][] yLoc = initY();

//layout
String[][] board = new String[rectNum][rectNum];
int textRow = 8;
Integer[] emptyRows = {7, 9};

//input
StringBuilder input = new StringBuilder();
ArrayList<String> finalExpression = new ArrayList<String>();

boolean solved = false;
Double solution;
public void drawGUI(float[][] xCoords, float[][] yCoords)
{
  float rectHeight = boardHeight/rectNum;
  float rectWidth = boardWidth/rectNum;
  
  for(int i = 0; i < rectNum; i++)
  {
    for(int j = 0; j < rectNum; j++)
    { 
      //alignments
      rectMode(CENTER);
      textAlign(CENTER, CENTER);
      
      if (board[j][i] == "NUL")
      {
        //square formatting
        fill(0xffDCDCDD);
        
        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //symbols
      if (symbol.contains(board[j][i]))
      {
        //square formatting
        fill(0xff4C5C68);
        textSize(60);
        
        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
        
        //text formatting
        fill(0,0,0);
        stroke(1);
        textSize(60);
      
        if (board[j][i] == "*")
        {
          //text for "*"
          text(board[j][i], xCoords[j][i], yCoords[j][i]-rectHeight/7);
        }
        else if (board[j][i] == "/")
        {
          //text formatting
          textSize(40);
          
          //text for "/"
          text(board[j][i], xCoords[j][i], yCoords[j][i]-rectHeight/28);
        }
        else if (board[j][i] == "^")
        {
          //text for "^"
          text(board[j][i], xCoords[j][i], yCoords[j][i]);
        }
        else
        {
          //text for non unique symbols
          text(board[j][i], xCoords[j][i], yCoords[j][i]-rectHeight/7);
        }
      }
      
      //solve
      else if (board[j][i].equals("SOL"))
      {
        fill(0xff1985A1);
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //delete
      else if (board[j][i].equals("DEL"))
      {
        fill(0xffa12019);
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //empty
      else if (board[j][i].equals("BLK"))
      {
        //square formatting
        fill(0xff000000);

        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //text
      else if (board[j][i].equals("TXT"))
      {
        //square formatting
        fill(0xff000000);
        
        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
        
        //text formatting
        noStroke();
        textAlign(LEFT, CENTER);
        fill(0xffFFFFFF);
        
        //check solved
        if (solved == false)
        {
          //text
          text(input.toString(), xCoords[0][i], yCoords[0][i]);
        }
        else
        {
          //text
          text("="+solution.toString(), xCoords[0][i], yCoords[0][i]);
        }
        
      }
    }
  }
}
public float[][] initX()
{
  float[][] xCoords = new float[rectNum][rectNum];
  
  //Set X coordinates
  for(int i = 0; i < rectNum; i++)
  {
    for(int j = 0; j < rectNum; j++)
    {
      xCoords[j][i] = (boardWidth/rectNum)*(j+1)-(boardWidth/rectNum/2);
    }
  }
  
  return xCoords;
}

public float[][] initY()
{
  float[][] yCoords = new float[rectNum][rectNum];
  
  //Set Y coordinates
  for(int i = 0; i < rectNum; i++)
  {
    for(int j = 0; j < rectNum; j++)
    {
      yCoords[j][i] = (boardHeight/rectNum)*(i+1)-(boardHeight/rectNum/2);
    }
  }
  
  return yCoords;
}
//Parse method using shunting-yard algorithm :: takes expression String and converts it to postfix notation ArrayList<String>
public ArrayList<String> parse(String initialString)
{

  //Array for tokens
  String[] tokens = initialString.split("");

  //Count since the last operator
  int oprCount = 0;

  //
  StringBuilder recursiveParse = new StringBuilder();
  ArrayList<String> recurStk = new ArrayList<String>();
  
  //
  String combine = "";

  //level within parentheses
  boolean inParen = false;

  //
  ArrayList<String> output = new ArrayList<String>();
  Stack<String> stack = new Stack<String>();

  //Read
  for (int i = 0; i < tokens.length; i++)
  {
    //is number --> output
    if (numeric.contains(tokens[i]) && inParen == false)
    {
      output.add(tokens[i]);
      oprCount++;
    }
    
    //is operator:
    else if (operator.contains(tokens[i]) && inParen == false)
    {
      //compress numbers in output to allow for more than one diget --standard notation operators mark the end of numbers--
      for (int k = oprCount; k > 1; k--)
      {
        //string combining the two relevant digits with the lowest indexes
        combine = output.get(output.size()-k) + (output.get(output.size()-k+1));

        //set the lowest index string to the combined string
        output.set(output.size()-k, combine);

        //remove the value appended onto the combined string
        output.remove(output.size()-k+1);
      }
      
      oprCount = 0;
      
      //check whether operator is negative sign
      if (operator.contains(tokens[i-1]) && tokens[i].equals("-") || operator.contains(tokens[i-1]) && tokens[i].equals("+"))
      {
        output.add(tokens[i]);
        oprCount++;
      }
      else
      {
        //top-of-stack precedence is higher or equal --> pop stack to output
        while (stack.size() > 0 && operator.contains(stack.get(stack.size()-1)) && precedence(stack.get(stack.size()-1)) >= precedence(tokens[i])) 
        {
          output.add(stack.get(stack.size()-1));
          stack.pop();
        }
        //top-of-stack precendence less --> output
        stack.add(tokens[i]);
      }
    }
    
    //is symbol in parentheses
    else if (operNum.contains(tokens[i]) && inParen == true)
    {
      recurStk.add(tokens[i]);
      System.out.println("recursive stack: " + recurStk);
    }
    
    // is left parenthesis --> stack
    else if (orderStart.contains(tokens[i]))
    {
      
      inParen = true;
      recurStk.add(tokens[i]);
    }
    
    //is right parenthesis:
    else if (orderEnd.contains(tokens[i]))
    {
      recursiveParse = new StringBuilder(); //reset class
      
      //while there is no left paren on top of recurStk:
      while (orderStart.contains(recurStk.get(recurStk.size()-1)) == false)
      {
        //string containing infix values of expression in parenthesis
        recursiveParse.insert(0, recurStk.get(recurStk.size()-1));

        //pop string added to recursiveParse
        recurStk.remove(recurStk.size()-1);
      }
      
      //pop left parenthesis
      recurStk.remove(recurStk.size()-1);
      
      System.out.println("recurStk: " + recurStk);
      System.out.println("recursiveParse: "+recursiveParse);


      //IMPORTANT -- Recursive function subject to issues
      output.addAll(parse(recursiveParse.toString()));

      inParen = false;
    }
    
    
    else
    {
      throw new IllegalArgumentException("Invalid Character or Expression");
    }
  }

  //compress as before at the end of the expression
  for (int k = oprCount; k > 1; k--)
  {
    //string combining the two relevant digits with the lowest indexes
    combine = output.get(output.size()-k) + (output.get(output.size()-k+1));

    //set the lowest index string to the combined string
    output.set(output.size()-k, combine);

    //remove the value appended onto the combined string
    output.remove(output.size()-k+1);
  }

  //add remaining stack values to output
  for (int i = stack.size(); i > 0; i--)
  {
    output.add(stack.get(stack.size()-1));
    stack.pop();
  }
  
  return output;
}
public int precedence(String operator){
  switch (operator)
    {
      case "+":
      case "-":
      return 0;
      
      case "*":
      case "/":
      return 1;
      
      case "^":
      return 2;
      
      default:
      throw new IllegalArgumentException("Invalid Operator");
    }
}
public Double solveRPN(ArrayList<String> eqn)
{
  Stack<Double> stack = new Stack<Double>();
  Double arithSolution;
  
  String firstNum;
  String secondNum;
  String arithOpr;
  
  
  for(int i = 0; i < eqn.size(); i++)
  {
    //checking whether first or last digits are numeric
    if(numeric.contains(eqn.get(i).substring(0)) || numeric.contains(eqn.get(i).substring(eqn.get(i).length()-1))) // if number --> to stack
    {
      stack.add(Double.valueOf(eqn.get(i)));
    }
    
    else if(operator.contains(eqn.get(i))) // if operator --> arithmatic solution for top two numbers
    {
      firstNum = stack.get(stack.size()-2).toString();
      secondNum =stack.get(stack.size()-1).toString();
      arithOpr = eqn.get(i);
      
      //arith method to calculate current value
      arithSolution = arith(firstNum, secondNum, arithOpr);
      stack.pop();
      stack.pop();
      stack.add(arithSolution);
    }
    else 
    {
      throw new IllegalArgumentException("Invalid Character");
    }
  }
  
  if (stack.size() > 1)
  {
    throw new IllegalArgumentException("Invalid Expression");
  }
  else 
  {
    return stack.get(0);
  }
  
  
}
  public void settings() {  size(900, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#B889EA", "Calculator_Project" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
