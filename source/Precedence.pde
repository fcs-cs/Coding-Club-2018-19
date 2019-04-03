int precedence(String operator){
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
