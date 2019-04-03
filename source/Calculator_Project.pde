//Imports
import java.lang.Math;
import java.util.*;
import java.util.Scanner;


//Set Width and Height --declared outside of Declarations for convinience--
int boardWidth = 900;
int boardHeight = 900;

//Setup
void setup()
{
  
  //font
  //PFont font;
  // font = loadFont("");
  //textFont(font, 32);
  
  //board
  size(900, 900); //Must match boardWidth and boardHeight
  background(0, 0, 0);
  fill(#FFFFFF);
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
void draw()
{
  fill(255, 255, 255);
  drawGUI(xLoc, yLoc);
}

//Button Pressed
void mousePressed()
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
