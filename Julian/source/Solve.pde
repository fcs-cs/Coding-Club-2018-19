Double solveRPN(ArrayList<String> eqn)
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
