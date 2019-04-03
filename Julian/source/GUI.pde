void drawGUI(float[][] xCoords, float[][] yCoords)
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
        fill(#DCDCDD);
        
        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //symbols
      if (symbol.contains(board[j][i]))
      {
        //square formatting
        fill(#4C5C68);
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
        fill(#1985A1);
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //delete
      else if (board[j][i].equals("DEL"))
      {
        fill(#a12019);
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //empty
      else if (board[j][i].equals("BLK"))
      {
        //square formatting
        fill(#000000);

        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
      }
      
      //text
      else if (board[j][i].equals("TXT"))
      {
        //square formatting
        fill(#000000);
        
        //square
        rect(xCoords[j][i], yCoords[j][i], rectWidth, rectHeight);
        
        //text formatting
        noStroke();
        textAlign(LEFT, CENTER);
        fill(#FFFFFF);
        
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
