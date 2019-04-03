float[][] initX()
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

float[][] initY()
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
