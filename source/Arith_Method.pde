//Takes two numbers and an operator and performs the relevant operation
//Numbers can be negative
double arith(String firstNumStr, String secondNumStr, String opr)
{
  //TODO Null Values
  double answer = 0;
  
  //Typecast chars to double
  double firstNum = float(firstNumStr);
  double secondNum = float(secondNumStr);
  
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
