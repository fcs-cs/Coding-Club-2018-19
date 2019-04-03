//Parse method using shunting-yard algorithm :: takes expression String and converts it to postfix notation ArrayList<String>
ArrayList<String> parse(String initialString)
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
