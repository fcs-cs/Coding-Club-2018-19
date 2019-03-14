//assigning html elements to variables
let i = document.getElementById('calcInput');
let e = document.getElementById('equals');
//the operator functions.
function add (a,b) {
  return Number(a)+Number(b);
}
function subtract (a,b) {
  return Number(a)-Number(b);
}
function mult(a,b) {
  return Number(a)*Number(b);
}
function divide(a,b) {
  return Number(a)/Number(b);
}
function pow(a,b) {
  return Math.pow(a,b);
}
//Checks if its a numeral or operator with two functions.
function isNumeral(char) {
  if (char == '0' || char == '1' || char == '2' || char == '3' || char == '4' || char == '5' || char == '6' || char == '7' || char == '8' || char == '9' || char == '.') {
    return true;
  }
  return false;
}
function isOperator(char) {
  if (char == '+' || char == '-' || char == '*' || char == '/' || char == '^') {
    return true;
  }
  return false;
}
//the function that runs when equals button is pressed
function calculate() {
  //input box in html file
  let box = i.value;
  //s1 is a temporary array for all number chars while s2 is for operands.
  let s1 = [];
  let s2 = [];
  //the array of numbers and operands that'll eventually be run through calc functions
  let nums = [];
  let ops = [];
  //for loop that uses isNumeral and isOperator functions to sort every character in s1 in s2 arrays. Nulls are used as placeholders.
  for (let i = 0; i < box.length; i++) {
    if (isNumeral(box[i])) {
      s1[i] = box[i];
      s2[i] = null;
    } else if (isOperator(box[i])) {
      s1[i] = null;
      s2[i] = box[i];
    } else {
    s1[i] = null;
    s2[i] = null;
  }
}
  //nb stores the string of number that'll go into the nums array
  let nb = '';
  //sorts all the groups of numbers in arrays into numbers without nulls and all that
  for (let i = 0; i < s2.length; i++) {
    if (s1[i] !== null) {
      nb = nb + s1[i];
    } else {
      nums.push(nb);
      ops.push(s2[i]);
      nb = '';
    }
  }
  nums.push(nb);
  //set the value to the first number
  let val = nums[0];
  //calls operators functions to use operators
  for (let i = 0; i < ops.length; i++) {
      if (ops[i] == '+') {
      val = add(val, nums[i+1]);
    } else if (ops[i] == '-') {
      val = subtract(val, nums[i+1]);
    } else if (ops[i] == '*') {
      val = mult(val, nums[i+1]);
    } else if (ops[i] == '/') {
      val = mult(val, nums[i+1]);
    } else if (ops[i] == '^') {
      val = pow(val, nums[i+1]);
    }
  }
  //pushes val to the bottom
  document.getElementById('answer').innerHTML = 'The Answer is: ' + val;
}
